package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.entity.Booking;
import by.corporation.final_project.entity.Status;

import java.util.List;

public interface BookingDAO {
    int saveBookigDetails(Booking booking) throws DaoException;
    List<Booking> showUserBooking(String questRoomName, int questPerPage, int currentPage ) throws DaoException;
    int getBookingQuantityByQuestName(String questName) throws DaoException;
    void rejectBooking(int  bookingId) throws DaoException;
    void approveBooking(int  bookingId) throws DaoException;
    Status getStatus(int bookingId) throws DaoException;
}
