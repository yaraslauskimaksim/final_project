package by.corporation.quest_fire.controller.command.impl;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.UserService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.util.BundleResourceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for signing in a user
 */

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    /**
     * This command returns the {@link CommandResult}.
     * If sign in is successful, user is redirected to appropriate page.
     * If not, user is stayed at log in page.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user = formCreds(requestContent);
            user = userService.fetchUser(user);
            if (user != null) {
                commandResult.putSessionAttribute(Constants.USER_ID, user.getId());
                commandResult.putSessionAttribute(Constants.USER, user);
                commandResult.putSessionAttribute(Constants.ROLE, user.getRole());
                commandResult.putSessionAttribute(Constants.STATUS, user.getStatus());
                commandResult.setPage(identifyRole(user.getRole(), user.getStatus()));
            } else {
                commandResult.putSessionAttribute(Constants.LOGIN_ERROR, Constants.INVALID_LOGIN_MESSAGE);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_LOGIN));
            }
        } catch (ServiceException e) {
            LOGGER.error("User can't be logged in!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        } catch (ValidationException e) {
            LOGGER.error("User filled in empty fields", e);
            commandResult.putSessionAttribute(Constants.EMPTY_FIELDS, Constants.EMPTY_FIELD_MESSAGE);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_LOGIN));
        }
        return commandResult;
    }
    /**
     * This method returns the path for redirecting user.
     */
    private String identifyRole(Role role, Status status) {
        String path = null;
        switch (role) {
            case CLIENT:
                if (status.equals(Status.ACTIVE)) {
                    path = BundleResourceManager.getConfigProperty(Constants.PATH_HOME);
                    return path;
                } else {
                    path = BundleResourceManager.getConfigProperty(Constants.PATH_FROZEN_USER);
                    return path;
                }
            case QUEST_OWNER:
                path = BundleResourceManager.getConfigProperty(Constants.PATH_OWNER);
                return path;
            case ADMINISTRATOR:
                path = BundleResourceManager.getConfigProperty(Constants.PATH_ADMIN);
                return path;
        }
        return path;
    }
    /**
     * This method returns the user the {@link User}.
     * email and password parameters are received from log in form
     */
    private User formCreds(RequestContent requestContent) {
        User user = new User();
        user.setEmail(requestContent.getRequestParameter(Constants.EMAIL));
        user.setPassword(requestContent.getRequestParameter(Constants.PASSWORD));
        return user;
    }
}