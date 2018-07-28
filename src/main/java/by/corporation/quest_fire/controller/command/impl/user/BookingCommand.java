package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Message;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;
/**
 * This command class is responsible for booking a quest by a user
 */
public class BookingCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BookingCommand.class);
    /**
     * This command returns the {@link CommandResult}.
     * User is able to book a quest.
     */
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, BundleResourceManager.getConfigProperty(Constants.PATH_QUEST_SINGLE));
        Booking booking = formBooking(requestContent);
        try {
            BookingService bookingService = ServiceFactory.getInstance().getBookingService();
            int bookingId = bookingService.saveBookingDetails(booking);
            if (bookingId != 0) {
                commandResult.putSessionAttribute("bookingId", bookingId);
                commandResult.putSessionAttribute("success", "your booking is successfully done!");
                commandResult.setPage(Constants.RETURN_TO_SINGLE_QUEST_PAGE + requestContent.getSessionAttribute(Constants.QUEST_ID));
            } else {
                commandResult.putRequestAttribute("error", "something goes wrong, please try once again");
                commandResult.setPage(Constants.RETURN_TO_SINGLE_QUEST_PAGE + requestContent.getSessionAttribute(Constants.QUEST_ID));
            }
        } catch (ServiceException e) {
            LOGGER.error("Message was not sent!", e);
            commandResult.setPage(BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }
        return commandResult;
    }


    /**
     * This method returns {@link Booking}.
     *
     * @param requestContent is received from booking form
     * and consists of time, number of guests, userId, questId
     */
    private Booking formBooking(RequestContent requestContent) {
        Booking booking = new Booking();
        booking.setTimestamp(parseDate(requestContent));
        booking.setNumberOfGuests(Integer.valueOf(requestContent.getRequestParameter(Constants.NUMBER_OF_GUESTS).trim()));
        booking.setUserId( (Integer) requestContent.getSessionAttribute(Constants.USER_ID));
        booking.setQuestId((Integer) requestContent.getSessionAttribute(Constants.QUEST_ID));
        return booking;
    }
    /**
     * This method returns {@link Timestamp}.
     *
     * @param requestContent is received from booking form
     * and consists of time slot and date.
     * The purpose of the method is to convert String date
     * and String time into {@link Timestamp}.
     */
    private Timestamp parseDate(RequestContent requestContent) {
        String date = requestContent.getRequestParameter(Constants.DATE).trim();
        String time = requestContent.getRequestParameter(Constants.TIME).trim() + ":00.0";
        String booked = date + " " + time;
        Timestamp dateTime = Timestamp.valueOf(booked);
        return dateTime;
    }


}

