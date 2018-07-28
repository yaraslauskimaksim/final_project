package by.corporation.quest_fire.controller.command.impl;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for searching quest by quest name
 */
public class SearchCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SearchCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * If there are quests, user is redirected to page where they are present
     * If not, there is a message that there aren't any quests according to the search criteria(quest name)
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());

        String name = requestContent.getRequestParameter(Constants.SEARCH_NAME);

        List<Quest> quest = null;
        try {
            QuestService questService = ServiceFactory.getInstance().getQuestService();
            quest = questService.searchQuests(name);
            if(!quest.isEmpty()) {
                commandResult.putRequestAttribute(Constants.QUEST, quest);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.GO_TO_SEARCH_RESULT_PAGE));
            }else {
                LOGGER.warn("No quests are found");
                commandResult.putRequestAttribute(Constants.EMPTY_LIST, Constants.EMPTY_QUEST_LIST);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.GO_TO_SEARCH_RESULT_PAGE));
            }
        } catch (ServiceException e) {
            LOGGER.error("Quests can't be seen!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
}
