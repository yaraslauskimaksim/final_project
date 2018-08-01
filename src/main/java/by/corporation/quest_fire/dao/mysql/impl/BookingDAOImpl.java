package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.AbstractDAO;
import by.corporation.quest_fire.dao.TransactionManager;
import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.mysql.BookingDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.*;
import by.corporation.quest_fire.entity.dto.BookingTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with booking
 */
public class BookingDAOImpl extends AbstractDAO implements BookingDAO {

    public BookingDAOImpl(PooledConnection connection) {
        super(connection);
    }

    private static final Logger LOGGER = LogManager.getLogger(BookingDAOImpl.class);

    private static final String SAVE_BOOKING = "insert into booking(boo_user_id, boo_quest_id, boo_date, boo_number_of_guests) values (?,?,?,?)";
    private static final String SELECT_BOOKING_DETAIL_BY_QUEST_ROOM = "select boo_id, boo_user_id, boo_quest_id, boo_date, boo_number_of_guests, boo_status, que_name, usr_firstname, usr_lastname from booking left join quest on quest.que_id = booking.boo_quest_id left join user on user.usr_id = booking.boo_user_id where que_room_name = ? and boo_status = 'pending' order by boo_date DESC LIMIT ? OFFSET ?";
    private static final String GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME = "SELECT COUNT(*) FROM booking left join quest on quest.que_id = booking.boo_quest_id where que_room_name=? and boo_status='pending'";
    private static final String UPDATE_BOOKING = "UPDATE booking SET boo_user_id = ?, boo_quest_id = ?, boo_date = ?, boo_number_of_guests = ?, boo_status = ? FROM booking WHERE boo_id = ?";
    private static final String SELECT_USER_BOOKING_DETAILS = " SELECT boo_id, boo_status, boo_date, boo_number_of_guests, que_name, que_genre from booking left join quest on que_id = boo_quest_id where boo_user_id = ? order by boo_date DESC";
    private static final String SELECT_ALL_BOOKING_QUANTITY_BY_USER_ID = "SELECT COUNT(*) FROM booking where boo_user_id = ?";
    private static final String SELECT_BOOKED_DATE = "SELECT boo_date from booking where boo_quest_id = ? and (boo_status = 'approved' or boo_status = 'pending') order by boo_date DESC";
    private static final String SELECT_BOOKING = "SELECT boo_user_id, boo_quest_id,boo_date, boo_status,boo_number_of_guests FROM booking WHERE boo_id = ?";


