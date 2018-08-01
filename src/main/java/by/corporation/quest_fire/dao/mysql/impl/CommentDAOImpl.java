package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.AbstractDAO;
import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.mysql.CommentDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.entity.dto.CommentTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with comments
 */
public class CommentDAOImpl extends AbstractDAO implements CommentDAO {

    public CommentDAOImpl(PooledConnection connection) {
        super(connection);
    }

    private static final Logger LOGGER = LogManager.getLogger(CommentDAOImpl.class);

    private static final String SELECT_SINGLE_COMMENT = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, com_date from comment where com_id = ?";
    private static final String CREATE_NEW_COMMENT = "insert into comment(com_user_id, com_quest_id, com_description, com_date) values (?,?,?,?)";
    private static final String SELECT_ALL_COMMENTS = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, que_name, usr_firstname, usr_lastname from comment left join quest on quest.que_id = comment.com_quest_id left join user on user.usr_id = comment.com_user_id where com_status = 'pending' LIMIT ? OFFSET ?";
    private static final String SELECT_COMMENT_QUANTITY = "SELECT COUNT(*) FROM comment WHERE com_status = 'pending'";
    private static final String SELECT_ALL_COMMENTS_BY_QUEST_ID = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, que_name, usr_firstname, usr_lastname from comment left join quest on quest.que_id = comment.com_quest_id left join user on user.usr_id = comment.com_user_id where com_status = 'approved' and que_id = ?";
    private static final String UPDATE_COMMENT = "UPDATE comment SET com_user_id = ?, com_quest_id = ?, com_status = ?, com_description = ?, com_date = ?  WHERE com_id = ?";
    private static final String SELECT_ALL_COMMENTS_BY_USER_ID = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, com_date from comment WHERE com_user_id = ?";




    @Override
    public void update(Comment comment) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT);
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getQuestId());
            preparedStatement.setString(3, String.valueOf((comment.getStatus())).toLowerCase());
            preparedStatement.setString(4, comment.getDescription());
            preparedStatement.setTimestamp(5, comment.getTime());
            preparedStatement.setInt(6, comment.getCommentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    @Override
    public void update(List<Comment> comments) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT);
           formUpdatedComments(preparedStatement, comments);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    /**
     * The method returns the {@link Comment}.
     *
     * @param commentId is the constant for limiting comments per page
     * @throws DaoException
     */

    public Comment fetchById(int commentId) throws DaoException {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Comment comment = new Comment();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SINGLE_COMMENT);
            preparedStatement.setInt(1, commentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
                comment.setUserId(resultSet.getInt(Constants.COMMENT_USER_ID));
                comment.setQuestId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
                comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
                comment.setTime(resultSet.getTimestamp(Constants.COMMENT_DATE));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return comment;
    }



    /**
     * The method returns the generated id for comment from db.
     * It is responsible for create a comment and save it to db.
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} happens.
     */
    @Override
    public int create(Comment comment) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_NEW_COMMENT, Statement.RETURN_GENERATED_KEYS);
            formSavedComment(preparedStatement, comment);
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int commentId = resultSet.getInt(1);
            return commentId;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving a comment", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    /**
     * The method returns the collection of transfer {@link CommentTO}.
     *
     * @param commentPerPage is the constant for limiting comments per page
     * @param currentPage    is the page where user is currently
     * @throws DaoException
     */
    @Override
    public List<CommentTO> fetchAllComment(int currentPage, int commentPerPage) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<CommentTO> comments = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS);
            preparedStatement.setInt(1, commentPerPage);
            int startIndex = (currentPage - 1) * commentPerPage;
            preparedStatement.setInt(2, startIndex);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Quest quest = new Quest();
                quest.setName(resultSet.getString(Constants.QUEST_NAME));
                quest.setId(resultSet.getInt(Constants.COMMENT_QUEST_ID));

                User user = new User();
                user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
                user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
                user.setId(resultSet.getInt(Constants.COMMENT_USER_ID));

                CommentTO comment = new CommentTO();
                comment.setUser(user);
                comment.setQuest(quest);
                comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
                comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
                comments.add(comment);
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
        return comments;
    }

    @Override
    public int fetchQuantity() throws DaoException {
       PooledConnection connection = null;
        Statement statement = null;
        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COMMENT_QUANTITY);
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }

    @Override
    public List<CommentTO> fetchAllById(long questId) throws DaoException {
        PooledConnection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_COMMENTS_BY_QUEST_ID);
            statement.setLong(1, questId);
            resultSet = statement.executeQuery();
            List<CommentTO> comments = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
                user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
                user.setId(resultSet.getInt(Constants.COMMENT_USER_ID));
                Quest quest = new Quest();
                quest.setName(resultSet.getString(Constants.QUEST_NAME));
                quest.setId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
                CommentTO comment = new CommentTO();
                comment.setUser(user);
                comment.setQuest(quest);
                comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
                comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
                comments.add(comment);
            }

            return comments;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving all comments by quest id", e);
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
    public List<Comment> fetchAllByUserId(long userId) throws DaoException {
        PooledConnection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_COMMENTS_BY_USER_ID);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            List<Comment> comments = formCommentByUserId(resultSet);
            return comments;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving all comments by quest id", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    private List<CommentTO> formComment(ResultSet resultSet) throws SQLException {
        List<CommentTO> comments = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
            user.setLastName(resultSet.getString(Constants.USER_LASTNAME));
            user.setId(resultSet.getInt(Constants.COMMENT_USER_ID));
            Quest quest = new Quest();
            quest.setName(resultSet.getString(Constants.QUEST_NAME));
            quest.setId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
            CommentTO comment = new CommentTO();
            comment.setUser(user);
            comment.setQuest(quest);
            comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
            comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
            comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
            comments.add(comment);
        }
        return comments;
    }

    private List<Comment> formCommentByUserId(ResultSet resultSet) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setUserId(resultSet.getInt(Constants.COMMENT_USER_ID));
            comment.setQuestId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
            comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
            comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
            comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
            comment.setTime(resultSet.getTimestamp(Constants.COMMENT_DATE));
            comments.add(comment);
        }
        return comments;
    }


    /**
     * The method is used in 'create' method and gets
     * @param preparedStatement and
     * @param comment for saving comment to db.
     * @throws {@link SQLException}.
     */
    private void formSavedComment(PreparedStatement preparedStatement, Comment comment) throws SQLException {
        preparedStatement.setInt(1, comment.getUserId());
        preparedStatement.setInt(2, comment.getQuestId());
        preparedStatement.setString(3, comment.getDescription());
        preparedStatement.setTimestamp(4, comment.getTime());
        preparedStatement.executeUpdate();
    }

    /**
     * The method is used in 'create' method and gets
     * @param statement and
     * @param comments for updating all comments.
     * @throws {@link SQLException}.
     */
    private void formUpdatedComments(PreparedStatement statement, List<Comment> comments) throws SQLException {
        for (Comment comment : comments) {
            statement.setInt(1, comment.getQuestId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getDescription());
            statement.setString(4, String.valueOf(comment.getStatus()).toLowerCase());
            statement.setTimestamp(5, comment.getTime());
            statement.setInt(6, comment.getCommentId());
            statement.addBatch();
        }
        statement.executeBatch();
    }
}
