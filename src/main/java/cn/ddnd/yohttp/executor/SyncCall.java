package cn.ddnd.yohttp.executor;

import cn.ddnd.yohttp.Request;
import cn.ddnd.yohttp.Response;
import cn.ddnd.yohttp.YoHttpClient;
import cn.ddnd.yohttp.pipeline.CallServerInterceptor;
import cn.ddnd.yohttp.pipeline.Chain;
import cn.ddnd.yohttp.pipeline.ConnectionInterceptor;
import cn.ddnd.yohttp.pipeline.Interceptor;

import java.util.ArrayList;
import java.util.List;

public final class SyncCall implements Call{
    private final YoHttpClient yoHttpClient;
    private boolean executed;
    private final Request request;

    public SyncCall(YoHttpClient yoHttpClient, Request request) {
        this.yoHttpClient = yoHttpClient;
        this.request = request;
        executed = false;
    }

    @Override
    public Response executor() {
        synchronized (this) {
            if (this.executed)
                throw new IllegalStateException("Call Already Executed");
            this.executed = true;
        }
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ConnectionInterceptor(yoHttpClient, request));
        interceptors.add(new CallServerInterceptor(request));
        Chain chain = new Chain(interceptors, null);
        Response response = chain.proceed();
        chain = null; //help gc
        return response;
    }

}
