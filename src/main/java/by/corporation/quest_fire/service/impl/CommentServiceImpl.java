package by.corporation.quest_fire.service.impl;


import by.corporation.quest_fire.dao.mysql.CommentDAO;
import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.dto.CommentTO;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.service.util.ServiceUtil;
import by.corporation.quest_fire.util.Constant;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private static CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();

    /**
     * The method returns the auto generated id for comment and save the comment
     *
     * @param comment is received from comment form
     * @throws ValidationException if user does not fill in anithing
     * @throws ServiceException
     */
    @Override
    public int saveComment(Comment comment) throws ServiceException, ValidationException {
        int commentId = 0;
        if (comment == null & comment.getDescription().equals("")) {
            throw new ValidationException("Exception during saving empty comment");
        }
        try {
            commentId = commentDAO.create(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception during saving comment", e);
        }
        return commentId;
    }


    /**
     * The method returns the collection {@link CommentTO} and
     * transfers it to Controller layer.
     *
     * @param currentPage is where user currently is.
     * @throws ServiceException
     */
    @Override
    public List<CommentTO> fetchAllComments(int currentPage) throws ServiceException {
        List<CommentTO> comments = null;
        try {
            comments = commentDAO.fetchAllComment(currentPage, Constant.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving all comments", e);
        }
        return comments;
    }

    @Override
    public List<CommentTO> fetchAllCommentsByQuestId(int questId) throws ServiceException {
        List<CommentTO> comments = null;
        try {
            comments = commentDAO.fetchAllById(questId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return comments;
    }

    @Override
    public int fetchNumberOfPages() throws ServiceException {
        int numberOfRecords = fetchCommentQuantity();
        int numberOfPages = ServiceUtil.getNumberOfPage(numberOfRecords, Constant.ITEMS_PER_PAGE);
        return numberOfPages;
    }


    @Override
    public void approveComment(int commentId) throws ServiceException {
        try {
            Comment comment = fetchComment(commentId);
            Status status = comment.getStatus();
            if (status.equals(Status.PENDING)) {
                comment.setStatus(Status.APPROVED);
                commentDAO.update(comment);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting the comment to approveComment status", e);
        }
    }

    @Override
    public void rejectComment(int commentId) throws ServiceException {
        try {
            Comment comment = fetchComment(commentId);
            Status status = comment.getStatus();
            if (status.equals(Status.PENDING)) {
                comment.setStatus(Status.REJECTED);
                commentDAO.update(comment);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting the comment to rejected status", e);
        }

    }

    private Comment fetchComment(int commentId) throws DaoException {
        Comment comment = commentDAO.fetchById(commentId);
        return comment;
    }

    private int fetchCommentQuantity() throws ServiceException {
        int counter = 0;
        try {
            counter = commentDAO.fetchQuantity();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return counter;
    }

}
