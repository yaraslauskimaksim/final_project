package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.BookingDAO;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.entity.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    Connection connection = null;

    public BookingDAOImpl(ConnectionPool connectionPool){
        connection = connectionPool.getConnection();
    }

    private static final String SAVE_NEW_BOOKING_DETAIL= "insert into booking(boo_user_id, boo_quest_id, boo_date, boo_number_of_guests) values (?,?,?,?)";
    private static final String SHOW_BOOKING_DETAIL_BY_QUEST_ROOM= "select boo_id, boo_user_id, boo_quest_id, boo_date, boo_number_of_guests, boo_status, que_name, usr_firstname, usr_lastname from booking left join quest on quest.que_id = booking.boo_quest_id left join user on user.usr_id = booking.boo_user_id where que_room_name = ? LIMIT ? OFFSET ?";
    private static final String GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME = "SELECT COUNT(*) FROM BOOKING left join quest on quest.que_id = booking.boo_quest_id where que_name=?";
    private static final String APPROVE_BOOKING = "UPDATE booking SET boo_status = 'approved' WHERE boo_id = ?";
    private static final String REJECT_BOOKING = "UPDATE booking SET boo_status = 'rejected' WHERE boo_id = ?";
    private static final String SELECT_STATUS = "SELECT boo_id, boo_status FROM booking WHERE boo_id = ?";


    @Override
    public int saveBookigDetails(Booking booking) throws DaoException {
        int bookingId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_BOOKING_DETAIL, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, booking.getUserId());
            preparedStatement.setInt(2, booking.getQuestId());
            preparedStatement.setDate(3, new Date(booking.getDate().getTime()));
            preparedStatement.setInt(4, booking.getNumberOfGuests());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                resultSet.next();
                bookingId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during saving booking details", e);
        }
        return bookingId;
    }

    @Override
    public List<Booking> showUserBooking(String questRoomName, int currentPage, int questPerPage) throws DaoException {
        List<Booking> bookingList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_BOOKING_DETAIL_BY_QUEST_ROOM);) {
            preparedStatement.setString(1, questRoomName);
            preparedStatement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            preparedStatement.setInt(3, startIndex);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
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
                    booking.setDate(resultSet.getDate("boo_date"));
                    booking.setNumberOfGuests(resultSet.getInt("boo_number_of_guests"));
                    booking.setStatus(Status.valueOf(resultSet.getString("boo_status").toUpperCase()));
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about all bookings", e);
        }
        return bookingList;
    }

    @Override
    public int getBookingQuantityByQuestName(String questName) throws DaoException {
        int counter = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BOOKING_QUANTITY_BY_QUEST_NAME)) {
            preparedStatement.setString(1, questName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    counter =  resultSet.getInt(1);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return counter;
    }

    @Override
    public void approveBooking(int bookingId) throws DaoException {
        try (PreparedStatement  preparedStatement = connection.prepareStatement(APPROVE_BOOKING);){
            preparedStatement.setInt(1, bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void rejectBooking(int  bookingId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REJECT_BOOKING);){
            preparedStatement.setInt(1,  bookingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
    }

    @Override
    public Status getStatus(int bookingId) throws DaoException {
        Status status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);){
            preparedStatement.setInt(1, bookingId);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                 status = Status.valueOf(resultSet.getString("boo_status").toUpperCase());

                }
            }
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during set status to be rejected", e);
        }
        return status;
    }


}
