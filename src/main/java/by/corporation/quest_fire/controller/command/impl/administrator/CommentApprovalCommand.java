package by.corporation.quest_fire.controller.command.impl.administrator;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

public class CommentApprovalCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CommentApprovalCommand.class);
    @Override
    public CommandResult execute(RequestContent requestContent)  {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_COMMENTS));
        int page = FrontControllerUtil.getCurrentPage(requestContent);

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        int commentId = Integer.parseInt(requestContent.getRequestParameter(Constants.ID));

        if(role.equals(Role.ADMINISTRATOR)){
            try {
                CommentService commentService = ServiceFactory.getInstance().getCommnetService();
                commentService.approveComment(commentId);
                commandResult.setPage(Constants.PATH_SHOW_USER_COMMENT_AFTER_ACTION  + page);
            } catch (ServiceException e) {
               LOGGER.error("Exception during setting comment status to approved", e);
               commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }
        return commandResult;
    }
}
