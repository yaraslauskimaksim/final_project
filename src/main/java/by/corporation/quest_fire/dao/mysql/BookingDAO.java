package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.dto.BookingTO;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDAO {
    int saveBookingDetails(Booking booking) throws DaoException;
    List<BookingTO> fetchUserBooking(String questRoomName, int bookingPerPage, int currentPage) throws DaoException;
    int fetchBookingQuantityByQuestName(String questName) throws DaoException;
    void rejectBooking(int  bookingId) throws DaoException;
    void approveBooking(int  bookingId) throws DaoException;
    Status getStatus(int bookingId) throws DaoException;
    List<BookingTO> fetchSingleUserBookingDetails(int userId) throws DaoException;
    int fetchBookingQuantityBuUserId(int userId) throws DaoException;
    List<Timestamp> fetchBookedDate(int questId) throws DaoException;

}
