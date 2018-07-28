package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindSingleQuestByRoomNameCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindSingleQuestByRoomNameCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(FORWARD, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        int questId = Integer.parseInt(requestContent.getRequestParameter(Constants.QUEST_ID).trim());

        if (role.equals(Role.QUEST_OWNER)) {
            Quest singleQuest = null;
            try {
                QuestService questService = ServiceFactory.getInstance().getQuestService();
                singleQuest = questService.fetchSingleQuest(questId);
                if (singleQuest != null) {
                    commandResult.putSessionAttribute(Constants.SINGLE_QUEST, singleQuest);
                    commandResult.putSessionAttribute(Constants.QUEST_ID, questId);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SINGLE_QUEST_OWNER));
                }
            } catch (ServiceException e) {
                LOGGER.error("Exception during showing a single quest", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }
        return commandResult;
    }
}
