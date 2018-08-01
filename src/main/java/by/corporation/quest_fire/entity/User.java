package by.corporation.quest_fire.entity;

import java.io.Serializable;

public class User extends Entity implements Serializable {

    private static final long serialVersionUID = 5103603126812784493L;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private Status status;


    public User(){}


    public User(String firstName, String lastName, String email, String password, Role role, long id, Status status){
        super(id);
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.role=role;
        this.status=status;
    }


    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this==null) return false;
        if(getClass()!=obj.getClass()) return false;
        User user = (User) obj;
        if (firstName == null) {
            if (user.firstName!= null)
                return false;
        } else if (!firstName.equals(user.firstName))
            return false;
        if(lastName==null){
            if(user.lastName!=null)
                return false;
        }else if(!lastName.equals(user.lastName))
            return false;
        if (email == null) {
            if (user.email!= null)
                return false;
        } else if (!email.equals(user.email))
            return false;
        if(password==null){
            if(user.password!=null)
                return false;
        }else if(!password.equals(user.password))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        return (int)(31*((firstName == null) ? 0 : firstName.hashCode())+((lastName== null) ? 0 : lastName.hashCode()) + ((password == null) ? 0 : password.hashCode()) + ((email == null) ? 0 : email.hashCode()) );
    }

    @Override
    public String toString() {
        return  firstName + " " + lastName;
    }
}
