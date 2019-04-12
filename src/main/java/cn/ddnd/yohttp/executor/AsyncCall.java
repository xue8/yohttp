package cn.ddnd.yohttp.executor;

import cn.ddnd.yohttp.CallBack;
import cn.ddnd.yohttp.Request;
import cn.ddnd.yohttp.Response;
import cn.ddnd.yohttp.YoHttpClient;
import cn.ddnd.yohttp.pipeline.CallServerInterceptor;
import cn.ddnd.yohttp.pipeline.Chain;
import cn.ddnd.yohttp.pipeline.ConnectionInterceptor;
import cn.ddnd.yohttp.pipeline.Interceptor;

import java.util.ArrayList;
import java.util.List;

public final class AsyncCall implements Call{
    private final YoHttpClient yoHttpClient;
    private boolean executed;
    private final Request request;

    public AsyncCall(YoHttpClient yoHttpClient, Request request) {
        this.yoHttpClient = yoHttpClient;
        this.request = request;
        executed = false;
    }

    @Override
    public Response executor() {
        return null;
    }

    public void executor(CallBack callBack) {
        synchronized (this) {
            if (this.executed)
                throw new IllegalStateException("Call Already Executed");
            this.executed = true;
        }
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ConnectionInterceptor(yoHttpClient, request));
        interceptors.add(new CallServerInterceptor(request));
        Chain chain = new Chain(interceptors, callBack);
        yoHttpClient.getDispatcher().addChain(chain);
    }
}
