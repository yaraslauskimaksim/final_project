package final_project.dao;

import final_project.dao.db.SqlDaoException;
import final_project.entity.Comment;

public interface CommentDAO {
    void saveComment(Comment comment) throws SqlDaoException;
}
