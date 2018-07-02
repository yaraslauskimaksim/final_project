package by.corporation.final_project.dao.pool;

import by.corporation.final_project.util.BundleResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * The class is responsible for instantiation of the connection pool.
 */

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private BlockingQueue<Connection> connectionQueue;
    private int poolSize;


    private static final ConnectionPool INSTANCE = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public BlockingQueue<Connection> getConnectionQueue() {
        return connectionQueue;
    }

    private ConnectionPool() {
        initPoolSize();
        Connection connection = null;
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                connection = DriverManager.getConnection(BundleResourceManager.getDatabasegProperty("db.url"), BundleResourceManager.getDatabasegProperty("db.user"), BundleResourceManager.getDatabasegProperty("db.password"));
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            } catch (SQLException e) {
                throw new RuntimeException("Error during instantiation of the connection pool");
            }
        }
    }


   private void initPoolSize() {
        try {
            this.poolSize = Integer.parseInt(BundleResourceManager.getDatabasegProperty("db.poolsize"));
        } catch (NumberFormatException e) {
            poolSize = 5;
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "connection can't be taken");
        }
        return connection;
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = connectionQueue.take();
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                ((PooledConnection) connection).reallyClose();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Exception occurs during the closing of the connection pool.", e);
            }
        }
    }

}
