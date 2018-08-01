package by.corporation.quest_fire.entity.dto;

import by.corporation.quest_fire.entity.Entity;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommentTO extends Entity implements Serializable {

    private static final long serialVersionUID = -5105680222574964445L;
    private int commentId;
    private String description;
    private Timestamp time;
    private Status status;
    private Quest quest;
    private User user;

    public CommentTO() {
    }

    public CommentTO(int commentId, int userId, int questId, String description, Status status, Timestamp time, User user, Quest quest) {
        this.commentId = commentId;
        this.description = description;
        this.status = status;
        this.time = time;
        this.quest = quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quest getQuest() {
        return quest;
    }

    public User getUser() {
        return user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == null) return false;
        if (getClass() != obj.getClass()) return false;
        CommentTO comment = (CommentTO) obj;

        if (description == null) {
            if (comment.description != null)
                return false;
        } else if (!description.equals(comment.description))
            return false;
        if (status == null) {
            if (comment.status != null)
                return false;
        } else if (!status.equals(comment.status))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (31 * ((description == null) ? 0 : description.hashCode()) + ((status == null) ? 0 : status.hashCode()));
    }
}

