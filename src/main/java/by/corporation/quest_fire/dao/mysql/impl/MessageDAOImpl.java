package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.mysql.MessageDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.entity.*;
import by.corporation.quest_fire.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with messages
 */
public class MessageDAOImpl implements MessageDAO {

    private static final Logger LOGGER = LogManager.getLogger(MessageDAOImpl.class);

    private static final String SAVE_MESSAGE = "insert into message(mes_user_id, mes_message) values (?,?)";
    private static final String GET_MESSAGES = "select mes_id, mes_message, usr_firstname, usr_lastname, usr_email from message join user on mes_user_id = usr_id LIMIT ? OFFSET ?";
    private static final String GET_MESSAGE_QUANTITY = "SELECT COUNT(*) FROM message";
    private static final String DELETE_MESSAGE = "DELETE FROM message where mes_id = ?";

    /**
     * The method returns the collection of {@link Message} from db.
     *
     * @param contactForm is userID and message
     * @throws DaoException the dao exception
     */
    @Override
    public int saveMessage(Message contactForm) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id = 0;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SAVE_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, contactForm.getUserId());
            preparedStatement.setString(2, contactForm.getMessage());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving contact message", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return id;
    }

    @Override
    public List<Message> getAllMessages(int currentPage, int commentPerPage) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Message> messages = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_MESSAGES);
            preparedStatement.setInt(1, commentPerPage);
            int startIndex = (currentPage - 1) * commentPerPage;
            preparedStatement.setInt(2, startIndex);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString(Constant.FIRSTNAME));
                user.setLastName(resultSet.getString(Constant.LASTNAME));
                user.setEmail(resultSet.getString(Constant.USR_EMAIL));

                Message message = new Message();
                message.setMessage(resultSet.getString("mes_message"));
                message.setId(resultSet.getInt("mes_id"));
                message.setUser(user);

                messages.add(message);

            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return messages;
    }

    @Override
    public int getMessageQuantity() throws DaoException {
        PooledConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int counter = 0;
        try{ connection = ConnectionPool.getInstance().getConnection();
             statement = connection.createStatement();
             resultSet = statement.executeQuery(GET_MESSAGE_QUANTITY);
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
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

    @Override
    public void deleteMessage(int messageId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MESSAGE);) {
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during deleting message from db", e);
        }
    }
}
