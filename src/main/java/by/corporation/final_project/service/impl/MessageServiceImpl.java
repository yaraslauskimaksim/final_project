package by.corporation.final_project.service.impl;

import by.corporation.final_project.dao.DAOFactory;
import by.corporation.final_project.dao.mysql.MessageDAO;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.entity.Message;

import by.corporation.final_project.service.MessageService;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.util.Constants;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    private static MessageService CONTACT_FORM_SERVICE = new MessageServiceImpl();

    public static MessageService getContactFormService() {
        return CONTACT_FORM_SERVICE;
    }

    private MessageServiceImpl() {
    }

    private static MessageDAO messageDAO = DAOFactory.getInstance().getMessageDAO();
    @Override
    public int saveMessage(Message message) throws ServiceException {
        int id = 0;
        try {
            id = messageDAO.saveMessage(message);
        } catch (DaoException e) {
           throw new ServiceException("", e);
        }
        return id;
    }

    @Override
    public List<Message> getAllMessages(int currentPage) throws ServiceException {
        List<Message> messages = null;
        try {
            messages= messageDAO.getAllMessages(currentPage, Constants.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return messages;

    }

    @Override
    public int getMessageQuantity() throws ServiceException {
        int counter = 0;
        try {
            counter = messageDAO.getMessageQuantity();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return counter;
    }

    @Override
    public void deleteMessage(int messageId) throws ServiceException {
        try {
            messageDAO.deleteMessage(messageId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
