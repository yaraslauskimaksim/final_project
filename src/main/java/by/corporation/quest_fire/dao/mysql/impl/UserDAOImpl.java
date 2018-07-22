package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.mysql.TransactionManager;
import by.corporation.quest_fire.dao.mysql.UserDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.*;
import by.corporation.quest_fire.service.hash.PasswordHash;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with user
 */
public class UserDAOImpl implements UserDAO {

    TransactionManager transactionManager = new TransactionManager();

    private static final String SELECT_LOGGIN_USER = "select  usr_id, usr_firstname, usr_lastname, usr_email, usr_password, usr_status, rol_role from user join role on usr_role_id = rol_id where usr_email = ? and usr_password=?";
    private static final String CREATE_NEW_USER = "insert into user(usr_firstname,usr_lastname,usr_email, usr_password) values (?,?,?,?)";
    private static final String SELECT_USER_ID = "SELECT usr_id from user where usr_email=?";
    private static final String MAKE_USER_ACTIVE = "UPDATE user SET usr_status = 'active' WHERE usr_id = ?";
    private static final String FROZE_USER = "UPDATE user SET usr_status = 'frozen' WHERE usr_id = ?";
    private static final String SELECT_STATUS = "SELECT usr_id, usr_status FROM user WHERE usr_id = ?";
    private static final String GET_ALL_USERS_WITH_STATUS = "SELECT usr_id, usr_status, usr_firstname, usr_lastname, usr_email FROM user WHERE usr_status = ? LIMIT ? OFFSET ?";
    private static final String GET_USER_BY_STATUS_QUANTITY = "SELECT COUNT(*) FROM user WHERE usr_status = ?";
    private static final String REJECT_COMMENT = "UPDATE comment SET com_status = 'rejected' WHERE com_user_id = ?";
    private static final String APPROVE_COMMENT = "UPDATE comment SET com_status = 'approved' WHERE com_user_id = ?";

    /**
     * The method returns the {@link User} from db.
     *
     * @throws DaoException the dao exception
     */

    public User fetchUser(String email, String password) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGGIN_USER);) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getRow() == 0) {
                return null;
            }
            user.setRole(Role.valueOf(resultSet.getString(Constants.ROLE).toUpperCase()));
            user.setId(resultSet.getInt(Constants.USER_ID));
            user.setEmail(resultSet.getString(Constants.USER_EMAIL));
            user.setPassword(resultSet.getString(Constants.USER_PASSWORD));
            user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
            user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
            user.setStatus(Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase()));
            return user;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving a user who is trying to log in");
        }
    }

    /**
     * The method returns the generated id for user from db.
     *
     * @throws DaoException the dao exception
     */
    public int registerUser(User user) throws DaoException {
        int userId = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS);) {
            formUser(user, preparedStatement);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                resultSet.next();
                userId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during user's registration", e);
        }
        return userId;
    }

    @Override
    public int fetchUserId(User user) throws DaoException {
        ResultSet resultSet = null;
        int userId = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ID)) {
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getInt(Constants.USER_ID);
            }
        } catch (SQLException e) {
           throw new DaoException("Exception occurs during retrieving user id", e);
        }
        return userId;
    }

    @Override
    public void makeUserActive(int userId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(MAKE_USER_ACTIVE);
             PreparedStatement preparedStatement2 = connection.prepareStatement(APPROVE_COMMENT);) {

            transactionManager.startTransaction();
            preparedStatement1.setInt(1, userId);
            preparedStatement1.executeUpdate();

            preparedStatement2.setInt(1, userId);
            preparedStatement2.executeUpdate();

            transactionManager.commit();
            transactionManager.stopTransaction();
        } catch (SQLException e) {
            transactionManager.rollback();
            throw new DaoException("Exception occurs during set status to frozen", e);
        }
    }

    @Override

    public void frozeUser(int userId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(FROZE_USER);
             PreparedStatement preparedStatement2 = connection.prepareStatement(REJECT_COMMENT)) {

            transactionManager.startTransaction();

            preparedStatement1.setInt(1, userId);
            preparedStatement1.executeUpdate();

            preparedStatement2.setInt(1, userId);
            preparedStatement2.executeUpdate();

            transactionManager.commit();
            transactionManager.stopTransaction();
        } catch (SQLException e) {
            transactionManager.rollback();
            throw new DaoException("Exception occurs during set status to frozen", e);
        }
    }

    @Override
    public Status getStatus(int userId) throws DaoException {

        Status status = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    status = Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase());
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
        return status;
    }

    @Override
    public List<User> getAllUsersWithStatus(Status status, int currentPage, int usersPerPage) throws DaoException {

        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_WITH_STATUS);) {
            preparedStatement.setString(1, String.valueOf(status).toLowerCase());
            preparedStatement.setInt(2, usersPerPage);
            int startIndex = (currentPage - 1) * usersPerPage;
            preparedStatement.setInt(3, startIndex);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt(Constants.USER_ID));
                    user.setStatus(Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase()));
                    user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
                    user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
                    user.setEmail(resultSet.getString(Constants.USER_EMAIL));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        }
        return userList;
    }

    @Override
    public int getUserQuantatyByStatus(Status status) throws DaoException {

        int counter = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_STATUS_QUANTITY)) {
            preparedStatement.setString(1, String.valueOf(status).toLowerCase());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    counter = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return counter;
    }


    private void formUser(User user, PreparedStatement preparedStatement) throws SQLException {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String hashedPassword = PasswordHash.hash(user.getPassword());

        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, hashedPassword);
    }
}





