package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Booking;


import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.BookingServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BookingCommand implements Command {
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException, ControllerException {
        HttpSession session = request.getSession();
        //session


        Booking booking = formBooking(request, session);

        try {
            int bookingId = BookingServiceImpl.getBookingService().saveBookingDetails(booking);
            if(bookingId!=0){
                request.getSession().setAttribute("bookingId", bookingId);
                request.setAttribute("success", "your booking is successfully done!");
                path.append(request.getContextPath()).append("/frontController?command=singleQuest&questId="+ (Integer) session.getAttribute("questId"));
            }
            else {
                request.setAttribute("error", "something goes wrong, please try once again");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SINGLE_QUEST_OWNER));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        response.sendRedirect(path.toString());
        path.setLength(0);

    }


    private Booking formBooking(HttpServletRequest request, HttpSession session){
        Booking booking = new Booking();
        booking.setTimestamp(parseDate(request));
        booking.setNumberOfGuests(Integer.valueOf(request.getParameter("numberOfGuests").trim()));
        booking.setUserId((Integer) session.getAttribute("userId"));
        booking.setQuestId((Integer) session.getAttribute("questId"));
        return booking;
    }

    private Timestamp parseDate(HttpServletRequest request){

        String date = request.getParameter("date").trim();
        String time = request.getParameter("time").trim() + ":00.0";
        String booked = date + " " + time;
        Timestamp dateTime = Timestamp.valueOf(booked);
        return dateTime;
    }


}
