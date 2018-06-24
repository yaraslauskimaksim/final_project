package final_project.service.impl;

import final_project.dao.DAOFactory;
import final_project.dao.QuestDAO;
import final_project.dao.UserDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.db.impl.UserDAOImpl;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Quest;
import final_project.service.QuestService;
import final_project.service.exception.ServiceException;

import java.util.List;

public class QuestServiceImpl implements QuestService {

    private static final QuestDAO questDAO = DAOFactory.getInstance().getQuestDAO();

    @Override
    public List<Quest> showAllQuests() throws ServiceException {
        List<Quest>  quests = null;
        try {
            quests = questDAO.getAllQuests();
            if (quests.isEmpty()){
                return null;
            }
        } catch (ConnectionPoolException e) {
             throw new ServiceException("Exception occurs during validation of quests" , e);
        }
       return quests;
    }

    @Override
    public Quest getSingleQuest(int questId) throws SqlDaoException {
        Quest singleQuest = questDAO.getSingleQuest(questId);
        if(singleQuest==null){
            return null;
        }
        return singleQuest;
    }
}
