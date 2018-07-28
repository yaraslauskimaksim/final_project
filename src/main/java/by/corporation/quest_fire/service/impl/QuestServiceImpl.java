package by.corporation.quest_fire.service.impl;

import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.QuestDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.exception.QuestAlreadyExistException;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.Constant;


import java.util.List;

public class QuestServiceImpl implements QuestService {

    private static final QuestDAO questDAO = DAOFactory.getInstance().getQuestDAO();

    /**
     * The method returns the collection of {@link Quest}
     *
     * @param currentPage is for pagination
     * @throws ServiceException the service exception
     */
    @Override
    public List<Quest> fetchAllQuests(int currentPage) throws ServiceException {
        List<Quest> quests = null;
        try {
            quests = questDAO.fetchAllQuests(Constant.ITEMS_PER_PAGE, currentPage);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving all quests", e);
        }
        return quests;
    }

    /**
     * The method returns the collection of {@link Quest} accordingly to the filled in
     * @param questName
     * @throws ServiceException the service exception
     */

    @Override
    public List<Quest> searchQuests(String questName) throws ServiceException {
        List<Quest> quests = null;
        try {
            quests = questDAO.searchQuests(questName);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving quests which are searched by name", e);
        }
        return quests;
    }

    @Override
    public void addImage(String image, Integer questId) throws ServiceException {
        try {
            questDAO.addImage(image, questId);
        } catch (DaoException e) {
            throw new ServiceException("exception occurs during adding image", e);
        }
    }


    @Override
    public Quest fetchSingleQuest(Integer questId) throws ServiceException {
        Quest singleQuest = null;
        try {
            singleQuest = questDAO.fetchSingleQuest(questId);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving a single quest", e);
        }
        return singleQuest;
    }


    /**
     * The method update the score for the {@link Quest}
     * @param questId is for what quest
     * @throws ServiceException the service exception
     */
    @Override
    public void updateScore(int questId) throws ServiceException {
        try {
            Quest quest = questDAO.fetchSingleQuest(questId);
            int newScore = fetchQuestScore(quest.getQuestId(), quest.getScore());
            quest.setScore(newScore);
            questDAO.update(quest);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during updating score of the quest", e);
        }
    }
    /**
     * The method delete the {@link Quest}
     * @param questId is for verifying which quest needs to be deleted
     * @throws ServiceException
     */
    @Override
    public void deleteQuest(int questId) throws ServiceException {
        try {
            questDAO.deleteQuest(questId);
        } catch (DaoException e) {
           throw new ServiceException("Exception occurs during quest deleting", e);
        }
    }
    /**
     * @param quest is for updating the quest
     * @throws ServiceException the service exception
     * @throws QuestAlreadyExistException if quest with such name already exists
     */
    @Override
    public void updateQuest(Quest quest) throws ServiceException, QuestAlreadyExistException {
        try {
            if (!isQuestExists(quest)) {
                questDAO.updateQuest(quest);
            }else {
                throw new QuestAlreadyExistException("Quest already exists");
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during updating quest", e);
        }
    }
    /**
     * The method returns the auto generated id for a new quest
     * @param quest is for creating a new quest
     * @throws ServiceException the service exception
     * @throws QuestAlreadyExistException if quest with such name already exists
     */
    @Override
    public Integer addQuest(Quest quest) throws ServiceException,QuestAlreadyExistException {
        int questId = 0;
        try {
            if (!isQuestExists(quest)) {
                questId = questDAO.addQuest(quest);
            }else {
                throw new QuestAlreadyExistException("Quest already exists");
            }
            return questId;
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during adding a new quest.", e);
        }
    }

    @Override
    public List<Quest> findAllQuestsByQuestRoom(int userId, int currentPage) throws ServiceException {
        String questRoomName = findQuestRoomName(userId);
        List<Quest> quests = null;
        try {
            quests = questDAO.getAllQuestByQuestRoomName(questRoomName, Constant.ITEMS_PER_PAGE, currentPage);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during ", e);
        }
        return quests;
    }


    /**
     * The method returns the collection of {@link Quest} according to rating
     *
     * @param currentPage is for pagination
     * @throws ServiceException the service exception
     */
    public List<Quest> fetchAllQuestsByRating(int currentPage) throws ServiceException {
        List<Quest> quests = null;
        try {
            quests = questDAO.fetchAllQuestByRating(Constant.ITEMS_PER_PAGE, currentPage);

        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during retrieving quests according to the rating ", e);
        }
        return quests;
    }

    public String findQuestRoomName(int userId) throws ServiceException {
        String name = null;
        try {
            name = questDAO.getQuestRoomName(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during finding quest room name", e);
        }
        return name;
    }


    @Override
    public int fetchQuestQuantityByQuestRoom(String questRoomName) {
        int counter = 0;
        try {
            counter = questDAO.fetchQuestQuantityByQuestRoom(questRoomName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public int fetchNumberOfPagesByQuestRoomName(int userId) throws ServiceException {
        String questRoomName = null;
        int numberOfPages = 0;
        try {
            questRoomName = findQuestRoomName(userId);
            int numberOfRecords = fetchQuestQuantityByQuestRoom(questRoomName);
            numberOfPages = FrontControllerUtil.getNumberOfPage(numberOfRecords, Constant.ITEMS_PER_PAGE);
        } catch (ServiceException e) {
            throw new ServiceException("", e);
        }

        return numberOfPages;
    }

    /**
     * The method returns the quantity of {@link Quest} for pagination
     *
     * @throws ServiceException the service exception
     */
    @Override
    public int fetchNumberOfPages() throws ServiceException {
        int numberOfPages = 0;
        try {
            int numberOfRecords = questDAO.fetchQuestQuantity();
            numberOfPages = FrontControllerUtil.getNumberOfPage(numberOfRecords, Constant.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("Exception during retrieving quantity of quests", e);
        }
        return numberOfPages;
    }


    /**
     * The method returns true if quest with certain name exists,
     * or returns false if not.
     */
    private boolean isQuestExists(Quest quest) throws DaoException {
        Quest quest1 = questDAO.fetchSingleQuest(quest.getQuestId());
        boolean isQuestExists = questDAO.isQuestExists(quest);
        return isQuestExists && !(quest1.getName().equalsIgnoreCase(quest.getName()));
    }

    /**
     * The method returns the score of the quest and adds the new score to it
     * @param questId is for what quest
     * @param score is new score
     * @throws ServiceException the service exception
     */

    private int fetchQuestScore(int questId, int score) throws ServiceException {
        int newScore = 0;
        try {
            newScore = questDAO.fetchScoreQuest(questId);
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during updating the score", e);
        }
        return score + newScore;
    }

}









