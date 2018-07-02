package by.corporation.final_project.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{

    private static final long serialVersionUID = -5105680222574964445L;
    private int commentId;
    private int userId;
    private int questId;
    private String description;
    private Date date;
    private Status status;
    private Quest quest;
    private User user;

    public Comment(){};
    public Comment(String  description){
        this.description=description;
    };
    public Comment(int userId, int questId, String description){
        this.userId=userId;
        this.questId=questId;
        this.description=description;

    }
    public Comment(int userId, int questId, String description, Status status){
        this.userId=userId;
        this.questId=questId;
        this.description=description;
        this.status=status;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }



    public int getQuestId() {
        return questId;
    }

    public int getUserId() {
        return userId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this==null) return false;
        if(getClass()!=obj.getClass()) return false;
        Comment comment = (Comment) obj;

        if (description == null) {
            if (comment.description!= null)
                return false;
        } else if (!description.equals(comment.description))
            return false;
        if (status == null) {
            if (comment.status!= null)
                return false;
        } else if (!status.equals(comment.status))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int)(31*((description == null) ? 0 :description.hashCode())+((status == null) ? 0 :status.hashCode()));
    }
}
