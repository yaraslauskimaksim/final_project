package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for rating the quest from 1 to 5
 */
public class RatingCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RatingCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * If user id logged in, he is able to rate the quest
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        Integer questId = (Integer) requestContent.getSessionAttribute(Constants.QUEST_ID);
        Integer score = Integer.parseInt(requestContent.getRequestParameter(Constants.SCORE).trim());
        QuestService questService = ServiceFactory.getInstance().getQuestService();
        int newScore = 0;
        try {
            questService.updateScore(questId);
            commandResult.putSessionAttribute(Constants.RATING_SAVED, Constants.RATING_WAS_SAVED_MESSAGE);
            commandResult.setPage(Constants.RETURN_TO_SINGLE_QUEST_PAGE + questId);
        } catch (ServiceException e) {
            LOGGER.error("Score can't be updated", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
}
