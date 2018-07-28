package by.corporation.quest_fire.entity.dto;

import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;

import java.sql.Timestamp;

public class BookingTO {

    private int bookingId;
    private int numberOfGuests;
    private User user;
    private Quest quest;
    private Status status;
    private Timestamp timestamp;

    public BookingTO() {
    }

    public BookingTO(int bookingId, Timestamp timestamp, int numberOfGuests, User user, Quest quest, Status status) {
        this.bookingId = bookingId;
        this.timestamp = timestamp;
        this.numberOfGuests = numberOfGuests;
        this.user = user;
        this.quest = quest;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public Status getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
