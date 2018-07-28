package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.dto.CommentTO;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;
/**
 * This command class is responsible for finding users' comments in a 'pending' status
 */
public class FindUserCommentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindUserCommentCommand.class);
    /**
     * This command returns the {@link CommandResult}.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(FORWARD, requestContent.getReferer());
        int page = FrontControllerUtil.getCurrentPage(requestContent);
        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        if (role.equals(Role.ADMINISTRATOR)) {
            List<CommentTO> comments = null;
            try {
                CommentService commentService = ServiceFactory.getInstance().getCommnetService();
                comments = commentService.fetchAllComments(page);
                if (!comments.isEmpty()){
                    int numberOfPages = commentService.fetchNumberOfPages();
                    commandResult.putRequestAttribute(Constants.COMMENTS, comments);
                    commandResult.putRequestAttribute(Constants.PAGE, page);
                    commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_COMMENTS));
                } else {
                    LOGGER.warn("Currently, there are no comments");
                    commandResult.putSessionAttribute(Constants.EMPTY_LIST, Constants.EMPTY_COMMENT_LIST);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_COMMENTS));
                }
            } catch (ServiceException e) {
                LOGGER.error("Comments can't be found!", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }

        }
        return commandResult;
    }


}