package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindAllQuestByRoomCommand implements Command {


    private static final Logger LOGGER = LogManager.getLogger(FindAllQuestByRoomCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(FORWARD, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));

        int page = ControllerUtil.getCurrentPage(requestContent);

        Integer userId = (Integer) requestContent.getSessionAttribute(Constants.USER_ID);

        QuestService questService = ServiceFactory.getInstance().getQuestService();
        try {
            List<Quest> quests = questService.findAllQuestsByQuestRoom(userId, page);
            int numberOfPages = questService.fetchNumberOfPagesByQuestRoomName(userId);
            if (quests != null) {
                commandResult.putRequestAttribute(Constants.QUEST, quests);
                commandResult.putRequestAttribute(Constants.PAGE, page);
                commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_QUESTS_BY_ROOM_NAME));
            } else {
                commandResult.putRequestAttribute(Constants.ERROR, Constants.EMPTY_QUEST_LIST);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_QUESTS_BY_ROOM_NAME));
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during showing quests by quest room name", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
}
