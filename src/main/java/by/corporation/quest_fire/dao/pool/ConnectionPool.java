package by.corporation.quest_fire.dao.pool;

import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.util.BundleResourceManager;
import by.corporation.quest_fire.util.Constant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The class is responsible for instantiation of the connection pool.
 */

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    /**
     * Defines data base and connection pool configurations
     */

    private BlockingQueue<PooledConnection> availableConnection;
    private ArrayDeque<PooledConnection> takenConnections;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private int poolSize;


    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    private void register() {
        poolSize = Integer.parseInt(BundleResourceManager.getDatabasegProperty(Constant.POOL_INITIAL_SIZE));
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Couldn't register driver", e);
            throw new RuntimeException("Couldn't register driver", e);
        }
    }

    public void initializeConnectionPool() throws ConnectionPoolException {
        register();
        availableConnection = new ArrayBlockingQueue<>(poolSize);
        takenConnections = new ArrayDeque<>();
        for (int i = 0; i < poolSize; i++) {
            try {
                createConnection();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        if (availableConnection.size() == poolSize) {
            LOGGER.log(Level.INFO, "Successfully initialized connection pool");
        }

    }


    public PooledConnection getConnection() {
        PooledConnection connection = null;
        try {
            connection = availableConnection.take();
            takenConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "connection can't be taken");
        }
        return connection;
    }

    private PooledConnection createConnection() throws SQLException {
        PooledConnection connection = new PooledConnection(DriverManager.getConnection(
                BundleResourceManager.getDatabasegProperty(Constant.URL),
                BundleResourceManager.getDatabasegProperty(Constant.USER),
                BundleResourceManager.getDatabasegProperty(Constant.PASSWORD)));
        availableConnection.add(connection);
        return connection;
    }

    public void releaseConnection(PooledConnection connection) throws ConnectionPoolException {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            takenConnections.remove(connection);
            availableConnection.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Couldn't release connection", e);
        }
    }
    /**
     * Closes the pool by closing each of its connections.
     */

    public void closeConnectionPool() throws ConnectionPoolException {
        PooledConnection connection;
        int currentPoolSize = availableConnection.size();
        for (int i = 0; i < currentPoolSize; i++) {
            try {
                connection = availableConnection.take();
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, e);
            } catch (SQLException e) {
                throw new ConnectionPoolException("Couldn't close connection", e);
            }
        }
        deregisterAllDrivers();
    }

    private void deregisterAllDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Deregister driver error");
        }
    }

    public void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    public void closeStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

    public void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }

    public void closeDBResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        resultSet.close();
        preparedStatement.close();
    }

    public void closeDBResources(ResultSet resultSet, Statement statement) throws SQLException {
        resultSet.close();
        statement.close();
    }
}
