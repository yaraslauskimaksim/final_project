package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.MessageService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.impl.MessageServiceImpl;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;


public class DeleteMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteMessageCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_ADMIN));
        int page = ControllerUtil.getCurrentPage(requestContent);
        Integer messageId = Integer.parseInt(requestContent.getRequestParameter(Constants.ID).trim());

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);

        if (role.equals(Role.ADMINISTRATOR)) {
            try {
                MessageService messageService = ServiceFactory.getInstance().getMessageService();
               messageService.deleteMessage(messageId);
                commandResult.setPage(Constants.PATH_SHOW_USER_MESSAGES + page);

            } catch (ServiceException e) {
                LOGGER.error("Exception occurs during deletion of user's message");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }

        return commandResult;
    }
}
