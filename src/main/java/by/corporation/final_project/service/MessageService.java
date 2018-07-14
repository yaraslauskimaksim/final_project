package by.corporation.final_project.service;

import by.corporation.final_project.entity.Message;
import by.corporation.final_project.service.exception.ServiceException;

import java.util.List;

public interface MessageService {
    int saveMessage(Message message) throws ServiceException;
    List<Message> getAllMessages(int currentPage) throws ServiceException;
    int getMessageQuantity() throws ServiceException;
    void deleteMessage(int messageId) throws ServiceException;
}
