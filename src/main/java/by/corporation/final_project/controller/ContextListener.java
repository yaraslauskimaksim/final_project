package by.corporation.final_project.controller;

import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.dao.pool.ConnectionPoolException;
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
            ConnectionPool.getInstance().closeConnectionQueue();
        } catch (ConnectionPoolException e) {
            LOGGER.fatal("Exception during closing pool");
        }
    }
}
