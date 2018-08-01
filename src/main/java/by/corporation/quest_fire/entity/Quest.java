package by.corporation.quest_fire.entity;

import java.io.Serializable;

public class Quest extends Entity implements Serializable {

    private static final long serialVersionUID = -4880227423370898268L;
    private long userId;
    private String genre;
    private String name;
    private String description;
    private String image;
    private double score;
    private String questRoomName;

    public Quest() {
    }


    public Quest(long questId, String genre, String name, String description, String image, double score, long userId, String questRoomName) {
       super(questId);
        this.genre = genre;
        this.name = name;
        this.description = description;
        this.image = image;
        this.score = score;
        this.userId=userId;
        this.questRoomName=questRoomName;
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

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getQuestRoomName() {
        return questRoomName;
    }

    public void setQuestRoomName(String questRoomName) {
        this.questRoomName = questRoomName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == null) return false;
        if (getClass() != obj.getClass()) return false;
        Quest quest = (Quest) obj;

        if (name == null) {
            if (quest.name != null)
                return false;
        } else if (!name.equals(quest.name))
            return false;
        if(score != quest.score) return false;
        if (description == null) {
            if (quest.description != null)
                return false;
        } else if (!description.equals(quest.description))
            return false;
        if (genre == null) {
            if (quest.genre != null)
                return false;
        } else if (!genre.equals(quest.genre))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (31 * score + ((name == null) ? 0 : name.hashCode()) + ((description == null) ? 0 : description.hashCode()) + ((genre == null) ? 0 : genre.hashCode()));
    }

    @Override
    public String toString() {
        return name + " " + description + " " + genre;
    }
}
