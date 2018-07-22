package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.mysql.CommentDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with comments
 */
public class CommentDAOImpl implements CommentDAO {

    private static final Logger LOGGER = LogManager.getLogger(CommentDAOImpl.class);

    private static final String SAVE_NEW_COMMENT = "insert into comment(com_user_id, com_quest_id, com_description, com_date) values (?,?,?,?)";
    private static final String SELECT_ALL_COMMENTS = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, que_name, usr_firstname, usr_lastname from comment left join quest on quest.que_id = comment.com_quest_id left join user on user.usr_id = comment.com_user_id where com_status = 'pending' LIMIT ? OFFSET ?";
    private static final String APPROVE_COMMENT = "UPDATE  comment SET com_status = 'approved' WHERE com_id = ?";
    private static final String REJECT_COMMENT = "DELETE from comment WHERE com_id = ?";
    private static final String SELECT_STATUS = "SELECT com_id, com_status FROM comment WHERE com_id = ?";
    private static final String GET_COMMENT_QUANTITY = "SELECT COUNT(*) FROM comment WHERE com_status = 'pending'";
    private static final String SELECT_ALL_COMMENTS_BY_QUEST_ID = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, que_name, usr_firstname, usr_lastname from comment left join quest on quest.que_id = comment.com_quest_id left join user on user.usr_id = comment.com_user_id where com_status = 'approved' and que_id = ?";


    /**
     * The method returns the generated id for comment from db.
     *
     * @throws DaoException the dao exception
     */
    @Override
    public int saveComment(Comment comment) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_COMMENT, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getQuestId());
            preparedStatement.setString(3, comment.getDescription());
            preparedStatement.setTimestamp(4, comment.getTime());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int commentId = resultSet.getInt(1);
            return commentId;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving a comment", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    @Override
    public List<Comment> getAllComment(int currentPage, int commentPerPage) throws DaoException {

        List<Comment> comments = new ArrayList<>();
        try (
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS);) {
            preparedStatement.setInt(1, commentPerPage);
            int startIndex = (currentPage - 1) * commentPerPage;
            preparedStatement.setInt(2, startIndex);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    Quest quest = new Quest();
                    User user = new User();
                    user.setFirstName(resultSet.getString(Constants.FIRSTNAME));
                    user.setLastName(resultSet.getString(Constants.LASTNAME));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    comment.setUser(user);
                    comment.setQuest(quest);
                    comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
                    comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                    comment.setQuestId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
                    comment.setUserId(resultSet.getInt(Constants.COMMENT_USER_ID));
                    comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        }
        return comments;
    }


    @Override
    public void setCommentToApproved(int commentId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(APPROVE_COMMENT);
        ) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void deleteComment(int commentId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REJECT_COMMENT);) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
    }

    @Override
    public Status getStatus(int commentId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);) {
            preparedStatement.setInt(1, commentId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                    return comment.getStatus();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
        return null;
    }

    @Override
    public int getCommentQuantity() throws DaoException {
        int counter = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_COMMENT_QUANTITY)) {
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
        }
        return counter;
    }

    @Override
    public List<Comment> getAllCommentBuQuestId(int questId) throws DaoException {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_BY_QUEST_ID);) {
            preparedStatement.setInt(1, questId);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    Quest quest = new Quest();
                    User user = new User();
                    user.setFirstName(resultSet.getString(Constants.FIRSTNAME));
                    user.setLastName(resultSet.getString(Constants.LASTNAME));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    comment.setUser(user);
                    comment.setQuest(quest);
                    comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
                    comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                    comment.setQuestId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
                    comment.setUserId(resultSet.getInt(Constants.COMMENT_USER_ID));
                    comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        }
        return comments;
    }
}
