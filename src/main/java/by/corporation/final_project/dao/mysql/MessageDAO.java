package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.entity.Message;

import java.util.List;

public interface MessageDAO {

    int saveMessage(Message message) throws DaoException;
    List<Message> getAllMessages(int currentPage, int commentPerPage) throws DaoException;
    int getMessageQuantity() throws DaoException;
    void deleteMessage(int messageId) throws DaoException;
}
