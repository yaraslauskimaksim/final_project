package by.corporation.final_project.service;

import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.service.exception.CommentSavingException;
import by.corporation.final_project.service.exception.ServiceException;


import java.util.List;

public interface CommentService {
    void saveComment(Comment comment) throws ServiceException, CommentSavingException;
    void setStatusToApprovedStatus(int com_id) throws ServiceException;
    void setStatusToRejectedStatus(int com_id) throws ServiceException;
    List<Comment> showAllComments();
}
