package by.corporation.quest_fire.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class Booking implements Serializable {

    private int bookingId;
    private int numberOfGuests;
    private Status status;
    private int userId;
    private int questId;
    private Timestamp timestamp;



    public Booking(){}

    public Booking( int bookingId, Timestamp timestamp, int numberOfGuests, Status status, int userId, int questId){
        this.bookingId=bookingId;
        this.timestamp=timestamp;
        this.numberOfGuests=numberOfGuests;
        this.status=status;
        this.userId=userId;
        this.questId =questId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }


    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public Status getStatus() {
        return status;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
