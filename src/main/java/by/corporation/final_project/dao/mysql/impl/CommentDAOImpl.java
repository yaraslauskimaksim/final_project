package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.CommentDAO;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.entity.Status;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    Connection connection = null;

    public CommentDAOImpl(ConnectionPool connectionPool) {
        connection =  connectionPool.getConnection();
    }

    private static final String SAVE_NEW_COMMENT= "insert into comment(com_user_id, com_quest_id, com_description) values (?,?,?)";
    private static final String SELECT_ALL_COMMENTS = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status, que_name, usr_firstname, usr_lastname from comment left join quest on quest.que_id = comment.com_quest_id left join user on user.usr_id = comment.com_user_id";
    private static final String APPROVE_COMMENT = "UPDATE  comment SET com_status = 'approved' WHERE com_id = ?";
    private static final String REJECT_COMMENT = "UPDATE  comment SET com_status = 'rejected' WHERE com_id = ?";
    private static final String SELECT_STATUS = "SELECT com_id, com_status FROM comment WHERE com_id = ?";

    @Override
    public void saveComment(Comment comment) throws DaoException {

        int user_id = comment.getUserId();
        int quest_id = comment.getQuestId();
        String description = comment.getDescription();
        // Status status = comment.getStatus();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_COMMENT);){
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, quest_id);
            preparedStatement.setString(3, description);
            // preparedStatement.setString(4, (String.valueOf(status).toLowerCase()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving a comment", e);
        }
    }

    @Override
    public List<Comment> getAllComment() throws DaoException {
        List<Comment> comments = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_COMMENTS);) {
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
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during getting all commnets", e);
        }
        return comments;
    }


    @Override
    public void setCommentToApproved(int commentId) throws DaoException {
        try (PreparedStatement  preparedStatement = connection.prepareStatement(APPROVE_COMMENT);){
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void setCommentToRejected(int commentId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REJECT_COMMENT);){
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
    }

    @Override
    public Status getStatus(int commentId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);){
            preparedStatement.setInt(1, commentId);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
                    return comment.getStatus();
                }
            }
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
        return null;
    }

}
