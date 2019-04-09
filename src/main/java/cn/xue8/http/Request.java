package cn.xue8.http;

import cn.xue8.http.connection.Connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-06 23:05
 **/
public final class Request {
    private final Headers headers;
    private Address address;
    private Connection connection;
    private String requestBody;
    private final String charSet;

    Request(Request.Builder builder) {
        this.headers = builder.headers.build();
        this.address = builder.address;
        this.requestBody = builder.requestBody;
        this.charSet = builder.charSet;
    }

    public String getCharSet() {
        return charSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String header(String name) {
        return headers.header(name);
    }

    public void addHeader(String name, String value) {
        headers.addHeader(name, value);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Address getAddress() {
        return address;
    }

    public final static class Builder {
        private String url;
        private String method = "GET";
        private Headers.Builder headers;
        private Address address;
        private String host;
        private int port;
        private String path;
        private String requestBody;
        private String charSet;

        public Builder() {
            this.headers = new Headers.Builder();
        }

        public Builder url(String url) {
            try {
                this.url = url.toLowerCase();
                checkUrl();
                host = InetAddress.getAllByName(this.url)[0].getHostAddress();
                if (host == null || host.equals(""))
                    throw new IllegalStateException("url error");
                this.address = new Address(host, port);
                addHeader(this.method, path + " HTTP/1.1");
                addHeader("Host", this.url);
                if (this.method.equals("POST") || this.method.equals("PUT"))
                    addHeader("Content-Length", String.valueOf(requestBody.length()));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder get() {
            this.method = "GET";
            this.requestBody = null;
            return this;
        }

        public Builder post(String requestBody) {
            this.method = "POST";
            this.requestBody = requestBody;
            return this;
        }

        public Builder delete() {
            this.method = "DELETE";
            this.requestBody = null;
            return this;
        }

        public Builder put(String requestBody) {
            this.method = "PUT";
            this.requestBody = requestBody;
            return this;
        }

        private void checkUrl() {
            if (url.contains("http://")) {
                url = url.replaceAll("http://", "");
            } else if(url.contains("https://")) {
                url = url.replaceAll("https://", "");
            }
            if (url.contains(":")) { //带端口
                String[] ary = url.split(":");
                url = ary[0];
                if (ary.length > 1) {
                    Pattern pattern = Pattern.compile("[0-9]\\d{0,5}");
                    Matcher matcher = pattern.matcher(ary[1]);
                    if (matcher.find()) {
                        port = Integer.valueOf(matcher.group(0));

                    }
                    path = ary[1].replaceAll(String.valueOf(port), "");
                }
            } else {  //不带端口
                port = 80;
                if (url.contains("/")) {
                    Pattern pattern = Pattern.compile("/.*");
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        path = matcher.group(0);
                        url = url.replace(path, "");
                    } else {
                        throw new IllegalStateException("url error");
                    }
                } else {
                    url = url;
                    path = "/";
                }
            }
        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder charSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Request build() {
            if (this.url == null) {
                throw new IllegalStateException("url == null");
            } else if (this.charSet == null){
                this.charSet = "UTF-8";
                return new Request(this);
            } else {
                return new Request(this);
            }
        }
    }
}
