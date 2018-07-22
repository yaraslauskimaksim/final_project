package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.UserAlreadyExistException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for signing up a user
 */
public class RegisterCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * If sign un is successful, user is redirected to appropriate page (home page)
     * If not, user is stayed at register page.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        User user = formUser(requestContent);
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            int userID = userService.register(user);
            commandResult.putSessionAttribute(Constants.USER_ID, userID);
            commandResult.putSessionAttribute(Constants.USER, user);
            commandResult.putSessionAttribute(Constants.ROLE, Role.CLIENT);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
        } catch (UserAlreadyExistException e) {
            LOGGER.error("User already exists", e);
            commandResult.putSessionAttribute(Constants.USER_EXISTS, Constants.USER_ALREADY_EXITS_MESSAGE);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_REGISTER));
        } catch (ValidationException e) {
            LOGGER.error("Invalid data", e);
            commandResult.putSessionAttribute(Constants.INVALID_FIELDS, Constants.INVALID_LOGIN_MESSAGE);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_REGISTER));
        } catch (ServiceException e) {
            LOGGER.error("User can't be registered!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;

    }

    /**
     * This method returns the user the {@link User}.
     *
     * @param requestContent is received from register form
     *                       and consists of
     *                       firstname, lastname, email, password
     */
    private User formUser(RequestContent requestContent) {
        User user = new User();
        user.setFirstName(requestContent.getRequestParameter(Constants.FIRSTNAME));
        user.setLastName(requestContent.getRequestParameter(Constants.LASTNAME));
        user.setEmail(requestContent.getRequestParameter(Constants.EMAIL));
        user.setPassword(requestContent.getRequestParameter(Constants.PASSWORD));
        user.setRole(Role.CLIENT);
        return user;
    }
}


