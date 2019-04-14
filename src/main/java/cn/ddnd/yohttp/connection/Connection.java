package cn.ddnd.yohttp.connection;

import cn.ddnd.yohttp.Address;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;

public final class Connection {
    private Socket socket;
    private final Address address;
    private long time;

    public Socket getSocket() {
        return socket;
    }

    public Connection(Address address) {
        this.address = address;
        try {
            if (address.getPort() == 443) {
                this.socket = SSLSocketFactory.getDefault().createSocket(address.getIp(), address.getPort());
            } else {
                this.socket = new Socket(address.getIp(), address.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Address address = (Address) obj;
        if (this.address.equals(address)) {
            return true;
        } else {
            return false;
        }
    }
}
