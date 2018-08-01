package by.corporation.quest_fire.service.impl;

import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.BookingDAO;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Booking;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.dto.BookingTO;
import by.corporation.quest_fire.service.BookingService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.util.Constants;
import by.corporation.quest_fire.service.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private static BookingDAO bookingDAO = DAOFactory.getInstance().getBookingDAO();

    private static final Logger LOGGER = LogManager.getLogger(BookingServiceImpl.class);

    @Override
    public int saveBookingDetails(Booking booking) throws ServiceException {
        int bookingId = 0;
        try {
            bookingId = bookingDAO.create(booking);
        } catch (DaoException e) {
            throw new ServiceException("Exception during saving booking details", e);
        }
        return bookingId;
    }

    /**
     * The method returns the collection of {@link Booking} for quest owner.
     *
     * @param questRoomName is for identifying definite quest owner.
     * @param currentPage is for pagination.
     * @throws ServiceException the service exception
     */
    @Override
    public List<BookingTO> fetchAllUserBooking(String questRoomName, int currentPage) throws ServiceException {
        List<BookingTO> filteredBookingList = new ArrayList<>();
        List<BookingTO> bookingList;
        try {
            bookingList = bookingDAO.fetchUserBookingByQuestRoom(questRoomName, currentPage, Constants.ITEMS_PER_PAGE);
            for (BookingTO booking: bookingList){
                if(booking.getQuest().getQuestRoomName().equalsIgnoreCase(questRoomName)){
                    filteredBookingList.add(booking);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving users' booking for quest owner", e);
        }
        return filteredBookingList;
    }


    @Override
    public List<BookingTO> findSingleUserBooking(long userId) throws ServiceException {
        List<BookingTO> bookings = null;
        List<BookingTO> filteredBooking = new ArrayList<>();
        try {
            bookings = bookingDAO.fetchSingleUserBookingDetails(userId);
            for (BookingTO bookingTO: bookings){
                if(bookingTO.getBookedDate() == null){
                    continue;
                }
                long time = System.currentTimeMillis();
                if (bookingTO.getBookedDate().getTime() >= time) {
                    filteredBooking.add(bookingTO);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving data of a single user's booking", e);
        }
        return filteredBooking;
    }

    @Override
    public void approveStatus(long bookingId) throws ServiceException {
        try {
            Booking booking = bookingDAO.fetchBooking(bookingId);
            if (booking.getStatus().equals(Status.PENDING)) {
                booking.setStatus(Status.APPROVED);
                bookingDAO.update(booking);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }

    @Override
    public void rejectStatus(long bookingId) throws ServiceException {
        try {
            Booking booking = bookingDAO.fetchBooking(bookingId);
            if (booking.getStatus().equals(Status.PENDING)) {
                booking.setStatus(Status.REJECTED);
                bookingDAO.update(booking);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during setting status on service layer", e);
        }

    }


    @Override
    public int fetchNumberOfPages(int userId) throws ServiceException {
        int numberOfPages = 0;
        try {
            int numberOfRecords = retrieveNumberOfBookingByUserId(userId);
            numberOfPages = ServiceUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);
        } catch (ServiceException e) {
            throw new ServiceException("", e);
        }

        return numberOfPages;
    }

    /**
     * The method returns the collection of {@link Timestamp} for user not to book
     * already booked quest.
     *
     * @param questId is for what quest.
     * @throws ServiceException the service exception
     */
    private List<Timestamp> fetchBookedDate(long questId) throws ServiceException {
        List<Timestamp> bookedDate = new ArrayList<>();
        try {
            bookedDate = bookingDAO.fetchBookedDate(questId);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving booked dates", e);
        }
        return bookedDate;
    }

    /**
     * The method returns the collection of {@link Timestamp} for user not to book
     * already booked quest, but date is sorted by current date.
     *
     * @param questId is for what quest.
     * @throws ServiceException the service exception if date is null
     */
    @Override
    public List<Timestamp> fetchFilteredBookedDateByCurrentTime(long questId) throws ServiceException {
        List<Timestamp> bookedDate = fetchBookedDate(questId);
        List<Timestamp> filteredDate = new ArrayList<>();
        for (Timestamp date : bookedDate) {
            if (date == null) {
                continue;
            }
            long time = System.currentTimeMillis();
            if (date.getTime() >= time) {
                filteredDate.add(date);
            }
        }
        return filteredDate;
    }


    /**
     * The method returns the number of pages for pagination
     */
    public int fetchNumberOfPagesByQuestRoom(String questRoomName) {
        int bookingQuantity = fetchBookingQuantityByQuestRoom(questRoomName);
        int numberOfPages = ServiceUtil.getNumberOfPage(bookingQuantity, Constants.ITEMS_PER_PAGE);
        return numberOfPages;

    }

    private int retrieveNumberOfBookingByUserId(int userId) throws ServiceException {
        int quantity = 0;
        try {
            quantity = bookingDAO.fetchBookingQuantityBuUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
        return quantity;
    }

    /**
     * The method returns the quantity of users' booking for pagination.
     */
    private int fetchBookingQuantityByQuestRoom(String questRoomName) {
        int counter = 0;
        try {
            counter = bookingDAO.fetchBookingQuantityByQuestName(questRoomName);
        } catch (DaoException e) {
            LOGGER.error("Exception during retrieving users' booking quantity", e);
        }
        return counter;
    }
}
