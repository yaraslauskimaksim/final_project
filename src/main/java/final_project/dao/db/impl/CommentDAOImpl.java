package final_project.dao.db.impl;

import final_project.dao.CommentDAO;
import final_project.dao.db.ConnectionDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.pool.ConnectionPool;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentDAOImpl extends ConnectionDAO implements CommentDAO {

    private static final String SAVE_NEW_COMMNET= "insert into comment(com_user_id, com_quest_id, com_description, com_is_approved) values (?,?,?,?)";

    @Override
    public void saveComment(Comment comment) throws SqlDaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int user_id = comment.getUserId();
        int quest_id = comment.getQuestId();
        String description = comment.getDescription();
        boolean isApproved = comment.isApproved();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SAVE_NEW_COMMNET);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, quest_id);
            preparedStatement.setString(3, description);
            preparedStatement.setBoolean(4, isApproved);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
           throw new SqlDaoException("Exception occurs during saving a comment", e);
        } finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
