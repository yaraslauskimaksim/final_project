package by.corporation.quest_fire.controller.command.impl;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.dto.CommentTO;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;
/**
 * This command class is responsible for finding a single quest and comments for it
 */
public class FindSingleQuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindSingleQuestCommand.class);
    /**
     * This command returns the {@link CommandResult}.
     * There is a single quest with comments for it
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(FORWARD, requestContent.getReferer());

        Integer questId = Integer.parseInt(requestContent.getRequestParameter(Constants.QUEST_ID).trim());

        verifyQuestId(questId, commandResult);
        Quest singleQuest = null;
        try {
            QuestService questService = ServiceFactory.getInstance().getQuestService();
            singleQuest = questService.fetchSingleQuest(questId);
            CommentService commentService = ServiceFactory.getInstance().getCommnetService();
            List<CommentTO> comments = commentService.fetchAllCommentsByQuestId(questId);
            BookingService bookingService = ServiceFactory.getInstance().getBookingService();
            List<Timestamp> bookedDate = bookingService.fetchFilteredBookedDateByCurrentTime(questId);
            commandResult.putRequestAttribute(Constants.BOOKING_DATE, bookedDate);
            commandResult.putSessionAttribute(Constants.SINGLE_QUEST, singleQuest);
            commandResult.putSessionAttribute(Constants.QUEST_ID, questId);
            commandResult.putSessionAttribute(Constants.COMMENT, comments);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_QUEST_SINGLE));
        } catch (ServiceException e) {
            LOGGER.error("Quest can't be seen!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
    /**
     * This method is for verifying whether
     * @param questId empty or not.
     * If there is no quest id, user is redirected to error page
     */
    private void verifyQuestId(Integer questId, CommandResult commandResult){
        if(questId == null || questId == 0){
            LOGGER.error("No Quest Id");
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
    }
}
