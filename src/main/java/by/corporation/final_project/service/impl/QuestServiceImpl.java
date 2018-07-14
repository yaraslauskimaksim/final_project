package by.corporation.final_project.service.impl;

import by.corporation.final_project.dao.DAOFactory;
import by.corporation.final_project.dao.mysql.QuestDAO;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPoolException;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.service.QuestService;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.util.Constants;


import java.util.List;

public class QuestServiceImpl implements QuestService {

    private static QuestService QUEST_SERVICE = new QuestServiceImpl();

    public static QuestService getQuestService() {
        return QUEST_SERVICE;
    }

    private QuestServiceImpl() {
    }

    private static final QuestDAO questDAO = DAOFactory.getInstance().getQuestDAO();

    @Override
    public List<Quest> showAllQuests(int currentPage) throws ServiceException {
        List<Quest> quests = null;
        try {
            if(currentPage >= 0){
                quests = questDAO.getAllQuests(Constants.ITEMS_PER_PAGE, currentPage);
            }
            if (quests.isEmpty()) {
                    return null;
            }
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Exception occurs during validation of quests", e);
        }
        return quests;
    }

    @Override
    public List<Quest> searchQuests(String name, int currentPage) throws ServiceException {
        List<Quest> quests = null;
        try {
            if(currentPage >= 0){
                quests = questDAO.searchQuests(name, Constants.ITEMS_PER_PAGE, currentPage);
            }
            if (quests.isEmpty()) {
                return null;
            }
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Exception occurs during validation of quests", e);
        }
        return quests;
    }


    @Override
    public Quest getSingleQuest(int questId) throws DaoException {
        Quest singleQuest = questDAO.getSingleQuest(questId);
        if (singleQuest == null) {
            return null;
        }
        return singleQuest;
    }

    @Override
    public int getScore(int questId, int score) {
        int s = 0;
        try {
            s = questDAO.getScoreQuest(questId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return score + s;
    }

    @Override
    public void setScore(int score, int questId) {
        int newScore = getScore(questId, score);
        try {
            questDAO.rateQuest(score, questId);
        } catch (DaoException e) {
            e.printStackTrace();
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
    public void updateQuest(Quest quest) {
        try {
            questDAO.updateQuest(quest);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer addQuest(Quest quest) throws ServiceException {
        int questId = 0;
        try {
            if (!isQuestExists(quest)) {
                questId = questDAO.addQuest(quest);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during saving a new user on service layer", e);
        }
        return questId;
    }


    @Override
    public boolean isQuestExists(Quest quest) {
        boolean isQuestExists = questDAO.isQuestExists(quest);
        return isQuestExists;
    }

    @Override
    public List<Quest> showAllQuestsOfQuestRoom(String questRoomName, int currentPage) throws ServiceException {
        List<Quest>  quests = null;
        try {
            if(currentPage >= 0){
                quests = questDAO.getAllQuestByQuestRoomName(questRoomName, Constants.ITEMS_PER_PAGE, currentPage);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during " , e);
        }
        if (quests.isEmpty()){
            return null;
        }
        return quests;
    }

    public List<Quest> showAllQuestsByRating(int currentPage) throws ServiceException {
        List<Quest>  quests = null;
        try {
            if(currentPage >= 0){
                quests = questDAO.getAllQuestByRating(Constants.ITEMS_PER_PAGE, currentPage);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception occurs during " , e);
        }
        if (quests.isEmpty()){
            return null;
        }
        return quests;
    }

    public String getQuestRoomName(int userId){
        String name = null;
        try {
            name  = questDAO.getQuestRoomName(userId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public int getQuestQuantity() {
        int counter = 0;
        try {
            counter = questDAO.getQuestQuantity();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return counter;
    }
    @Override
    public int getQuestQuantityByQuestRoom(String questRoomName) {
        int counter = 0;
        try {
            counter = questDAO.getQuestQuantityByQuestRoom(questRoomName);
        }  catch (DaoException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public int getQuestQuantityByQuestName(String questName) {
        int counter = 0;
        try {
            counter = questDAO.getQuestQuantityByQuestName(questName);
        }  catch (DaoException e) {
            e.printStackTrace();
        }
        return counter;
    }

}









