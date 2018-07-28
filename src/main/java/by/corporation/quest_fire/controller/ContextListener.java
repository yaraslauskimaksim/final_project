package by.corporation.quest_fire.controller;

import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().initializeConnectionPool();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Error during initializing of connection pool");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().closeConnectionPool();
        } catch (ConnectionPoolException e) {
            LOGGER.log(Level.ERROR, "Pool can not be closed!", e);
        }
    }
}
