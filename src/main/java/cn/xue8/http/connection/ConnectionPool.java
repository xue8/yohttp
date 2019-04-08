package cn.xue8.http.connection;

import cn.xue8.http.Address;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: http
 * @description: socket连接池
 * @author: Xue 8
 * @create: 2019-04-07 16:25
 **/
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
        if (connections.containsKey(address)) {
            synchronized (address) {
                connections.get(address).setTime(System.currentTimeMillis());
                return connections.get(address);
            }
        }

        synchronized (address) {
            if (!connections.containsKey(address)) {
                Connection connection = new Connection(address);
                connection.setTime(System.currentTimeMillis());
                connections.put(address, connection);
                cleanUpConnection();
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
                connections.remove(entry.getKey());
            }
        }
    }
}
