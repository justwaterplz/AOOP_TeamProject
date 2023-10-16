package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int initialSize = 10;
    private static final ConnectionPool instance;

    static {
        try {
            instance = new ConnectionPool(initialSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final List<Connection> pool = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    // 싱글턴
    public static synchronized ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool(int initialSize) throws SQLException {
        for (int i = 0; i < initialSize; i++) {
            pool.add(createNewConnection());
        }
    }

    public Connection getConnection() {
        lock.lock();
        try {
            if (pool.isEmpty()) {
                return createNewConnection();
            }
            Connection connection = pool.remove(pool.size() - 1);
            if (connection.isClosed()) {
                return getConnection();
            }
            return connection;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void releaseConnection(Connection connection) {
        lock.lock();
        try {
            if (connection != null) {
                pool.add(connection);
            }
        } finally {
            lock.unlock();
        }
    }

    private Connection createNewConnection() throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/main";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(dbUrl, user, password);
    }
}
