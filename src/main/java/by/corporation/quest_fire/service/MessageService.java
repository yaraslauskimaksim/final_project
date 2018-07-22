package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Message;
import by.corporation.quest_fire.service.exception.ServiceException;

import java.util.List;

public interface MessageService {
    int saveMessage(Message message) throws ServiceException;
    List<Message> getAllMessages(int currentPage) throws ServiceException;
    int fetchNumberOfPages();
    void deleteMessage(int messageId) throws ServiceException;
}
