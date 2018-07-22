package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Status;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDAO {
    int saveBookigDetails(Booking booking) throws DaoException;
    List<Booking> fetchUserBooking(String questRoomName, int bookingPerPage, int currentPage) throws DaoException;
    int getBookingQuantityByQuestName(String questName) throws DaoException;
    void rejectBooking(int  bookingId) throws DaoException;
    void approveBooking(int  bookingId) throws DaoException;
    Status getStatus(int bookingId) throws DaoException;
    List<Booking> showSingleUserBookingDetails(int userId, int bookingPerPage, int currentPage) throws DaoException;
    int fetchBookingQuantityBuUserId(int userId) throws DaoException;
    List<Timestamp> fetchBookedDate(int questId) throws DaoException;

}
