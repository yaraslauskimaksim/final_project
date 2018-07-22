package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;


import java.util.List;

public interface CommentService {
    int saveComment(Comment comment) throws ServiceException, ValidationException;
    void setStatusToApprovedStatus(int com_id) throws ServiceException;
    void setStatusToRejectedStatus(int com_id) throws ServiceException;
    List<Comment> showAllComments(int currentPage) throws ServiceException;
    int getCommentQuantity() throws ServiceException;
    List<Comment> showAllCommentsByQuestId(int questId) throws ServiceException;
}
