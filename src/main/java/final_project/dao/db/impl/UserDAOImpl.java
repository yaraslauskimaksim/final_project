package final_project.dao.db.impl;

import final_project.dao.db.ConnectionDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.UserDAO;
import final_project.dao.pool.ConnectionPool;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Role;
import final_project.entity.User;
import final_project.entity.UserDetails;

import java.sql.*;

public class UserDAOImpl extends ConnectionDAO implements UserDAO {


    public  UserDAOImpl(){
    }
    public UserDAOImpl(Connection connection){
        super(connection);
    }



    private static final String SELECT_EMAIL_PASSWORD_FOR_IDENTIFYING_USER = "select usr_email, usr_password from user where usr_email = ? and usr_password=?";
    private static final String SELECT_EMAIL_PASSWORD_ROLE_FOR_IDENTIFYING_USER = "select  usr_id, rol_role from user join role on usr_role_id = rol_id where usr_email = ? and usr_password=?";
    private static final String CREATE_NEW_USER= "insert into user(usr_firstname,usr_lastname,usr_email, usr_password) values (?,?,?,?)";


    public UserDetails readByPasswordEmailRole(String email, String password) throws SqlDaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EMAIL_PASSWORD_ROLE_FOR_IDENTIFYING_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setRole(Role.valueOf(resultSet.getString("rol_role").toUpperCase()));
                userDetails.setId(resultSet.getInt("usr_id"));
                return userDetails;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new SqlDaoException("Exception occurs during reading password, email and role for authentication", e);
        } finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public UserDetails readByPasswordEmail(String email, String password) throws SqlDaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_EMAIL_PASSWORD_FOR_IDENTIFYING_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setEmail(resultSet.getString("usr_email"));
                userDetails.setPassword(resultSet.getString("usr_password"));
                return userDetails;
            }
        } catch (SQLException | ConnectionPoolException e) {
           throw new SqlDaoException("Exception occurs during reading password and email for authentication", e);
        }  finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(resultSet,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void registerUser(UserDetails userDetails, User user) throws SqlDaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = userDetails.getEmail();
        String password = userDetails.getPassword();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(CREATE_NEW_USER);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new SqlDaoException("Exception occurs during user's registration", e);
        }finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
