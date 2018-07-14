package by.corporation.final_project.dao;

import by.corporation.final_project.dao.mysql.*;
import by.corporation.final_project.dao.mysql.impl.*;
import by.corporation.final_project.dao.pool.ConnectionPool;



public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private static final UserDAO userDAO = new UserDAOImpl(ConnectionPool.getInstance());
    private static final QuestDAO questDAO = new QuestDAOImpl(ConnectionPool.getInstance());
    private static final CommentDAO commentDAO = new CommentDAOImpl(ConnectionPool.getInstance());
    private static final BookingDAO bookingDAO = new BookingDAOImpl(ConnectionPool.getInstance());
    private static final MessageDAO messageDAO = new MessageDAOImpl(ConnectionPool.getInstance());


    private DAOFactory(){

    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }


    public QuestDAO getQuestDAO() {
        return questDAO;
    }


    public CommentDAO getCommentDAO() {
        return commentDAO;
    }

    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }

    public MessageDAO getMessageDAO() {
        return messageDAO;
    }
}
