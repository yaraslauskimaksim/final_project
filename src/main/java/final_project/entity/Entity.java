package final_project.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -9073498577415600770L;
    private int id;

    public Entity(){};

    public Entity(int id){
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "";
    }
}
