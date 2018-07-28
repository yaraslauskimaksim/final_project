package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.QuestAlreadyExistException;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

public class EditQuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditQuestCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);

        Integer questId = (Integer) requestContent.getSessionAttribute(Constants.QUEST_ID);

        if (role.equals(Role.QUEST_OWNER)) {
            try {
                QuestService questService = ServiceFactory.getInstance().getQuestService();
                Quest quest = formQuest(requestContent);
                questService.updateQuest(quest);
                commandResult.setPage(Constants.RETURN_TO_CREATED_QUEST_PAGE + questId);
            } catch (QuestAlreadyExistException e) {
                LOGGER.warn("Such quest already exists");
                commandResult.putSessionAttribute(Constants.QUEST_ALREADY_EXISTS, Constants.QUEST_ALREADY_EXISTS_MESSGE);
                commandResult.setPage(requestContent.getReferer());
            }catch (ServiceException e) {
                LOGGER.error("Quest can't be edited", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }

        return commandResult;
    }

    private Quest formQuest(RequestContent requestContent) {
        Quest quest = new Quest();
        quest.setDescription(requestContent.getRequestParameter(Constants.QUEST_DESCRIPTION));
        quest.setName(requestContent.getRequestParameter(Constants.QUEST_NAME));
        quest.setGenre(requestContent.getRequestParameter(Constants.QUEST_GENRE));
        quest.setQuestId((Integer) requestContent.getSessionAttribute(Constants.QUEST_ID));
        return quest;
    }
}
