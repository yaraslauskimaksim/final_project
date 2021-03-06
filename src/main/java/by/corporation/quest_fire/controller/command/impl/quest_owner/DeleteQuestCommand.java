package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

public class DeleteQuestCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteQuestCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * Quest Owner is able to delete any quest.
     * When quest is deleted, comments related to it are also deleted.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));
        int page = FrontControllerUtil.getCurrentPage(requestContent);
        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        Integer questId = (Integer) requestContent.getSessionAttribute(Constants.QUEST_ID);

        QuestService questService = ServiceFactory.getInstance().getQuestService();
        if (role.equals(Role.QUEST_OWNER)) {
            try {
                questService.deleteQuest(questId);
                commandResult.setPage(Constants.PATH_SHOW_ALL_QUESTS_BY_ROOM_NAME + page);
            } catch (ServiceException e) {
                LOGGER.error("Quest can't be deleted!", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }
        return commandResult;
    }
}
