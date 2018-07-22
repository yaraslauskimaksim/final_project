package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Timestamp;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

/**
 * This command class is responsible for saving user's comments
 */
public class UserCommentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UserCommentCommand.class);

    /**
     * This command returns the {@link CommandResult}.
     * After saving a comment, user stays at a single quest page.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        Integer questId = (Integer) requestContent.getSessionAttribute(Constants.QUEST_ID);
        Comment comment = formComment(requestContent, questId);
        try {
            CommentService commentService = ServiceFactory.getInstance().getCommnetService();
            commentService.saveComment(comment);
            commandResult.putSessionAttribute(Constants.COMMENT_SAVED, Constants.COMMENT_WAS_SENT_MESSAGE);
            commandResult.setPage(Constants.RETURN_TO_SINGLE_QUEST_PAGE + questId);
        } catch (ValidationException e) {
            LOGGER.error("Comment can't be saved empty", e);
            commandResult.putSessionAttribute(Constants.EMPTY_FIELDS, Constants.EMPTY_FIELD_MESSAGE);
            commandResult.setPage(Constants.RETURN_TO_SINGLE_QUEST_PAGE + questId);
        } catch (ServiceException e) {
            LOGGER.error("Comment can't be saved!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;

    }

    /**
     * This method returns the comment {@link Comment}.
     *
     * @param requestContent (description) is received from comment form
     * @param questId        is received from session
     */
    private Comment formComment(RequestContent requestContent, int questId) {
        Comment comment = new Comment();
        comment.setUserId((Integer) requestContent.getSessionAttribute(Constants.USER_ID));
        comment.setQuestId(questId);
        comment.setDescription(requestContent.getRequestParameter(Constants.COMMENT_DESCRIPTION));
        comment.setTime(formCurrentDate());
        return comment;
    }

    private Timestamp formCurrentDate() {
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        return timestamp;
    }
}
