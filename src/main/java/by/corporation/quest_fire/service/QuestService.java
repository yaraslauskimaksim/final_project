package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;

import java.util.List;

public interface QuestService {
    List<Quest> fetchAllQuests(int currentPage) throws ServiceException;
    Quest fetchSingleQuest(Integer questId) throws ServiceException;
    int fetchQuestScore(int questId, int score) throws ServiceException;
    void updateScore(int score, int questId) throws ServiceException;
    void deleteQuest(int questId);
    void updateQuest(Quest quest) throws ServiceException;
    Integer addQuest(Quest quest) throws ServiceException, ValidationException;
    boolean isQuestExists(Quest quest);
    List<Quest> findAllQuestsByQuestRoom(int currentPage, int userId) throws ServiceException;
    String findQuestRoomName(int userId) throws ServiceException;
    int fetchNumberOfPages() throws ServiceException;
    int getQuestQuantityByQuestRoom(String questRoomName);
    List<Quest> fetchAllQuestsByRating(int currentPage) throws ServiceException;
    List<Quest> searchQuests(String name) throws ServiceException;
    void addImage(String image, Integer questId) throws ServiceException;
    int fetchNumberOfPagesByQuestRoomName(int userId) throws ServiceException;

}
