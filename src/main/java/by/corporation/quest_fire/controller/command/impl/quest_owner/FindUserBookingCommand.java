package by.corporation.quest_fire.controller.command.impl.quest_owner;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.dto.BookingTO;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;
/**
 * This command class is responsible for finding all users' booking for quest owner
 */
public class FindUserBookingCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BookingRejectionCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {

        CommandResult commandResult = new CommandResult(FORWARD, BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));

        Role role = (Role) requestContent.getSessionAttribute(Constants.ROLE);
        int page = FrontControllerUtil.getCurrentPage(requestContent);

        Integer userId = (Integer) requestContent.getSessionAttribute(Constants.USER_ID);

        if (role.equals(Role.QUEST_OWNER)) {
            List<BookingTO> booking = null;
            try {
                QuestService questService = ServiceFactory.getInstance().getQuestService();
                String questRoomName = questService.findQuestRoomName(userId);
                BookingService bookingService = ServiceFactory.getInstance().getBookingService();
                booking = bookingService.fetchAllUserBooking(questRoomName, page);
                if (!booking.isEmpty()) {
                    int numberOfPages = bookingService.fetchNumberOfPagesByQuestRoom(questRoomName);
                    commandResult.putRequestAttribute(Constants.BOOKING, booking);
                    commandResult.putRequestAttribute(Constants.PAGE, page);
                    commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_BOOKING));
                } else {
                    commandResult.putRequestAttribute(Constants.EMPTY_LIST, Constants.NO_BOOKING_OF_QUEST_ROOM);
                    commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.PATH_BOOKING));
                }
            } catch (ServiceException e) {
                LOGGER.error("Users booking can not be seen", e);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
            }
        }
        return commandResult;
    }
}
