package final_project.dao.pool;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * The class is responsible for instantiation of the connection pool.
 */

public final class ConnectionPool  {

    private BlockingQueue<Connection> connectionQueue;
    private String driverName;
    private int poolSize;
    private String url;
    private String user;
    private String password;

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private ConnectionPool() {
        try {
            init();
        } catch (ConnectionPoolException e) {
           e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public BlockingQueue<Connection> getConnectionQueue() {
        return connectionQueue;
    }

    public Connection init() throws ConnectionPoolException {
        initParams();
        Connection connection = null;
        Locale.setDefault(Locale.ENGLISH);
        try {
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);

            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception occurs during pool initialization", e);
        }
        return connection;
    }

    public void initParams() {
        this.url = ConnectionManager.getProperty("db.url");
        this.user =ConnectionManager.getProperty("db.user");
        this.password=ConnectionManager.getProperty("db.password");
        try {
            this.poolSize=Integer.parseInt(ConnectionManager.getProperty("db.poolsize"));
        } catch (NumberFormatException e){
            poolSize=5;
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        }catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception occurs during getting connection form the connection pool", e);
        }
        return connection;
    }



    public void closeDB(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }

    public void closeDB(Connection connection, Statement statement) throws SQLException {
        closeDB(connection, statement, null);
    }

    public void closeDB(Statement... statements) throws SQLException {
        for (Statement statement : statements) {
            closeStatement(statement);
        }
    }

    public void closeDB(ResultSet resultSet, Statement... statements) throws SQLException {
        closeResultSet(resultSet);
        for (Statement statement : statements) {
            closeStatement(statement);
        }
    }



    private void closeConnection(Connection connection) throws SQLException {
        if(connection!=null){
            connection.close();
        }
    }
    private void closeResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet!=null){
            resultSet.close();
        }
    }
    private void closeStatement(Statement statement) throws SQLException {
        if(statement!=null){
            statement.close();
        }
    }



    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = connectionQueue.take();
                if (! connection.getAutoCommit()) {
                    connection.commit();
                }
                ((PooledConnection) connection).reallyClose();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Exception occurs during the closing of the connection pool.", e);
            }
        }
    }

}
