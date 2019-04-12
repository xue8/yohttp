package cn.ddnd.yohttp.connection;

import cn.ddnd.yohttp.Address;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private final ConcurrentHashMap<Address, Connection> connections;
    private int poolSize;
    private final long keepAliveTime;

    public ConnectionPool() {
        this(5, TimeUnit.MINUTES);
    }

    public ConnectionPool(long keepAliveTime, TimeUnit timeUnit){
        connections = new ConcurrentHashMap<>();
        this.keepAliveTime = timeUnit.toMillis(keepAliveTime);
    }

    public Connection getConnection(Address address) {
        return tryAcquire(address);
    }

    private Connection tryAcquire(Address address) {
        cleanUpConnection();
        if (connections.containsKey(address)) {
            connections.get(address).setTime(System.currentTimeMillis());
            return connections.get(address);
        }

        synchronized (address) {
            if (!connections.containsKey(address)) {
                Connection connection = new Connection(address);
                connection.setTime(System.currentTimeMillis());
                connections.put(address, connection);
                return connection;
            } else {
                connections.get(address).setTime(System.currentTimeMillis());
                return connections.get(address);
            }
        }
    }

    private void cleanUpConnection() {
        for (Map.Entry<Address, Connection> entry: connections.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().getTime() <= keepAliveTime) {
                try {
                    connections.get(entry.getKey()).getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connections.remove(entry.getKey());
            }
        }
    }
}
