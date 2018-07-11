package by.corporation.final_project.service;

import by.corporation.final_project.entity.Booking;
import by.corporation.final_project.service.exception.ServiceException;

import java.util.List;

public interface BookingService {
    int saveBookingDetails(Booking booking) throws ServiceException;
    List<Booking> getAllBooking(int currentPage, String questRoomName) throws ServiceException;
    void approveStatus(int bookingId) throws ServiceException;
    void rejectStatus(int bookingId) throws ServiceException;
    int getBookingQuantityByQuestRoom(String questRoomName) throws ServiceException;
}
