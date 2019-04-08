package cn.xue8.http;

import cn.xue8.http.connection.Connection;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 18:40
 **/
public class Response {
    private Request request;
    private String body;
    private String headerInfo;

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
