package final_project.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{

    private static final long serialVersionUID = -5105680222574964445L;
    private int userId;
    private int questId;
    private String description;
    private Date date;
    private boolean isApproved;

    public Comment(){};
    public Comment(String  description){
        this.description=description;
    };
    public Comment(int userId, int questId, String description, boolean isApproved){
        this.userId=userId;
        this.questId=questId;
        this.description=description;
        this.isApproved=isApproved;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
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

    public boolean isApproved() {
        return isApproved;
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
}
