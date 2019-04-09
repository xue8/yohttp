package cn.xue8.http.executor;

import cn.xue8.http.CallBack;
import cn.xue8.http.Request;
import cn.xue8.http.Response;
import cn.xue8.http.YoHttpClient;
import cn.xue8.http.pipeline.CallServerInterceptor;
import cn.xue8.http.pipeline.Chain;
import cn.xue8.http.pipeline.ConnectionInterceptor;
import cn.xue8.http.pipeline.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: http
 * @description: 同步请求
 * @author: Xue 8
 * @create: 2019-04-07 18:44
 **/
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
