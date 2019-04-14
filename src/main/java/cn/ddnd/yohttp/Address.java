package cn.ddnd.yohttp;

public final class Address {
    private final String ip;
    private final int port;

    public Address(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int hashCode() {
        long code = Long.valueOf(ip.replaceAll("\\.", "")) + port;
        code = code / port;
        return (int)code;
    }

    @Override
    public boolean equals(Object obj) {
        Address address = (Address) obj;
        if (address.ip.equals(this.ip) && address.port == port) {
            return true;
        } else {
            return false;
        }
    }
}
