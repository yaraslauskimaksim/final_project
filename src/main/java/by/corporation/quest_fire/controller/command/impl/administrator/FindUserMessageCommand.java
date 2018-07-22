package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Message;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.MessageService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

/**
 * This command class is responsible for finding frozen users' messages
 */
public class FindUserMessageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindUserMessageCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * Administrator is able to view messages from frozen users
     * pagination is available for messages
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(FORWARD, requestContent.getReferer());
        int page = ControllerUtil.getCurrentPage(requestContent);
        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        if (role.equals(Role.ADMINISTRATOR)) {
            List<Message> messages = null;
            try {
                MessageService messageService = ServiceFactory.getInstance().getMessageService();
                messages = messageService.getAllMessages(page);
                if (!messages.isEmpty()) {
                    int numberOfPages = messageService.fetchNumberOfPages();
                    commandResult.putRequestAttribute(Constants.MESSAGES, messages);
                    commandResult.putRequestAttribute(Constants.PAGE, page);
                    commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_MESSAGE));
                } else {
                    commandResult.putRequestAttribute(Constants.EMPTY_LIST, Constants.EMPTY_MESSAGE_LIST);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_MESSAGE));
                }
            } catch (ServiceException e) {
                LOGGER.error("Users' messages can't be seen");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }
        return commandResult;
    }
}
