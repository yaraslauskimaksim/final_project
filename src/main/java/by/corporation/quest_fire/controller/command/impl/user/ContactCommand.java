package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Message;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.MessageService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.impl.MessageServiceImpl;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;
/**
 * This command class is responsible for sending messages to administrator
 */
public class ContactCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ContactCommand.class);
    /**
     * This command returns the {@link CommandResult}.
     * User can send a message for an administrator.
     */
    @Override
    public CommandResult execute(RequestContent requestContent)  {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_QUEST_SINGLE));
        Message message = formMessage(requestContent);
        try {
            MessageService messageService = ServiceFactory.getInstance().getMessageService();
            messageService.saveMessage(message);
            commandResult.putSessionAttribute(Constants.SUCCESS, Constants.MESSAGE_WAS_SENT);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_FROZEN_USER));
        } catch (ServiceException e) {
            LOGGER.error("Message was not sent!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
       return commandResult;
    }

    /**
     * This method returns the user the {@link Message}.
     *
     * @param requestContent is received from message form
     *                       and consists of
     *                       userId, message
     */
    private Message formMessage(RequestContent requestContent){
        Integer userId = (Integer) requestContent.getSessionAttribute(Constants.USER_ID);
        String message = requestContent.getRequestParameter(Constants.MESSAGE);
        Message contactForm = new Message();
        contactForm.setUserId(userId);
        contactForm.setMessage(message);
        return contactForm;
    }
}