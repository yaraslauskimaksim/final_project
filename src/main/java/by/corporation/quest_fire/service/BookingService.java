package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.dto.BookingTO;
import by.corporation.quest_fire.service.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;

public interface BookingService {
    int saveBookingDetails(Booking booking) throws ServiceException;
    List<BookingTO> fetchAllUserBooking(String questRoomName, int currentPage) throws ServiceException;
    List<BookingTO> findSingleUserBooking(int userId) throws ServiceException;
    void approveStatus(int bookingId) throws ServiceException;
    void rejectStatus(int bookingId) throws ServiceException;
    int fetchNumberOfPagesByQuestRoom(String questRoomName);
    int fetchNumberOfPages(int userId) throws ServiceException;
    List<Timestamp> fetchFilteredBookedDateByCurrentTime(int questId) throws ServiceException;
}
