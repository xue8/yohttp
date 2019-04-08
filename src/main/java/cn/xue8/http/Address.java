package cn.xue8.http;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 16:57
 **/
public final class Address {
    private final String host;
    private final int ip;

    public Address(String host, int ip) {
        this.host = host;
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public int getIp() {
        return ip;
    }

    @Override
    public int hashCode() {
        long code = Long.valueOf(host.replaceAll("\\.", "")) + ip;
        code = code / ip;
        return (int)code;
    }

    @Override
    public boolean equals(Object obj) {
        Address address = (Address) obj;
        if (address.host.equals(this.host) && address.ip == ip) {
            return true;
        } else {
            return false;
        }
    }
}