    /**
     * The method save new {@link Booking} to the db.
     *
     * @param booking contains details which need to be saved.
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} happens.
     */
    @Override
    public int create(Booking booking) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int bookingId = 0;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SAVE_BOOKING, Statement.RETURN_GENERATED_KEYS);
            formBookingDetail(preparedStatement, booking);
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            bookingId = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving booking details", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingId;
    }


    /**
     * The method returns the collection of {@link Booking} from db.
     *
     * @param questRoomName is for verifying which quest room quest is booked.
     * @param questPerPage  is the constant for limiting quests per page
     * @param currentPage   is the page where user is currently
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} happens.
     */
    @Override
    public List<BookingTO> fetchUserBookingByQuestRoom(String questRoomName, int currentPage, int questPerPage) throws DaoException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<BookingTO> bookingList = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BOOKING_DETAIL_BY_QUEST_ROOM);
            preparedStatement.setString(1, questRoomName);
            preparedStatement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            preparedStatement.setInt(3, startIndex);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookingTO booking = formUserBookingResult(resultSet);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingList;
    }

    /**
     * The method returns the collection of {@link Booking} from db according to
     * @param userId which is for verifying who booked the quest.
     * @throws DaoException if {@link SQLException} or {@link ConnectionPool} happens.
     */
    @Override
    public List<BookingTO> fetchSingleUserBookingDetails(long userId) throws DaoException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<BookingTO> bookingList;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BOOKING_DETAILS);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            bookingList = formSingleBookingResult(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return bookingList;
    }

    @Override
    public Booking fetchBooking(long bookingId) throws DaoException {
        Booking booking = new Booking();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BOOKING);
            preparedStatement.setLong(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                booking = new Booking();
                booking.setQuestId(resultSet.getInt(Constants.BOOKING_QUEST_ID));
                booking.setUserId(resultSet.getInt(Constants.BOOKING_USER_ID));
                booking.setStatus(Status.valueOf(resultSet.getString(Constants.BOOKING_STATUS).toUpperCase()));
                booking.setTimestamp(resultSet.getTimestamp(Constants.BOOKING_DATE));
                booking.setNumberOfGuests(resultSet.getInt(Constants.BOOKING_NUMBER_OF_GUESTS));
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return booking;
    }

    @Override
    public int fetchBookingQuantityByQuestName(String questName) throws DaoException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME);
            preparedStatement.setString(1, questName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }

    @Override
    public void update(Booking booking) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_BOOKING);
            preparedStatement.setLong(1, booking.getUserId());
            preparedStatement.setLong(2, booking.getQuestId());
            preparedStatement.setTimestamp(3, booking.getTimestamp());
            preparedStatement.setInt(4, booking.getNumberOfGuests());
            preparedStatement.setString(5, String.valueOf((booking.getStatus())).toLowerCase());
            preparedStatement.setLong(6, booking.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    @Override
    public int fetchBookingQuantityBuUserId(long userId) throws DaoException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKING_QUANTITY_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
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
    public List<Timestamp> fetchBookedDate(long questId) throws DaoException {
        PooledConnection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Timestamp> bookedTime = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BOOKED_DATE);
            preparedStatement.setLong(1, questId);
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
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    /**
     * The method set status for booking to 'approved'
     *
     * @param bookingId is which quest needs to be approved.
     * @throws DaoException the dao exception
     */


    private void formBookingDetail(PreparedStatement preparedStatement, Booking booking) throws SQLException {
        preparedStatement.setLong(1, booking.getUserId());
        preparedStatement.setLong(2, booking.getQuestId());
        preparedStatement.setTimestamp(3, booking.getTimestamp());
        preparedStatement.setInt(4, booking.getNumberOfGuests());
        preparedStatement.executeUpdate();
    }

    private BookingTO formUserBookingResult(ResultSet resultSet) throws SQLException {
        BookingTO booking = new BookingTO();

        while (resultSet.next()) {
            Quest quest = new Quest();
            quest.setName(resultSet.getString(Constants.QUEST_NAME));
            quest.setId(resultSet.getInt(Constants.BOOKING_QUEST_ID));

            User user = new User();
            user.setId(resultSet.getInt(Constants.BOOKING_USER_ID));
            user.setFirstName(resultSet.getString(Constants.USER_FIRSTNAME));
            user.setLastName(resultSet.getString(Constants.USER_LASTNAME));

            booking.setUser(user);
            booking.setQuest(quest);
            booking.setId(resultSet.getInt(Constants.BOOKING_ID));
            booking.setBookedDate(resultSet.getTimestamp(Constants.BOOKING_DATE));
            booking.setNumberOfGuests(resultSet.getInt(Constants.BOOKING_NUMBER_OF_GUESTS));
            booking.setStatus(Status.valueOf(resultSet.getString(Constants.BOOKING_STATUS).toUpperCase()));
        }
        return booking;
    }

    private List<BookingTO> formSingleBookingResult(ResultSet resultSet) throws SQLException {
        List<BookingTO> bookingList = new ArrayList<>();
        while (resultSet.next()) {
            Quest quest = new Quest();
            quest.setName(resultSet.getString(Constants.QUEST_NAME));
            quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));
            BookingTO booking = new BookingTO();
            booking.setQuest(quest);
            booking.setId(resultSet.getInt(Constants.BOOKING_ID));
            booking.setStatus(Status.valueOf(resultSet.getString(Constants.BOOKING_STATUS).toUpperCase()));
            booking.setBookedDate(resultSet.getTimestamp(Constants.BOOKING_DATE));
            booking.setNumberOfGuests(resultSet.getInt(Constants.BOOKING_NUMBER_OF_GUESTS));
            bookingList.add(booking);
        }
        return bookingList;
    }
}
