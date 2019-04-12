package cn.ddnd.yohttp.pipeline;

import cn.ddnd.yohttp.Response;

public interface Interceptor {
    Response proceed(Response response);
}
