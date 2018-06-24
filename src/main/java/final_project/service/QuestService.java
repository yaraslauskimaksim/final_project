package final_project.service;

import final_project.dao.db.SqlDaoException;
import final_project.entity.Quest;
import final_project.service.exception.ServiceException;

import java.util.List;

public interface QuestService {
    List<Quest> showAllQuests() throws ServiceException;
    Quest getSingleQuest(int questId) throws SqlDaoException;
}
