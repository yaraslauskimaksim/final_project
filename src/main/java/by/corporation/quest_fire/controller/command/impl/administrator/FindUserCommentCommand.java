package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindUserCommentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindUserCommentCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(FORWARD, BundleResourceManager.getConfigProperty(Constants.PATH_ADMIN));
        int page = ControllerUtil.getCurrentPage(requestContent);
        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        if (role.equals(Role.ADMINISTRATOR)) {
            List<Comment> comments = null;
            try {
                CommentService commentService = ServiceFactory.getInstance().getCommnetService();
                comments =commentService.showAllComments(page);
                int commentSize = commentService.getCommentQuantity();
                int numberOfPages = ControllerUtil.getNumberOfPage(commentSize, Constants.ITEMS_PER_PAGE);
                commandResult.putRequestAttribute(Constants.COMMENTS, comments);
                commandResult.putRequestAttribute(Constants.PAGE, page);
                commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_COMMENTS));

            } catch (ServiceException e) {
                LOGGER.error("Exception occurs during showing all comments");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }

        }
        return commandResult;
    }


}