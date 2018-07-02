package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.UserDAO;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.hash.PasswordHash;


import java.sql.*;

public class UserDAOImpl  implements UserDAO {

    Connection connection = null;

    public UserDAOImpl(ConnectionPool connectionPool) {
        connection = connectionPool.getConnection();
    }

    //private static final String SELECT_EMAIL_PASSWORD_FOR_IDENTIFYING_USER = "select usr_email, usr_password from user where usr_email = ? and usr_password=?";
    private static final String SELECT_EMAIL_PASSWORD_ROLE_FOR_IDENTIFYING_USER = "select  usr_id, usr_firstname, usr_lastname, usr_email, usr_password, rol_role from user join role on usr_role_id = rol_id where usr_email = ? and usr_password=?";
    private static final String CREATE_NEW_USER = "insert into user(usr_firstname,usr_lastname,usr_email, usr_password) values (?,?,?,?)";
    private static final String IS_LOGIN_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE usr_email =?)";


    public User getUser(String email, String password) throws DaoException {
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_PASSWORD_ROLE_FOR_IDENTIFYING_USER);) {
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

}


