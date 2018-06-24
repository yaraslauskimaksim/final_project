package final_project.dao.db;

import final_project.dao.pool.ConnectionPool;
import final_project.dao.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDAO {

    private Connection connection;
   private boolean isTransaction;

    public ConnectionDAO() {
    }

    public ConnectionDAO(Connection connection) {
        this.connection=connection;
        isTransaction = true;
    }



    public Connection getConnection() throws ConnectionPoolException {
        if (connection == null) {
            return ConnectionPool.getInstance().getConnection();
        } else {
            return connection;
        }
    }

    public void closeConnection(Connection connection) throws SQLException {
        if(!isTransaction){
            connection.close();
        }
    }

}
