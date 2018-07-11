package by.corporation.final_project.service.impl;

import by.corporation.final_project.dao.DAOFactory;
import by.corporation.final_project.dao.mysql.BookingDAO;

import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.entity.Booking;
import by.corporation.final_project.entity.Status;
import by.corporation.final_project.service.BookingService;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static BookingService BOOKING_SERVICE = new BookingServiceImpl();

    public static BookingService getBookingService() {
        return BOOKING_SERVICE;
    }
    private BookingServiceImpl(){}

    private static BookingDAO bookingDAO = DAOFactory.getInstance().getBookingDAO();


    @Override
    public int saveBookingDetails(Booking booking) throws ServiceException {
        int bookingId = 0;
        try {
           bookingId = bookingDAO.saveBookigDetails(booking);
        } catch (DaoException e) {
            throw new ServiceException("Exceprion during saving booking details", e);
        }
        return bookingId;
    }

    @Override
    public List<Booking> getAllBooking(int currentPage, String questRoomName) throws ServiceException {
        List<Booking> bookingList = new ArrayList<>();

        if(currentPage >= 0){
            try {

                bookingList = bookingDAO.showUserBooking(questRoomName, currentPage, Constants.ITEMS_PER_PAGE);
            } catch (DaoException e) {
                throw  new ServiceException("", e);
            }
        }
      return bookingList;

    }



    public void approveStatus(int bookingId) throws ServiceException {
        Status status;
        try {
            status = bookingDAO.getStatus(bookingId);
            if (status.equals(Status.PENDING)) {
                bookingDAO.approveBooking(bookingId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }
    @Override
    public void rejectStatus(int bookingId) throws ServiceException {
        Status status;
        try {
            status = bookingDAO.getStatus(bookingId);
            if (status.equals(Status.PENDING)) {
                bookingDAO.rejectBooking(bookingId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }


    @Override
    public int getBookingQuantityByQuestRoom(String questRoomName) throws ServiceException {
        int counter = 0;
        try {
            counter = bookingDAO.getBookingQuantityByQuestName(questRoomName);
        }  catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return counter;
    }
}
