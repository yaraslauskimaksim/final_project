package by.corporation.quest_fire.service.impl;


import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.CommentDAO;
import by.corporation.quest_fire.dao.TransactionManager;
import by.corporation.quest_fire.dao.mysql.UserDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.UserAlreadyExistException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.service.hash.PasswordHash;
import by.corporation.quest_fire.service.validation.Validator;
import by.corporation.quest_fire.util.Constant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserServiceImpl implements UserService {


    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private static CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();


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
            user = userDAO.fetchUserByEmailPassword(user.getEmail(), hashedPassword);
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
    public long register(User user) throws ServiceException, ValidationException, UserAlreadyExistException {
        if (fetchUserId(user) != 0) {
            throw new UserAlreadyExistException("User already exists");
        }
        if (!Validator.validateEmail(user.getEmail()) || !Validator.validatePassword(user.getPassword())) {
            throw new ValidationException("Invalid data is sent");
        }
        try {
            long userID = userDAO.create(user);
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
    private long fetchUserId(User user) throws ServiceException {
        long userId = 0;
        try {
            userId = userDAO.fetchUserId(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving user's id", e);
        }
        return userId;
    }

    /**
     * This method sets the user's status to 'active'
     * and sets the comments statuses to 'active'.
     * If status of user is 'frozen', the {@link User} status is updated
     * and updated comments and user are sent to dao layer.
     *
     * @throws DaoException if {@link SQLException} happens.
     */
    @Override
    public void makeUserActive(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager(ConnectionPool.getInstance().getConnection());
        try {
            User user = userDAO.fetchById(userId);
            Status status = user.getStatus();
            doUserActiveTransaction(status, user, transactionManager, userId);
        } catch (DaoException e) {
            transactionManager.rollback();
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        } finally {
            transactionManager.stopTransaction();
        }
    }


    /**
     * This method sets the user's status to 'frozen'
     * and sets the comments statuses to 'frozen'.
     * If status of user is 'active', the {@link User} status is updated
     * and updated comments and user are sent to dao layer.
     *
     * @throws DaoException if {@link SQLException} happens.
     */
    @Override
    public void frozeUser(long userId) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager(ConnectionPool.getInstance().getConnection());
        try {
            User user = userDAO.fetchById(userId);
            Status status = user.getStatus();
            doUserFrozenTransaction(status, user, transactionManager, userId);
        } catch (DaoException e) {
            transactionManager.rollback();
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        } finally {
            transactionManager.stopTransaction();
        }
    }


    @Override
    public List<User> fetchUsersByStatus(Status status, int currentPage) throws ServiceException {
        List<User> userList = null;
        try {
            userList = userDAO.getAllUsersWithStatus(status, currentPage, Constant.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("Exception happens during retrieving data of users filtered by status");
        }
        return userList;
    }

    @Override
    public int fetchUserQuantityByStatus(Status status) throws ServiceException {
        int counter = 0;

        try {
            counter = userDAO.getUserQuantatyByStatus(status);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return counter;
    }


    /**
     * This method sets the comments statuses to 'active'.
     *
     * @throws DaoException if {@link SQLException} happens.
     */
    private List<Comment> enableComments(long userId) throws DaoException {
        List<Comment> comments = commentDAO.fetchAllByUserId(userId);
        List<Comment> updatedStatus = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getStatus().equals(Status.FROZEN)) {
                comment.setStatus(Status.APPROVED);
                updatedStatus.add(comment);
            }
            if (comment.getStatus().equals(Status.FROZEN_PENDING)) {
                comment.setStatus(Status.PENDING);
                updatedStatus.add(comment);
            }
        }
        return updatedStatus;
    }

    /**
     * This method sets the comments statuses to 'frozen'.
     *
     * @throws DaoException if {@link SQLException} happens.
     */
    private List<Comment> disableComments(long userId) throws DaoException {
        List<Comment> comments = commentDAO.fetchAllByUserId(userId);
        List<Comment> updatedStatus = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getStatus().equals(Status.APPROVED)) {
                comment.setStatus(Status.FROZEN);
                updatedStatus.add(comment);
            }
            if (comment.getStatus().equals(Status.PENDING)) {
                comment.setStatus(Status.FROZEN_PENDING);
                updatedStatus.add(comment);
            }
        }
        return updatedStatus;
    }

    private void doUserActiveTransaction(Status status, User user, TransactionManager transactionManager, long userId) throws DaoException {
        if (status.equals(Status.FROZEN)) {
            user.setStatus(Status.ACTIVE);
            List<Comment> comments = enableComments(userId);
            transactionManager.startTransaction();
            userDAO.update(user, comments);
            commentDAO.update(comments);
            transactionManager.commit();
        } else {
            LOGGER.log(Level.WARN, "Status should be 'frozen'");
        }
    }

    private void doUserFrozenTransaction(Status status, User user, TransactionManager transactionManager, long userId) throws DaoException {
        if (status.equals(Status.ACTIVE)) {
            user.setStatus(Status.FROZEN);
            List<Comment> comments = disableComments(userId);
            transactionManager.startTransaction();
            userDAO.update(user, comments);
            transactionManager.commit();
        } else {
            LOGGER.log(Level.WARN, "Status should be 'active'");
        }
    }
}

