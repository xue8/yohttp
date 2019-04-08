package cn.xue8.http.pipeline;

import cn.xue8.http.Response;

public interface Interceptor {
    Response proceed(Response response);
}
