package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Message;

import java.util.List;

public interface MessageDAO {

    int saveMessage(Message message) throws DaoException;
    List<Message> getAllMessages(int currentPage, int commentPerPage) throws DaoException;
    int getMessageQuantity() throws DaoException;
    void deleteMessage(int messageId) throws DaoException;
}
