package cn.ddnd.yohttp.pipeline;

import cn.ddnd.yohttp.Request;
import cn.ddnd.yohttp.Response;

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
