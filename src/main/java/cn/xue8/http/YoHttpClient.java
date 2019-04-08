package cn.xue8.http;

import cn.xue8.http.connection.ConnectionPool;
import cn.xue8.http.executor.AsyncCall;
import cn.xue8.http.executor.Dispatcher;
import cn.xue8.http.executor.SyncCall;

import java.util.concurrent.TimeUnit;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 18:31
 **/
public final class YoHttpClient {
    private final Dispatcher dispatcher;
    private final ConnectionPool connectionPool;

    public YoHttpClient() {
        this(5, TimeUnit.MINUTES);
    }

    public YoHttpClient(int keepAliveTime, TimeUnit timeUnit) {
        this.dispatcher = new Dispatcher();
        this.connectionPool = new ConnectionPool(keepAliveTime, timeUnit);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public AsyncCall AsyncCall(Request request) {
        return new AsyncCall(this, request);
    }

    public SyncCall SyncCall(Request request) {
        return new SyncCall(this, request);
    }

    public static class Builder {
        public YoHttpClient build() {
            return new YoHttpClient();
        }
    }
}
