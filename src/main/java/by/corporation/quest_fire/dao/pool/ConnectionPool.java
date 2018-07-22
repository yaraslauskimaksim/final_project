package by.corporation.quest_fire.dao.pool;

import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * The class is responsible for instantiation of the connection pool.
 */

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private BlockingQueue<Connection> connectionQueue;

    /**
     * Defines data base and connection pool configurations
     */
    private String url;
    private String user;
    private String password;
    private int poolSize;


    private static final ConnectionPool INSTANCE = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public BlockingQueue<Connection> getConnectionQueue() {
        return connectionQueue;
    }


    public void initializeConnectionPool() throws ConnectionPoolException {
        initDBParameters();
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(new PooledConnection(connection));
            } catch (SQLException e) {
                throw new ConnectionPoolException("Error during initiation of the connection pool", e);
            }
        }
    }

    private void initDBParameters() {
        url = BundleResourceManager.getDatabasegProperty(Constants.URL);
        user = BundleResourceManager.getDatabasegProperty(Constants.USER);
        password = BundleResourceManager.getDatabasegProperty(Constants.PASSWORD);
        poolSize = Integer.parseInt(BundleResourceManager.getDatabasegProperty(Constants.POOL_SIZE));
        if (poolSize < 0) {
            throw new RuntimeException(
                    "Pool size incorrectly specified in property file, the number of connections should be positive digit.");
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

    /**
     * This method closes the connection pool (returns all
     * connections to the data base).
     * Logging error if connection can not be close
     */
    public void closeConnectionQueue()  {
        for (int i = 0; i < poolSize; i++) {
            //no poolsize
            try {
                Connection connection = connectionQueue.take();
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                ((PooledConnection) connection).reallyClose();
            } catch (SQLException | InterruptedException e) {
                LOGGER.error("connection can't be closed", e);
            }
        }
    }


    public void closeResourses(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

}
