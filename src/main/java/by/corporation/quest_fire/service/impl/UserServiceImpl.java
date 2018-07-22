package by.corporation.quest_fire.service.impl;


import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.UserDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.UserAlreadyExistException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.service.hash.PasswordHash;
import by.corporation.quest_fire.service.validation.Validator;
import by.corporation.quest_fire.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    /**
     * The method returns the {@link User} who provides {@code email}
     * and {@code password}. Returns {@code null} if user is not found.
     * Before sending password to DAO, it is hashed.
     *
     * @return {@code null} if such user is not found.
     * @throws ValidationException if user tries to send empty fields
     * @throws ServiceException    the service exception
     */

    public User fetchUser(User user) throws ServiceException, ValidationException {
        try {
            if (!Validator.validateEmptyFields(user.getEmail(), user.getPassword())) {
                throw new ValidationException("Sent data is not correct");
            }
            String hashedPassword = PasswordHash.hash(user.getPassword());
            user = userDAO.fetchUser(user.getEmail(), hashedPassword);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving user", e);
        }
        return user;
    }

    /**
     * The method returns the auto generated id for user
     *
     * @throws ValidationException       if user fills in incorrect email and password
     * @throws UserAlreadyExistException if user already exists
     * @throws ServiceException          the service exception
     */
    public int register(User user) throws ServiceException, ValidationException, UserAlreadyExistException {
        if (fetchUserId(user) != 0) {
            throw new UserAlreadyExistException("User already exists");
        }
        if (!Validator.validateEmail(user.getEmail()) || !Validator.validatePassword(user.getPassword())) {
            throw new ValidationException("Invalid data is sent");
        }
        try {
            int userID = userDAO.registerUser(user);
            return userID;
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during saving a new user on service layer", e);
        }
    }

    /**
     * The method returns the user'd id in order to verify whether such user exists
     *
     * @param user(email)
     * @throws ServiceException the service exception
     */
    private int fetchUserId(User user) throws ServiceException {
        Integer userId = 0;
        try {
            userId = userDAO.fetchUserId(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving user's id", e);
        }
        return userId;
    }

    @Override
    public void makeUserActive(int userId) throws ServiceException {
        Status status;
        try {
            status = userDAO.getStatus(userId);
            if (status.equals(Status.FROZEN)) {
                userDAO.makeUserActive(userId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }
    }

    @Override
    public void frozeUser(int userId) throws ServiceException {
        Status status;
        try {
            status = userDAO.getStatus(userId);
            if (status.equals(Status.ACTIVE)) {
                userDAO.frozeUser(userId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }
    }

    @Override
    public List<User> getUsersByStatus(Status status, int currentPage) {
        List<User> userList = null;
        try {
            userList = userDAO.getAllUsersWithStatus(status, currentPage, Constants.ITEMS_PER_PAGE);

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public int getUserQuantatyByStatus(Status status) throws ServiceException {
        int counter = 0;

        try {
            counter = userDAO.getUserQuantatyByStatus(status);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return counter;
    }


}

