package by.corporation.quest_fire.service.impl;

import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.MessageDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Message;

import by.corporation.quest_fire.service.MessageService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.util.Constants;
import by.corporation.quest_fire.service.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LogManager.getLogger(MessageServiceImpl.class);

    private static MessageDAO messageDAO = DAOFactory.getInstance().getMessageDAO();

    /**
     * The method returns the auto generated messageId
     *
     * @param message is needed to be saved
     * @throws ServiceException the service exception
     */
    @Override
    public int saveMessage(Message message) throws ServiceException {
        int id = 0;
        try {
            id = messageDAO.saveMessage(message);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during saving comment", e);
        }
        return id;
    }

    @Override
    public List<Message> fetchAllMessages(int currentPage) throws ServiceException {
        List<Message> messages = null;
        try {
            messages = messageDAO.getAllMessages(currentPage, Constants.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return messages;

    }

    @Override
    public void deleteMessage(int messageId) throws ServiceException {
        try {
            messageDAO.deleteMessage(messageId);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during deletion of user's message", e);
        }
    }

    /**
     * The method returns the number of pages for pagination
     */
    @Override
    public int fetchNumberOfPages() {
        int messageQuantity = fetchMessageQuantity();
        int numberOfPages = ServiceUtil.getNumberOfPage(messageQuantity, Constants.ITEMS_PER_PAGE);
        return numberOfPages;
    }
    /**
     * The method returns the quantity iof messages for pagination.
     */
    private int fetchMessageQuantity() {
        int counter = 0;
        try {
            counter = messageDAO.getMessageQuantity();
        } catch (DaoException e) {
            LOGGER.error("Exception during retrieving message quantity", e);
        }
        return counter;
    }
}
