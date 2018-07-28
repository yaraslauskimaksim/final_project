package by.corporation.quest_fire.controller.command.impl;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindQuestForHomePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindQuestForHomePageCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * If there are quests, user is redirected to page where they are present
     * If not, there is a message that there aren't any quests
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
            CommandResult commandResult = new CommandResult(FORWARD, requestContent.getReferer());
            int page = FrontControllerUtil.getCurrentPage(requestContent);
            List<Quest> quest = null;
            try {
                QuestService questService = ServiceFactory.getInstance().getQuestService();
                quest = questService.fetchAllQuests(page);
                if (!quest.isEmpty()) {
                    commandResult.putRequestAttribute(Constants.QUEST, quest);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
                } else {
                    LOGGER.warn("No quests are available");
                    commandResult.putRequestAttribute(Constants.EMPTY_LIST, Constants.EMPTY_QUEST_LIST);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
                }
            } catch (ServiceException e) {
                LOGGER.error("Quests can't be seen!", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
            return commandResult;
        }
    }

