package by.corporation.final_project.service;

import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.service.exception.ServiceException;

import java.util.List;

public interface QuestService {
    List<Quest> showAllQuests(int currentPage) throws ServiceException;
    Quest getSingleQuest(int questId) throws DaoException;
    int getScore(int questId, int score);
    void setScore(int score, int questId);
    void deleteQuest(int questId);
    void updateQuest(Quest quest);
    Integer addQuest(Quest quest) throws ServiceException;
    boolean isQuestExists(Quest quest);
    List<Quest> showAllQuestsOfQuestRoom(String questRoomName, int currentPage) throws ServiceException;
    String getQuestRoomName(int userId);
    int getQuestQuantity();
    int getQuestQuantityByQuestRoom(String questRoomName);
    int getQuestQuantityByQuestName(String questName);
    List<Quest> showAllQuestsByRating(int currentPage) throws ServiceException;
    List<Quest> searchQuests(String name, int currentPage) throws ServiceException;
}
