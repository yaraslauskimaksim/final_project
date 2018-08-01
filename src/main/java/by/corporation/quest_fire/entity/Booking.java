package by.corporation.quest_fire.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class Booking extends Entity implements Serializable {

    private int numberOfGuests;
    private Status status;
    private long userId;
    private long questId;
    private Timestamp timestamp;


    public Booking(){}

    public Booking( int bookingId, Timestamp timestamp, int numberOfGuests, Status status, long userId, long questId){
        super(bookingId);
        this.timestamp=timestamp;
        this.numberOfGuests=numberOfGuests;
        this.status=status;
        this.userId=userId;
        this.questId =questId;
    }


    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
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

    public long getQuestId() {
        return questId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
