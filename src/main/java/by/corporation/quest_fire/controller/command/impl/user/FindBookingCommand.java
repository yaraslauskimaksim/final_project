package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FindBookingCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(FindBookingCommand.class);

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(FORWARD, requestContent.getReferer());
        Integer userId = (Integer) requestContent.getSessionAttribute(Constants.USER_ID);
        int page = ControllerUtil.getCurrentPage(requestContent);
        try {
            BookingService bookingService = ServiceFactory.getInstance().getBookingService();
            int numberOfPages = bookingService.fetchNumberOfPages(userId);
            List<Booking> bookingList = bookingService.findSingleUserBooking(userId, page);
            if (!bookingList.isEmpty()){
                commandResult.putRequestAttribute(Constants.BOOKING, bookingList);
                commandResult.putRequestAttribute(Constants.PAGE, page);
                commandResult.putRequestAttribute(Constants.NUMBER_OF_PAGE, numberOfPages);
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.GO_TO_SINGLE_USER_BOOKING_PAGE));
            }else {
                commandResult.putRequestAttribute(Constants.ERROR, "error");
                commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.GO_TO_SINGLE_USER_BOOKING_PAGE));
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during showing booking for a single user", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }
}
