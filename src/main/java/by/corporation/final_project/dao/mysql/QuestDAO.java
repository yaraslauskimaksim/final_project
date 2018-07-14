package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPoolException;
import by.corporation.final_project.entity.Quest;

import java.util.List;

public interface QuestDAO {
    List<Quest> getAllQuests(int currentPage, int itemsPerPage) throws ConnectionPoolException;
    Quest getSingleQuest(int questId) throws DaoException;
    int getScoreQuest(int questId) throws DaoException;
    void rateQuest(int score, int questId) throws DaoException;
    void deleteQuest(int questId) throws DaoException;
    void updateQuest(Quest quest) throws DaoException;
    Integer addQuest(Quest quest) throws DaoException;
    boolean isQuestExists(Quest quest);
    List<Quest> getAllQuestByQuestRoomName(String questRoomName, int questPerPage, int currentPage) throws DaoException;
    String getQuestRoomName(int userId) throws DaoException;
    int getQuestQuantity() throws ConnectionPoolException;
    int getQuestQuantityByQuestRoom(String questRoomName) throws DaoException;
    int getQuestQuantityByQuestName(String questName) throws DaoException;
    List<Quest> getAllQuestByRating(int questPerPage, int currentPage) throws  DaoException;
    List<Quest> searchQuests(String  name, int questPerPage, int currentPage) throws ConnectionPoolException;
}
