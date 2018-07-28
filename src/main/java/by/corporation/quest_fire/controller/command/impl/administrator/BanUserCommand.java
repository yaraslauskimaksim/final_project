package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for setting user's status to 'frozen'
 */
public class BanUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BanUserCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * Administrator is able to make user 'frozen'.
     * After it, user is only able to send messages for an administrator
     * and asking him to make user active again.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_USER_STATUS));
        int page = FrontControllerUtil.getCurrentPage(requestContent);
        int userId = Integer.parseInt(requestContent.getRequestParameter(Constants.ID).trim());
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            userService.frozeUser(userId);
            commandResult.setPage(Constants.PATH_SHOW_USER_FROZEN + page);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurs during frozing of user", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
}
