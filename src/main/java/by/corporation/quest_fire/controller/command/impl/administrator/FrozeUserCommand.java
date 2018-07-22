package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.impl.UserServiceImpl;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;


public class FrozeUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FrozeUserCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_USER_STATUS));

        int page = ControllerUtil.getCurrentPage(requestContent);

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        int userId = Integer.parseInt(requestContent.getRequestParameter(Constants.ID).trim());

        if (role.equals(Role.ADMINISTRATOR)) {
            try {
                UserService userService = ServiceFactory.getInstance().getUserService();
               userService.frozeUser(userId);
                commandResult.setPage(Constants.PATH_SHOW_USER_FROZEN + page);
            } catch (ServiceException e) {
                LOGGER.error("Exception occurs during frozing of user");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }

        return commandResult;
    }
}
