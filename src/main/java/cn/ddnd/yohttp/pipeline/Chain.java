package cn.ddnd.yohttp.pipeline;

import cn.ddnd.yohttp.CallBack;
import cn.ddnd.yohttp.Response;

import java.util.List;

public final class Chain {
    private final List<Interceptor> interceptors;
    private final CallBack callBack;

    public Chain(List<Interceptor> interceptors, CallBack callBack) {
        this.interceptors = interceptors;
        this.callBack = callBack;
    }

    public Response proceed() {
        Response response = new Response();
        for (int i = 0; i < interceptors.size(); i++) {
            response = interceptors.get(i).proceed(response);
        }
        return response;
    }

    public CallBack getCallBack() {
        return callBack;
    }
}
