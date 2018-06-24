package final_project.entity;


import java.io.Serializable;

public class UserDetails implements Serializable{

    private static final long serialVersionUID = -9030452594343416515L;
    private Role role;
    private String email;
    private String password;
    private int id;


    public UserDetails(){};
    public UserDetails(String email, String password){
        this.email=email;
        this.password=password;
    }
    public UserDetails(String email, String password, Role role){
        this.email=email;
        this.password=password;
        this.role=role;
    }

    public UserDetails(String email, String password, Role role, int id){
        this.email=email;
        this.password=password;
        this.role=role;
        this.id=id;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this==null) return false;
        if(getClass()!=obj.getClass()) return false;
        UserDetails userDetails = (UserDetails) obj;
        if (email == null) {
            if (userDetails.email!= null)
                return false;
        } else if (!email.equals(userDetails.email))
            return false;
        if(password==null){
            if(userDetails.password!=null)
                return false;
        }else if(!password.equals(userDetails.password))
            return false;


        return true;
    }

    @Override
    public int hashCode() {
        return (int)(31*((email == null) ? 0 : email.hashCode())+((password == null) ? 0 :password.hashCode()) );
    }

    @Override
    public String toString() {
        return  email + " " + password;
    }
}
