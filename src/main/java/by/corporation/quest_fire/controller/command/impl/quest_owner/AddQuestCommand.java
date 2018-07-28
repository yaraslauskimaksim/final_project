package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.QuestAlreadyExistException;
import by.corporation.quest_fire.service.exception.ServiceException;

import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;
/**
 * This command class is responsible for adding a new quest.
 */
public class AddQuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddQuestCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * Quest Owner is able to add new quest.
     * After adding, user is redirected to just added quest
     * where he is able to upload image or edit quest.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        Integer userId = (Integer) requestContent.getSessionAttribute(Constants.USER_ID);
        try {
            QuestService questService = ServiceFactory.getInstance().getQuestService();
            String questRoom = questService.findQuestRoomName(userId);
            Quest quest = formQuest(requestContent, questRoom);
            int questId = questService.addQuest(quest);
            commandResult.putSessionAttribute(Constants.QUEST_ID, questId);
            commandResult.setPage(Constants.RETURN_TO_CREATED_QUEST_PAGE + questId);
        } catch (ServiceException e) {
            LOGGER.error("Quest can't be added", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        } catch (QuestAlreadyExistException e) {
            commandResult.putSessionAttribute(Constants.QUEST_ALREADY_EXISTS, Constants.QUEST_ALREADY_EXISTS_MESSGE);
            commandResult.setPage(requestContent.getReferer());
        }
        return commandResult;
    }

    /**
     * This method returns the quest {@link Quest}.
     * description, name, quest room and genre parameters are received from add quest form.
     */
    private Quest formQuest(RequestContent requestContent, String questRoom) {
        Quest quest = new Quest();
        quest.setDescription(requestContent.getRequestParameter(Constants.QUEST_DESCRIPTION));
        quest.setName(requestContent.getRequestParameter(Constants.QUEST_NAME));
        quest.setGenre(requestContent.getRequestParameter(Constants.QUEST_GENRE));
        quest.setUserId((Integer) requestContent.getSessionAttribute(Constants.USER_ID));
        quest.setQuestRoomName(questRoom);
        return quest;
    }

}