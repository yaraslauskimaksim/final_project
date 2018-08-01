package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.dto.CommentTO;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;


import java.util.List;

public interface CommentService {
    int saveComment(Comment comment) throws ServiceException, ValidationException;
    List<CommentTO> fetchAllComments(int currentPage) throws ServiceException;
    List<CommentTO> fetchAllCommentsByQuestId(long questId) throws ServiceException;
    int fetchNumberOfPages() throws ServiceException;
    void approveComment(int commentId) throws ServiceException;
    void rejectComment(int commentId) throws ServiceException;


}
