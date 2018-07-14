package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.MessageDAO;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.entity.*;
import by.corporation.final_project.util.Constants;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    Connection connection = null;

    public MessageDAOImpl(ConnectionPool connectionPool) {
        connection = connectionPool.getConnection();
    }

    private static final String SAVE_MESSAGE = "insert into message(mes_user_id, mes_message) values (?,?)";
    private static final String GET_MESSAGES = "select mes_id, mes_message, usr_firstname, usr_lastname, usr_email from message join user on mes_user_id = usr_id LIMIT ? OFFSET ?";
    private static final String GET_MESSAGE_QUANTITY = "SELECT COUNT(*) FROM message";
    private static final String DELETE_MESSAGE = "DELETE FROM message where mes_id = ?";

    @Override
    public int saveMessage(Message contactForm) throws DaoException {
        Integer userId = contactForm.getUserId();
        String message = contactForm.getMessage();
        Integer id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_MESSAGE, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, message);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving contact message", e);
        }
        return id;
    }

    @Override
    public List<Message> getAllMessages(int currentPage, int commentPerPage) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MESSAGES);) {
            preparedStatement.setInt(1, commentPerPage);
            int startIndex = (currentPage - 1) * commentPerPage;
            preparedStatement.setInt(2, startIndex);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setFirstName(resultSet.getString(Constants.FIRSTNAME));
                    user.setLastName(resultSet.getString(Constants.LASTNAME));
                    user.setEmail(resultSet.getString(Constants.USR_EMAIL));

                    Message message = new Message();
                    message.setMessage(resultSet.getString("mes_message"));
                    message.setId(resultSet.getInt("mes_id"));
                    message.setUser(user);

                    messages.add(message);
                }
            }
        }catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        }
        return messages;
    }

    @Override
    public int getMessageQuantity() throws DaoException {
        int counter = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_MESSAGE_QUANTITY)) {
            while (resultSet.next()) {
                counter =  resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
        }
        return counter;
    }

    @Override
    public void deleteMessage(int messageId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MESSAGE);) {
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during deleting message from db", e);
        }
    }
}
