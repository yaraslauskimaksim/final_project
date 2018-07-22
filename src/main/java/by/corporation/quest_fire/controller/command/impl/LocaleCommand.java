package by.corporation.quest_fire.controller.command.impl;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;

import javax.servlet.ServletException;
import java.io.IOException;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for changing language through all application
 */

public class LocaleCommand implements Command {
    /**
     * This command returns the {@link CommandResult}.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());

        commandResult.putSessionAttribute(Constants.LANGUAGE, requestContent.getRequestParameter(Constants.LANGUAGE));
        commandResult.setPage(requestContent.getReferer());
        return commandResult;
    }
}
