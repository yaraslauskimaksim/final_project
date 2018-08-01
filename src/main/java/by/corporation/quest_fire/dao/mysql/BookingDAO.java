package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.dto.BookingTO;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDAO {

    int create(Booking booking) throws DaoException;

    List<BookingTO> fetchUserBookingByQuestRoom(String questRoomName, int bookingPerPage, int currentPage) throws DaoException;

    int fetchBookingQuantityByQuestName(String questName) throws DaoException;

    void update(Booking booking) throws DaoException;

    int fetchBookingQuantityBuUserId(long userId) throws DaoException;

    List<Timestamp> fetchBookedDate(long questId) throws DaoException;

    List<BookingTO> fetchSingleUserBookingDetails(long userId) throws DaoException;

    Booking fetchBooking(long bookingId) throws DaoException;

}
