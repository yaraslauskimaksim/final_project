package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;

import java.util.List;

public interface CommentDAO {
        int saveComment(Comment comment) throws DaoException;
        List<Comment> getAllComment(int questPerPage, int currentPage ) throws DaoException;
        void setCommentToApproved(int commentId) throws DaoException;
        void deleteComment(int commentId) throws DaoException;
        Status getStatus(int commentId) throws DaoException;
        int getCommentQuantity() throws DaoException;
        List<Comment> getAllCommentBuQuestId(int questId) throws DaoException;

}
