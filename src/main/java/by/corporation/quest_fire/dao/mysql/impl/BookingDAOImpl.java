package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.mysql.BookingDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with booking
 */
public class BookingDAOImpl implements BookingDAO {

    private static final Logger LOGGER = LogManager.getLogger(BookingDAOImpl.class);

    private static final String SAVE_NEW_BOOKING_DETAIL = "insert into booking(boo_user_id, boo_quest_id, boo_date, boo_number_of_guests) values (?,?,?,?)";
    private static final String SELECT_BOOKING_DETAIL_BY_QUEST_ROOM = "select boo_id, boo_user_id, boo_quest_id, boo_date, boo_number_of_guests, boo_status, que_name, usr_firstname, usr_lastname from booking left join quest on quest.que_id = booking.boo_quest_id left join user on user.usr_id = booking.boo_user_id where que_room_name = ? and boo_status = 'pending' LIMIT ? OFFSET ?";
    private static final String GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME = "SELECT COUNT(*) FROM booking left join quest on quest.que_id = booking.boo_quest_id where que_room_name=? and boo_status='pending'";
    private static final String APPROVE_BOOKING = "UPDATE booking SET boo_status = 'approved' WHERE boo_id = ?";
    private static final String REJECT_BOOKING = "UPDATE booking SET boo_status = 'rejected' WHERE boo_id = ?";
    private static final String SELECT_STATUS = "SELECT boo_id, boo_status FROM booking WHERE boo_id = ?";
    private static final String SELECT_USER_BOOKING_DETAILS = " SELECT boo_id, boo_status, boo_date, boo_number_of_guests, que_name, que_genre from booking left join quest on que_id = boo_quest_id where boo_user_id = ? order by boo_date DESC LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_BOOKING_QUANTITY_BY_USER_ID = "SELECT COUNT(*) FROM booking where boo_user_id = ?";
    private static final String SELECT_BOOKED_DATE = "SELECT boo_date from booking where boo_quest_id = ? and (boo_status = 'approved' or boo_status = 'pending') order by boo_date DESC";


    @Override
    public int saveBookigDetails(Booking booking) throws DaoException {
        ResultSet resultSet = null;
        int bookingId = 0;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_BOOKING_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, booking.getUserId());
            preparedStatement.setInt(2, booking.getQuestId());
            preparedStatement.setTimestamp(3, booking.getTimestamp());
            preparedStatement.setInt(4, booking.getNumberOfGuests());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            bookingId = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving booking details", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingId;
    }

    @Override
    public List<Booking> fetchUserBooking(String questRoomName, int currentPage, int questPerPage) throws DaoException {
        ResultSet resultSet = null;
        List<Booking> bookingList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKING_DETAIL_BY_QUEST_ROOM);) {
            preparedStatement.setString(1, questRoomName);
            preparedStatement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            preparedStatement.setInt(3, startIndex);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Quest quest = new Quest();
                quest.setName(resultSet.getString("que_name"));

                User user = new User();
                user.setFirstName(resultSet.getString("usr_firstname"));
                user.setLastName(resultSet.getString("usr_lastname"));

                Booking booking = new Booking();
                booking.setUser(user);
                booking.setQuest(quest);
                booking.setBookingId(resultSet.getInt("boo_id"));
                booking.setUserId(resultSet.getInt("boo_user_id"));
                booking.setQuestId(resultSet.getInt("boo_quest_id"));
                booking.setTimestamp(resultSet.getTimestamp("boo_date"));
                booking.setNumberOfGuests(resultSet.getInt("boo_number_of_guests"));
                booking.setStatus(Status.valueOf(resultSet.getString("boo_status").toUpperCase()));
                bookingList.add(booking);

            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingList;
    }


    @Override
    public List<Booking> showSingleUserBookingDetails(int userId, int questPerPage, int currentPage) throws DaoException {
        ResultSet resultSet = null;
        List<Booking> bookingList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BOOKING_DETAILS);) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            preparedStatement.setInt(3, startIndex);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Quest quest = new Quest();
                quest.setName(resultSet.getString(Constants.QUEST_NAME));
                quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));

                Booking booking = new Booking();
                booking.setQuest(quest);
                booking.setBookingId(resultSet.getInt(Constants.BOOKING_ID));
                booking.setStatus(Status.valueOf(resultSet.getString(Constants.BOOKING_STATUS).toUpperCase()));
                booking.setTimestamp(resultSet.getTimestamp(Constants.BOOKING_DATE));
                booking.setNumberOfGuests(resultSet.getInt(Constants.BOOKING_NUMBER_OF_GUESTS));

                bookingList.add(booking);

            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingList;
    }

    @Override
    public int getBookingQuantityByQuestName(String questName) throws DaoException {
        ResultSet resultSet = null;
        int counter = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME)) {
            preparedStatement.setString(1, questName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }


    @Override
    public int fetchBookingQuantityBuUserId(int userId) throws DaoException {
        ResultSet resultSet = null;
        int counter = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKING_QUANTITY_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }

    /**
     * The method returns the {@link Timestamp} from db
     * in order to user not to book already booked quest
     *
     * @throws DaoException the dao exception
     */

    @Override
    public List<Timestamp> fetchBookedDate(int questId) throws DaoException {
        ResultSet resultSet = null;
        List<Timestamp> bookedTime = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKED_DATE)) {
            preparedStatement.setInt(1, questId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Timestamp time = resultSet.getTimestamp(1);
                bookedTime.add(time);
            }
            return bookedTime;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    @Override
    public void approveBooking(int bookingId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(APPROVE_BOOKING);) {
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void rejectBooking(int bookingId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REJECT_BOOKING);) {
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
    }

    @Override
    public Status getStatus(int bookingId) throws DaoException {
        ResultSet resultSet = null;
        Status status = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);) {
            preparedStatement.setInt(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status = Status.valueOf(resultSet.getString("boo_status").toUpperCase());
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeResourses(resultSet);
            } catch (SQLException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return status;
    }


}
