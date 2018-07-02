package by.corporation.final_project.service.impl;

import by.corporation.final_project.dao.DAOFactory;
import by.corporation.final_project.dao.mysql.UserDAO;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.UserService;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.hash.PasswordHash;


public class UserServiceImpl implements UserService {

    private static UserService USER_SERVICE = new UserServiceImpl();

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    private UserServiceImpl() {
    }

    ;

    private static UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    public boolean isUserExists(User user) {
        boolean isUserExists = userDAO.isEmailExists(user);
        return isUserExists;
    }

    public int save(User users) throws ServiceException {
        int userID = 0;
        try {
            if (!isUserExists(users)) {
                userID = userDAO.registerUser(users);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during saving a new user on service layer", e);
        }
        return userID;
    }

    public User getUser(String email, String password) throws ServiceException {
        User user = null;
        String hashedPassword = PasswordHash.hash(password);

        try {
            user = userDAO.getUser(email, hashedPassword);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return user;
    }
}

