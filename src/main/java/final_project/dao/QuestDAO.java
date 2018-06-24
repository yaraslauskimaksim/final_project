package final_project.dao;

import final_project.dao.db.SqlDaoException;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Quest;

import java.util.List;

public interface QuestDAO {
    List<Quest> getAllQuests() throws ConnectionPoolException;
    Quest getSingleQuest(int questId) throws SqlDaoException;
}
