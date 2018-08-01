package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Quest;

import java.util.List;

public interface QuestDAO {
    List<Quest> fetchAllQuests(int questPerPage, int currentPage) throws DaoException;
    Quest fetchSingleQuest(long questId)throws DaoException;
    double fetchScoreQuest(long questId) throws DaoException;
    void updateScore(double score, int questId) throws DaoException;
    void deleteQuest(long questId) throws DaoException;
    void updateQuest(Quest quest) throws DaoException;
    Integer addQuest(Quest quest) throws DaoException;
    boolean isQuestExists(Quest quest);
    List<Quest> getAllQuestByQuestRoomName(String questRoomName, int questPerPage, int currentPage) throws DaoException;
    String getQuestRoomName(long userId) throws DaoException;

    int fetchQuestQuantity() throws DaoException;
    int fetchQuestQuantityByQuestRoom(String questRoomName) throws DaoException;
    List<Quest> fetchAllQuestByRating(int questPerPage, int currentPage) throws  DaoException;
    List<Quest> searchQuests(String name) throws  DaoException;
    void addImage(String image, long questId) throws DaoException;
    void update(Quest quest) throws DaoException;
}
