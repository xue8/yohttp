package cn.ddnd.yohttp.executor;

import cn.ddnd.yohttp.Response;
import cn.ddnd.yohttp.pipeline.Chain;

import java.util.concurrent.*;

public final class Dispatcher {
    private final ChainQueue chainQueue;
    private final ThreadPoolExecutor threadPoolExecutor;

    public Dispatcher() {
        chainQueue = new ChainQueue();
        threadPoolExecutor = new ThreadPoolExecutor(0,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        this.executor();
    }

    public void addChain(Chain chain) {
        chainQueue.addChain(chain);
    }

    public Chain pollChain() {
        return chainQueue.pollChain();
    }

    private void executor() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (chainQueue.size() > 0) {
                        threadPoolExecutor.submit(new CallRunnable(chainQueue.pollChain()));
                    }
                }
            }
        });
        thread.start();
    }

    private final class CallRunnable implements Runnable {
        private Chain chain;

        CallRunnable(Chain chain) {
            this.chain = chain;
        }
        @Override
        public void run() {
            Response response = chain.proceed();
            chain.getCallBack().onResponse(response);
            chain = null;
        }
    }

    private final class ChainQueue {
        private BlockingQueue<Chain> queue;

        ChainQueue() {
            queue = new LinkedBlockingDeque();
        }

        public void addChain(Chain chain) {
            queue.add(chain);
        }

        public Chain pollChain() {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        public int size() {
            return queue.size();
        }
    }
}
