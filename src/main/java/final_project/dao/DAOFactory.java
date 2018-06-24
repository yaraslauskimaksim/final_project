package final_project.dao;

import final_project.dao.db.impl.AdministratorDAOImpl;
import final_project.dao.db.impl.CommentDAOImpl;
import final_project.dao.db.impl.QuestDAOImpl;
import final_project.dao.db.impl.UserDAOImpl;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final QuestDAO questDAO = new QuestDAOImpl();
    private static final CommentDAO commentDAO = new CommentDAOImpl();
    private static final AdministratorDAO administratorDAO = new AdministratorDAOImpl();

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

    public AdministratorDAO getAdministratorDAO() {
        return administratorDAO;
    }
}
