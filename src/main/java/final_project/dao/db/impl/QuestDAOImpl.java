package final_project.dao.db.impl;

import final_project.dao.db.ConnectionDAO;
import final_project.dao.QuestDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.pool.ConnectionPool;
import final_project.dao.pool.ConnectionPoolException;
import final_project.entity.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOImpl extends ConnectionDAO implements QuestDAO {

    private static final String SELECT_ALL_QUEST = "SELECT que_id, que_genre, que_name, que_description FROM quest LIMIT 2";
    private static final String SELECT_SINGLE_QUEST = "SELECT que_id, que_genre, que_name, que_description FROM quest where que_id=?";

    @Override
    public List<Quest> getAllQuests() throws ConnectionPoolException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Quest> products = new ArrayList<>();
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_QUEST);
            while (resultSet.next()) {
               Quest quest = new Quest();
               quest.setQuestId(resultSet.getInt("que_id"));
               quest.setName(resultSet.getString("que_name"));
               quest.setDescription(resultSet.getString("que_description"));
               quest.setGenre(resultSet.getString("que_genre"));
               products.add(quest);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ConnectionPoolException("Exception occurs during retrieving data of all quests", e);
        } finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(resultSet, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    @Override
    public Quest getSingleQuest(int que_id) throws SqlDaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SINGLE_QUEST);
            preparedStatement.setInt(1,que_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Quest quest = new Quest();
                quest.setQuestId(resultSet.getInt("que_id"));
                quest.setGenre(resultSet.getString("que_genre"));
                quest.setName(resultSet.getString("que_name"));
                quest.setDescription(resultSet.getString("que_description"));
                return quest;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new SqlDaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                closeConnection(connection);
                ConnectionPool.getInstance().closeDB(resultSet, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
