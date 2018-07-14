package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Status;

import java.util.List;

public interface CommentDAO {
        void saveComment(Comment comment) throws DaoException;
        List<Comment> getAllComment(int questPerPage, int currentPage ) throws DaoException;
        void setCommentToApproved(int commentId) throws DaoException;
        void deleteComment(int commentId) throws DaoException;
        Status getStatus(int commentId) throws DaoException;
        int getCommentQuantity() throws DaoException;
        List<Comment> getAllCommentBuQuestId(int questId) throws DaoException;

}
