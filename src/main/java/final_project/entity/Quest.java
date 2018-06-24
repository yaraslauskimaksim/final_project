package final_project.entity;

import java.io.Serializable;

public class Quest implements Serializable{

    private static final long serialVersionUID = -4880227423370898268L;
    private int questId;
    private String genre;
    private String name;
    private String description;

    public Quest(){ }
    public Quest(int questId, String genre, String name, String description){
        this.questId=questId;
        this.genre=genre;
        this.name=name;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(this==null) return false;
        if(getClass()!=obj.getClass()) return false;
       Quest quest = (Quest) obj;

        if (name == null) {
            if (quest.name!= null)
                return false;
        } else if (!name.equals(quest.name))
            return false;
        if (description == null) {
            if (quest.description!= null)
                return false;
        } else if (!description.equals(quest.description))
            return false;
        if (genre == null) {
            if (quest.genre!= null)
                return false;
        } else if (!genre.equals(quest.genre))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int)(31*((name == null) ? 0 :name.hashCode())+((description == null) ? 0 :description.hashCode()) +((genre == null) ? 0 :genre.hashCode()));
    }

    @Override
    public String toString() {
        return name + " " + description + " " + genre;
    }
}
