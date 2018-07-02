package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Status;

import java.util.List;

public interface CommentDAO {
        void saveComment(Comment comment) throws DaoException;
        List<Comment> getAllComment() throws DaoException;
        void setCommentToApproved(int commentId) throws DaoException;
        void setCommentToRejected(int commentId) throws DaoException;
        Status getStatus(int commentId) throws DaoException;

}
