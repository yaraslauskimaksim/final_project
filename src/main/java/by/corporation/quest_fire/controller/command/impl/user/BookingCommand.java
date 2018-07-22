package by.corporation.quest_fire.controller.command.impl.user;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;

import java.sql.Timestamp;

import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;

public class BookingCommand implements Command {
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
            e.printStackTrace();
        }
        return commandResult;
    }


    private Booking formBooking(RequestContent requestContent) {
        Booking booking = new Booking();
        booking.setTimestamp(parseDate(requestContent));
        booking.setNumberOfGuests(Integer.valueOf(requestContent.getRequestParameter(Constants.NUMBER_OF_GUESTS).trim()));
        booking.setUserId((Integer) requestContent.getSessionAttribute(Constants.USER_ID));
        booking.setQuestId((Integer) requestContent.getSessionAttribute(Constants.QUEST_ID));
        return booking;
    }

    private Timestamp parseDate(RequestContent requestContent) {
        String date = requestContent.getRequestParameter(Constants.DATE).trim();
        String time = requestContent.getRequestParameter(Constants.TIME).trim() + ":00.0";
        String booked = date + " " + time;
        Timestamp dateTime = Timestamp.valueOf(booked);
        return dateTime;
    }


}

