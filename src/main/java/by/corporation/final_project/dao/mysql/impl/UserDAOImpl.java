package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.UserDAO;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.entity.*;
import by.corporation.final_project.service.hash.PasswordHash;
import by.corporation.final_project.util.Constants;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    Connection connection = null;

    public UserDAOImpl(ConnectionPool connectionPool) {
        connection = connectionPool.getConnection();
    }

    //private static final String SELECT_EMAIL_PASSWORD_FOR_IDENTIFYING_USER = "select usr_email, usr_password from user where usr_email = ? and usr_password=?";
    private static final String SELECT_EMAIL_PASSWORD_ROLE_STATUS_FOR_IDENTIFYING_USER = "select  usr_id, usr_firstname, usr_lastname, usr_email, usr_password, usr_status, rol_role from user join role on usr_role_id = rol_id where usr_email = ? and usr_password=?";
    private static final String CREATE_NEW_USER = "insert into user(usr_firstname,usr_lastname,usr_email, usr_password) values (?,?,?,?)";
    private static final String IS_LOGIN_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE usr_email =?)";
    private static final String MAKE_USER_ACTIVE = "UPDATE user SET usr_status = 'active' WHERE usr_id = ?";
    private static final String FROZE_USER = "UPDATE user SET usr_status = 'frozen' WHERE usr_id = ?";
    private static final String SELECT_STATUS = "SELECT usr_id, usr_status FROM user WHERE usr_id = ?";
    private static final String GET_ALL_USERS_WITH_STATUS = "SELECT usr_id, usr_status, usr_firstname, usr_lastname, usr_email FROM user WHERE usr_status = ? LIMIT ? OFFSET ?";
    private static final String GET_USER_BY_STATUS_QUANTITY = "SELECT COUNT(*) FROM user WHERE usr_status = ?";
    private static final String REJECT_COMMENT = "UPDATE comment SET com_status = 'rejected' WHERE com_user_id = ?";
    private static final String APPROVE_COMMENT = "UPDATE comment SET com_status = 'approved' WHERE com_user_id = ?";

    public User getUser(String email, String password) throws DaoException {
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_PASSWORD_ROLE_STATUS_FOR_IDENTIFYING_USER);) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                resultSet.next();
                if (resultSet.getRow() == 0) {
                    return null;
                }
                user.setRole(Role.valueOf(resultSet.getString("rol_role").toUpperCase()));
                user.setId(resultSet.getInt("usr_id"));
                user.setEmail(resultSet.getString("usr_email"));
                user.setPassword(resultSet.getString("usr_password"));
                user.setFirstName(resultSet.getString("usr_firstname"));
                user.setLastName(resultSet.getString("usr_lastname"));
                user.setStatus(Status.valueOf(resultSet.getString("usr_status").toUpperCase()));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int registerUser(User user) throws DaoException {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String hashedPassword = PasswordHash.hash(user.getPassword());

        int userId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
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
    public boolean isEmailExists(User user) {
        boolean exists = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(IS_LOGIN_EXISTS);) {
            preparedStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                resultSet.next();
                exists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @Override
    public void makeUserActive(int userId) throws DaoException {
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(MAKE_USER_ACTIVE);
             PreparedStatement preparedStatement2 = connection.prepareStatement(APPROVE_COMMENT);) {
            connection.setAutoCommit(false);

            preparedStatement1.setInt(1, userId);
            preparedStatement1.executeUpdate();

            preparedStatement2.setInt(1, userId);
            preparedStatement2.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException("Exception during rollback transaction of frozen user and comments", e1);
            }
            throw new DaoException("Exception occurs during set status to frozen", e);
        }
    }

    @Override

    public void frozeUser(int userId) throws DaoException {
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(FROZE_USER);
        PreparedStatement preparedStatement2 = connection.prepareStatement(REJECT_COMMENT)) {
            connection.setAutoCommit(false);

            preparedStatement1.setInt(1, userId);
            preparedStatement1.executeUpdate();

            preparedStatement2.setInt(1, userId);
            preparedStatement2.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException("Exception during rollback transaction of frozen user and comments", e1);
            }
            throw new DaoException("Exception occurs during set status to frozen", e);
        }
    }

    @Override
    public Status getStatus(int userId) throws DaoException {
        Status status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    status = Status.valueOf(resultSet.getString(Constants.USR_STATUS).toUpperCase());
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_WITH_STATUS);) {
            preparedStatement.setString(1, String.valueOf(status).toLowerCase());
            preparedStatement.setInt(2, usersPerPage);
            int startIndex = (currentPage - 1) * usersPerPage;
            preparedStatement.setInt(3, startIndex);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt(Constants.USR_ID));
                    user.setStatus(Status.valueOf(resultSet.getString(Constants.USR_STATUS).toUpperCase()));
                    user.setFirstName(resultSet.getString(Constants.FIRSTNAME));
                    user.setLastName(resultSet.getString(Constants.LASTNAME));
                    user.setEmail(resultSet.getString(Constants.USR_EMAIL));
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_STATUS_QUANTITY)) {
            preparedStatement.setString(1, String.valueOf(status).toLowerCase());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    counter =  resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return counter;
    }
}





