package final_project.service;

import final_project.entity.Comment;
import final_project.service.exception.CommentSavingException;
import final_project.service.exception.ServiceException;

public interface CommentService {
    void saveComment(Comment comment) throws ServiceException, CommentSavingException;
}
