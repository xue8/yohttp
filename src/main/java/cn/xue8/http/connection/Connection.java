package cn.xue8.http.connection;

import cn.xue8.http.Address;

import java.io.IOException;
import java.net.Socket;

/**
 * @program: http
 * @description: socket连接对象
 * @author: Xue 8
 * @create: 2019-04-07 15:52
 **/
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
            this.socket = new Socket(address.getHost(), address.getIp());
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
