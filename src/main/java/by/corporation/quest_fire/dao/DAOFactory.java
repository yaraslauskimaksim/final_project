package by.corporation.quest_fire.dao;

import by.corporation.quest_fire.dao.mysql.*;
import by.corporation.quest_fire.dao.mysql.impl.*;


public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final QuestDAO questDAO = new QuestDAOImpl();
    private static final CommentDAO commentDAO = new CommentDAOImpl();
    private static final BookingDAO bookingDAO = new BookingDAOImpl();
    private static final MessageDAO messageDAO = new MessageDAOImpl();


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
