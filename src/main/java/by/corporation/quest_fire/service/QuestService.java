package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.exception.QuestAlreadyExistException;
import by.corporation.quest_fire.service.exception.ServiceException;


import java.util.List;

public interface QuestService {
    List<Quest> fetchAllQuests(int currentPage) throws ServiceException;

    Quest fetchSingleQuest(long questId) throws ServiceException;

    void updateScore(long questId) throws ServiceException;

    void deleteQuest(long questId) throws ServiceException;

    void updateQuest(Quest quest) throws ServiceException, QuestAlreadyExistException;

    Integer addQuest(Quest quest) throws ServiceException, QuestAlreadyExistException;

    List<Quest> findAllQuestsByQuestRoom( long userId, int currentPage) throws ServiceException;

    String findQuestRoomName(long userId) throws ServiceException;

    int fetchNumberOfPages() throws ServiceException;

    int fetchQuestQuantityByQuestRoom(String questRoomName);

    List<Quest> fetchAllQuestsByRating(int currentPage) throws ServiceException;

    List<Quest> searchQuests(String name) throws ServiceException;

    void addImage(String image, long questId) throws ServiceException;

    int fetchNumberOfPagesByQuestRoomName(long userId) throws ServiceException;


}
