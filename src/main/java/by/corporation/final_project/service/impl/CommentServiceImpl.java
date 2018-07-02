package by.corporation.final_project.service.impl;


import by.corporation.final_project.dao.mysql.CommentDAO;
import by.corporation.final_project.dao.DAOFactory;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Status;
import by.corporation.final_project.service.CommentService;
import by.corporation.final_project.service.exception.CommentSavingException;
import by.corporation.final_project.service.exception.ServiceException;


import java.util.List;

public class CommentServiceImpl implements CommentService {
    private static CommentService COMMENT_SERVICE = new CommentServiceImpl();

    public static CommentService getCommentService() {
        return COMMENT_SERVICE;
    }

    private CommentServiceImpl() {
    }

    private static CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();


    @Override
    public void saveComment(Comment comment) throws ServiceException, CommentSavingException {
        if (comment == null) {
            throw new CommentSavingException("Exception during saving commnet in service");
        }
        try {
            commentDAO.saveComment(comment);
        } catch (DaoException e) {
            throw new ServiceException("Exception during saving commnet in service", e);
        }
    }



    @Override
    public List<Comment> showAllComments() {
        List<Comment> comments = null;
        try {
            comments = commentDAO.getAllComment();
            if (comments.isEmpty()) {
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void setStatusToApprovedStatus(int com_id) throws ServiceException {
        Status status;
        try {
            status = commentDAO.getStatus(com_id);
            if (status.equals(Status.PENDING)) {
                commentDAO.setCommentToApproved(com_id);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }
    @Override
    public void setStatusToRejectedStatus(int com_id) throws ServiceException {
        Status status;
        try {
            status = commentDAO.getStatus(com_id);
            if (status.equals(Status.PENDING)) {
                commentDAO.setCommentToRejected(com_id);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }
}
