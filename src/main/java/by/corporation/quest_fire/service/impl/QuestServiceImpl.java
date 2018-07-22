package by.corporation.quest_fire.service.impl;

import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.dao.DAOFactory;
import by.corporation.quest_fire.dao.mysql.QuestDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Quest;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.ValidationException;
import by.corporation.quest_fire.util.Constants;


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
            quests = questDAO.fetchAllQuests(Constants.ITEMS_PER_PAGE, currentPage);
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
     * The method returns the score of the quest and adds the new score to it
     * @param questId is for what quest
     * @param score is new score
     * @throws ServiceException the service exception
     */
    @Override
    public int fetchQuestScore(int questId, int score) throws ServiceException {
        int newScore = 0;
        try {
            newScore = questDAO.fetchScoreQuest(questId);
        } catch (DaoException e) {
           throw new ServiceException("Exception occurs during updating the score", e);
        }
        return score + newScore;
    }
    /**
     * The method update the score for the {@link Quest}
     * @param questId is for what quest
     * @param score is new score
     * @throws ServiceException the service exception
     */
    @Override
    public void updateScore(int score, int questId) throws ServiceException {
        try {
            int newScore = fetchQuestScore(questId, score);
            if(newScore >= 0 ){
                questDAO.updateScore(score, questId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during updating score of the quest", e);
        }
    }

    @Override
    public void deleteQuest(int questId) {
        try {
            questDAO.deleteQuest(questId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateQuest(Quest quest) throws ServiceException {
        try {
            questDAO.updateQuest(quest);
        } catch (DaoException e) {
            throw new ServiceException("Exception during updating quest", e);
        }
    }
    /**
     * The method returns the auto generated id for a new quest
     * @param quest is for creating a new quest
     * @throws ServiceException the service exception
     * @throws ValidationException if quest with such name already exists
     */
    @Override
    public Integer addQuest(Quest quest) throws ServiceException, ValidationException {
        int questId = 0;
        try {
            if (!isQuestExists(quest)) {
                questId = questDAO.addQuest(quest);
            }else {
                throw new ValidationException("Quest already exists");
            }
            return questId;
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during saving a new user on service layer", e);
        }
    }


    @Override
    public boolean isQuestExists(Quest quest) {
        boolean isQuestExists = questDAO.isQuestExists(quest);
        return isQuestExists;
    }

    @Override
    public List<Quest> findAllQuestsByQuestRoom(int userId, int currentPage) throws ServiceException {
        String questRoomName = findQuestRoomName(userId);
        List<Quest> quests = null;
        try {
            quests = questDAO.getAllQuestByQuestRoomName(questRoomName, Constants.ITEMS_PER_PAGE, currentPage);
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
            quests = questDAO.fetchAllQuestByRating(Constants.ITEMS_PER_PAGE, currentPage);

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
    public int getQuestQuantityByQuestRoom(String questRoomName) {
        int counter = 0;
        try {
            counter = questDAO.getQuestQuantityByQuestRoom(questRoomName);
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
            int numberOfRecords = getQuestQuantityByQuestRoom(questRoomName);
            numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);
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
            numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException("Exception during retrieving quantity of quests", e);
        }
        return numberOfPages;
    }

}









