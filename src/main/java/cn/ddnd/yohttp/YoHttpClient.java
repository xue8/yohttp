package cn.ddnd.yohttp;

import cn.ddnd.yohttp.connection.ConnectionPool;
import cn.ddnd.yohttp.executor.AsyncCall;
import cn.ddnd.yohttp.executor.Dispatcher;
import cn.ddnd.yohttp.executor.SyncCall;

import java.util.concurrent.TimeUnit;

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
    
}
