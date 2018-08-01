package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.dto.CommentTO;

import java.util.List;

public interface CommentDAO {

    void update(Comment comment) throws DaoException;

    void update(List<Comment> comments) throws DaoException;

    int create(Comment comment) throws DaoException;

    List<CommentTO> fetchAllComment(int questPerPage, int currentPage) throws DaoException;

    int fetchQuantity() throws DaoException;

    List<CommentTO> fetchAllById(long questId) throws DaoException;

    Comment fetchById(int commentId) throws DaoException;

    List<Comment> fetchAllByUserId(long userId) throws DaoException;
}
