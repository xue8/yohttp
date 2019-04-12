package cn.ddnd.yohttp.pipeline;

import cn.ddnd.yohttp.Address;
import cn.ddnd.yohttp.Request;
import cn.ddnd.yohttp.Response;
import cn.ddnd.yohttp.YoHttpClient;
import cn.ddnd.yohttp.connection.Connection;

public final class ConnectionInterceptor implements Interceptor{
    private final YoHttpClient yoHttpClient;
    private final Request request;

    public ConnectionInterceptor(YoHttpClient yoHttpClient, Request request) {
        this.yoHttpClient = yoHttpClient;
        this.request = request;
    }

    @Override
    public Response proceed(Response response) {
        Address address = request.getAddress();
        Connection connection = yoHttpClient.getConnectionPool().getConnection(address);
        request.setConnection(connection);
        return response;
    }
}
