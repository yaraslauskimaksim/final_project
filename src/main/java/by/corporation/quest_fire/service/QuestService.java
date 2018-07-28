package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.exception.QuestAlreadyExistException;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;

import java.util.List;

public interface QuestService {
    List<Quest> fetchAllQuests(int currentPage) throws ServiceException;
    Quest fetchSingleQuest(Integer questId) throws ServiceException;
    void updateScore(int questId) throws ServiceException;
    void deleteQuest(int questId) throws ServiceException;
    void updateQuest(Quest quest) throws ServiceException, QuestAlreadyExistException;
    Integer addQuest(Quest quest) throws ServiceException, QuestAlreadyExistException;
    List<Quest> findAllQuestsByQuestRoom(int currentPage, int userId) throws ServiceException;
    String findQuestRoomName(int userId) throws ServiceException;
    int fetchNumberOfPages() throws ServiceException;
    int fetchQuestQuantityByQuestRoom(String questRoomName);
    List<Quest> fetchAllQuestsByRating(int currentPage) throws ServiceException;
    List<Quest> searchQuests(String name) throws ServiceException;
    void addImage(String image, Integer questId) throws ServiceException;
    int fetchNumberOfPagesByQuestRoomName(int userId) throws ServiceException;


}
