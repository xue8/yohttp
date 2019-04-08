package cn.xue8.http.pipeline;

import cn.xue8.http.CallBack;
import cn.xue8.http.Response;
import cn.xue8.http.executor.Call;

import java.util.List;

/**
 * @program: http
 * @description: 流水线处理器
 * @author: Xue 8
 * @create: 2019-04-07 22:56
 **/
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
