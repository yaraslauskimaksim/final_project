package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

public class BookingRejectionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookingRejectionCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        int bookingId = Integer.parseInt(requestContent.getRequestParameter(Constants.ID).trim());
        int page = FrontControllerUtil.getCurrentPage(requestContent);
        if (role.equals(Role.QUEST_OWNER)) {
            try {
                BookingService bookingService = ServiceFactory.getInstance().getBookingService();
                bookingService.rejectStatus(bookingId);
                commandResult.setPage(Constants.PATH_SHOW_USER_BOOKING + page);
            } catch (ServiceException e) {
                LOGGER.error("Exception during rejection of booking", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }

        return commandResult;

    }
}
