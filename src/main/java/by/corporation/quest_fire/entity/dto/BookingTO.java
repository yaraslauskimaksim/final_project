package by.corporation.quest_fire.entity.dto;

import by.corporation.quest_fire.entity.Entity;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;

public class BookingTO extends Entity implements Serializable {

    private int numberOfGuests;
    private User user;
    private Quest quest;
    private Status status;
    private Timestamp bookedDate;

    public BookingTO() {
    }

    public BookingTO(int id, Timestamp bookedDate, int numberOfGuests, User user, Quest quest, Status status) {
        super(id);
        this.bookedDate = bookedDate;
        this.numberOfGuests = numberOfGuests;
        this.user = user;
        this.quest = quest;
        this.status = status;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
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

    public void setBookedDate(Timestamp bookedDate) {
        this.bookedDate = bookedDate;
    }

    public Timestamp getBookedDate() {
        return bookedDate;
    }
}
