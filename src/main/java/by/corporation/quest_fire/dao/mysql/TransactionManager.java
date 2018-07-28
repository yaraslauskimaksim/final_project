package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger(TransactionManager.class);
    private PooledConnection connection = ConnectionPool.getInstance().getConnection();

    public void startTransaction()  {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction can not be started");
        }
    }

    public void stopTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction can not be stopped");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction can not be committed");
        }
    }


    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction can not be rollbacked");
        }
    }


    public void setTransactionIsolation(int level)  {
        try {
            connection.setTransactionIsolation(level);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Transaction isolation can not be set");
        }

    }


}