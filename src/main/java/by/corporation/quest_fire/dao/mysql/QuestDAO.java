package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.pool.ConnectionPoolException;
import by.corporation.quest_fire.entity.Quest;

import java.util.List;

public interface QuestDAO {
    List<Quest> fetchAllQuests(int questPerPage, int currentPage) throws DaoException;
    Quest fetchSingleQuest(int questId)throws DaoException;
    int fetchScoreQuest(int questId) throws DaoException;
    void updateScore(int score, int questId) throws DaoException;
    void deleteQuest(int questId) throws DaoException;
    void updateQuest(Quest quest) throws DaoException;
    Integer addQuest(Quest quest) throws DaoException;
    boolean isQuestExists(Quest quest);
    List<Quest> getAllQuestByQuestRoomName(String questRoomName, int questPerPage, int currentPage) throws DaoException;
    String getQuestRoomName(int userId) throws DaoException;
    int fetchQuestQuantity() throws DaoException;
    int getQuestQuantityByQuestRoom(String questRoomName) throws DaoException;
    List<Quest> fetchAllQuestByRating(int questPerPage, int currentPage) throws  DaoException;
    List<Quest> searchQuests(String  name) throws  DaoException;
    void addImage(String image, Integer questId) throws DaoException;
}
