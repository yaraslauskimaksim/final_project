package by.corporation.final_project.entity;

import javax.jws.soap.SOAPBinding;

public class Message {

    private Integer id;
    private Integer userId;
    private String message;
    private User user;

    public Message(){}

    public Message(Integer id, Integer userId, String message, User user){
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this==null) return false;
        if(getClass()!=obj.getClass()) return false;
        Message message = (Message) obj;
        if (id == null) {
            if (message.id!= null)
                return false;
        } else if (!id.equals(message.id))
            return false;
        if (userId == null) {
            if (message.userId!= null)
                return false;
        } else if (!userId.equals(message.userId))
            return false;
        if (this.message == null) {
            if (message.message!= null)
                return false;
        } else if (!this.message.equals(message.message))
            return false;

        return true;
    }
    @Override
    public int hashCode() {
        return (int)(31*((message == null) ? 0 : message.hashCode()) + ((id == null) ? 0 :id.hashCode()) + ((userId == null) ? 0 : userId.hashCode()));
    }

    @Override
    public String toString() {
        return  id + " " + userId + " " + message;
    }
}
