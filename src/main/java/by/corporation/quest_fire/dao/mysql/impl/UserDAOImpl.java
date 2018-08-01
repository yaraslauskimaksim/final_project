package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.AbstractDAO;
import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.mysql.UserDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.*;
import by.corporation.quest_fire.service.hash.PasswordHash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with user
 */
public class UserDAOImpl extends AbstractDAO implements UserDAO {

    public UserDAOImpl(PooledConnection connection){
        super(connection);
    }

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);


    private static final String SELECT_LOGGIN_USER = "select  usr_id, usr_firstname, usr_lastname, usr_email, usr_password, usr_status, rol_role from user join role on usr_role_id = rol_id where usr_email = ? and usr_password=?";
    private static final String CREATE_NEW_USER = "insert into user(usr_firstname,usr_lastname,usr_email, usr_password) values (?,?,?,?)";
    private static final String SELECT_USER_ID = "SELECT usr_id from user where usr_email=?";
    private static final String GET_ALL_USERS_WITH_STATUS = "SELECT usr_id, usr_status, usr_firstname, usr_lastname, usr_email FROM user WHERE usr_status = ? LIMIT ? OFFSET ?";
    private static final String GET_USER_BY_STATUS_QUANTITY = "SELECT COUNT(*) FROM user WHERE usr_status = ?";

    private static final String UPDATE_COMMENT = "UPDATE comment SET com_quest_id = ?, com_user_id = ?, com_description = ?, com_status = ?, com_date = ? WHERE com_id = ?";
    private static final String UPDATE_USER = "UPDATE user SET usr_firstname = ?, usr_lastname = ?,usr_password = ?, usr_status = ? WHERE usr_id = ? ";
    private static final String SELECT_USER_BY_ID = "select  usr_id, usr_firstname, usr_lastname, usr_email, usr_password, usr_status from user where usr_id = ?";



    /**
     * The method returns the {@link User} from db according to
     * @param email and
     * @param password
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} exception happens.
     */

    public User fetchUserByEmailPassword(String email, String password) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SELECT_LOGGIN_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getRow() == 0) {
                return null;
            }
            User user = formUser(resultSet);
            return user;
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during retrieving a user who is trying to log in");
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    /**
     * The method returns the generated id for user from db.
     *
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} exception happens.
     */
    public long create(User user) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long userId = 0;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            formUser(user, statement);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            userId = resultSet.getLong(1);
            return userId;
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during user's registration", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    @Override
    public long fetchUserId(User user) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
       long userId = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_USER_ID);
            statement.setString(1, user.getEmail());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getInt(Constants.USER_ID);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving user id", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return userId;
    }

    @Override
    public void update(User user, List<Comment> comments) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement1 = connection.prepareStatement(UPDATE_USER);
            statement1.setString(1, user.getFirstName());
            statement1.setString(2, user.getLastName());
            statement1.setString(3, user.getPassword());
            statement1.setString(4, String.valueOf(user.getStatus()).toLowerCase());
            statement1.setLong(5, user.getId());
            statement1.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to frozen", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(statement1);
                ConnectionPool.getInstance().closeStatement(statement2);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }



    @Override
    public User fetchById(long userId) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getRow() == 0) {
                return null;
            }
            User user = formUserWithoutRole(resultSet);
            return user;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving a user who is trying to log in");
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    @Override
    public List<User> getAllUsersWithStatus(Status status, int currentPage, int usersPerPage) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_USERS_WITH_STATUS);
            statement.setString(1, String.valueOf(status).toLowerCase());
            statement.setInt(2, usersPerPage);
            int startIndex = (currentPage - 1) * usersPerPage;
            statement.setInt(3, startIndex);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(Constants.USER_ID));
                user.setStatus(Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase()));
                user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
                user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
                user.setEmail(resultSet.getString(Constants.USER_EMAIL));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return userList;
    }

    @Override
    public int getUserQuantatyByStatus(Status status) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_USER_BY_STATUS_QUANTITY);
            statement.setString(1, String.valueOf(status).toLowerCase());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
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


    /**
     * The method returns the {@link User} for fetchUserByEmailPassword method.
     *
     * @throws SQLException
     */
    private User formUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setRole(Role.valueOf(resultSet.getString(Constants.ROLE).toUpperCase()));
        user.setId(resultSet.getInt(Constants.USER_ID));
        user.setEmail(resultSet.getString(Constants.USER_EMAIL));
        user.setPassword(resultSet.getString(Constants.USER_PASSWORD));
        user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
        user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
        user.setStatus(Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase()));
        return user;
    }


    private User formUserWithoutRole(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(Constants.USER_ID));
        user.setEmail(resultSet.getString(Constants.USER_EMAIL));
        user.setPassword(resultSet.getString(Constants.USER_PASSWORD));
        user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
        user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
        user.setStatus(Status.valueOf(resultSet.getString(Constants.STATUS).toUpperCase()));
        return user;
    }


}





