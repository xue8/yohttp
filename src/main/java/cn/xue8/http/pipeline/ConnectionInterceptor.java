package cn.xue8.http.pipeline;

import cn.xue8.http.Address;
import cn.xue8.http.Request;
import cn.xue8.http.Response;
import cn.xue8.http.YoHttpClient;
import cn.xue8.http.connection.Connection;

/**
 * @program: http
 * @description:  获取connection连接
 * @author: Xue 8
 * @create: 2019-04-07 20:34
 **/
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
