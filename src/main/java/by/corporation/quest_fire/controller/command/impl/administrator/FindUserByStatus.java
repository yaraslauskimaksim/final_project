package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.impl.UserServiceImpl;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindUserByStatus implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindUserByStatus.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(FORWARD, BundleResourceManager.getConfigProperty(Constants.PATH_ADMIN));

        int page = ControllerUtil.getCurrentPage(requestContent);
        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        Status status = Status.valueOf(requestContent.getRequestParameter(Constants.STATUS).toUpperCase().trim());

        if (role.equals(Role.ADMINISTRATOR)) {
            List<User> userList = null;
            try {
                UserService userService = ServiceFactory.getInstance().getUserService();
                userList = userService.getUsersByStatus(status, page);
                int numberOfRecords = userService.getUserQuantatyByStatus(status);
                int numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);
                commandResult.putRequestAttribute(Constants.STATUS, String.valueOf(status).toLowerCase());
                commandResult.putRequestAttribute(Constants.USERS, userList);
                commandResult.putRequestAttribute(Constants.PAGE, page);
                commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_USER_STATUS));
            } catch (ServiceException e) {
                LOGGER.error("Exception occurs during showing users by status");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }


        }
        return commandResult;
    }
}