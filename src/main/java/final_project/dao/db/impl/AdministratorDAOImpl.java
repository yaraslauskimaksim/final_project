package final_project.dao.db.impl;


import final_project.dao.AdministratorDAO;
import final_project.dao.CommentDAO;
import final_project.dao.db.ConnectionDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.pool.ConnectionPool;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Comment;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDAOImpl extends ConnectionDAO implements AdministratorDAO {

    private static final String SELECT_ALL_COMMENTS =  "select com_description from comment";

    @Override
    public List<Comment> getAllComment() throws SqlDaoException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        List<Comment> comments = new ArrayList<>();
        try{
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_COMMENTS);
            while (resultSet.next()) {
               Comment comment = new Comment();
               comment.setDescription(resultSet.getString("com_description"));
               comments.add(comment);

            }
    } catch (SQLException | ConnectionPoolException e) {
        throw new SqlDaoException("Exception occurs during getting all commnets", e);
    } finally {
        try {
            closeConnection(connection);
            ConnectionPool.getInstance().closeDB(resultSet, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return comments;
    }
}
