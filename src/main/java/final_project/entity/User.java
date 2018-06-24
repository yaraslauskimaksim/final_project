package final_project.entity;

import java.io.Serializable;

public class User extends Entity implements Serializable {

    private static final long serialVersionUID = 5103603126812784493L;
    private String firstName;
    private String lastName;
    private Role role;


    public User(){};
    public User(String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public int getId() {
        return super.getId();
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
        return true;
    }
    @Override
    public int hashCode() {
        return (int)(31*((firstName == null) ? 0 : firstName.hashCode())+((lastName== null) ? 0 : lastName.hashCode()) );
    }

    @Override
    public String toString() {
        return  firstName + " " + lastName;
    }
}
