package cn.xue8.http.pipeline;

import cn.xue8.http.Request;
import cn.xue8.http.Response;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 23:29
 **/
public final class BaseRequestInterceptor implements Interceptor{
    private final Request request;

    public BaseRequestInterceptor(Request request) {
        this.request = request;
    }

    @Override
    public Response proceed(Response response) {
        return null;
    }
}
