package by.corporation.quest_fire.service.impl;


import by.corporation.quest_fire.dao.mysql.CommentDAO;
import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.util.Constants;


import java.util.List;

public class CommentServiceImpl implements CommentService {

    private static CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();

    /**
     * The method returns the auto generated id for comment and save the comment
     * @param comment is received from comment form
     * @throws ValidationException       if user does not fill in anithing
     * @throws ServiceException          the service exception
     */
    @Override
    public int saveComment(Comment comment) throws ServiceException, ValidationException {
        int commentId = 0;
        if (comment == null & comment.getDescription().equals("")) {
            throw new ValidationException("Exception during saving empty comment");
        }
        try {
            commentId = commentDAO.saveComment(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception during saving comment", e);
        }
        return commentId;
    }



    @Override
    public List<Comment> showAllComments(int currentPage) throws ServiceException {
        List<Comment> comments = null;
        try {
            comments = commentDAO.getAllComment(currentPage, Constants.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return comments;
    }

    @Override
    public List<Comment> showAllCommentsByQuestId(int questId) throws ServiceException {
        List<Comment> comments = null;
        try {
            comments = commentDAO.getAllCommentBuQuestId(questId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return comments;
    }

    @Override
    public int getCommentQuantity() throws ServiceException {
        int counter = 0;
        try {
            counter = commentDAO.getCommentQuantity();
        } catch (DaoException e) {
           throw new ServiceException("", e);
        }
        return counter;
    }

    public void setStatusToApprovedStatus(int comId) throws ServiceException {
        Status status = null;
        try {
            status = commentDAO.getStatus(comId);
            if (status.equals(Status.PENDING)) {
                commentDAO.setCommentToApproved(comId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting to approve status", e);
        }

    }
    @Override
    public void setStatusToRejectedStatus(int comId) throws ServiceException {
        Status status;
        try {
            status = commentDAO.getStatus(comId);
            if (status.equals(Status.PENDING)) {
                commentDAO.deleteComment(comId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting to rejected status", e);
        }

    }
}
