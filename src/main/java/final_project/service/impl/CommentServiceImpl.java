package final_project.service.impl;


import final_project.dao.CommentDAO;
import final_project.dao.DAOFactory;
import final_project.dao.db.SqlDaoException;
import final_project.entity.Comment;
import final_project.service.CommentService;
import final_project.service.exception.CommentSavingException;
import final_project.service.exception.ServiceException;

public class CommentServiceImpl implements CommentService{

    private static CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();

    @Override
    public void saveComment(Comment comment) throws ServiceException, CommentSavingException {
        if(comment ==null){
            throw new CommentSavingException("Exception during saving commnet in service");
        }
        try {
            commentDAO.saveComment(comment);
        } catch (SqlDaoException e) {
            throw new ServiceException("Exception during saving commnet in service", e);
        }

    }
}
