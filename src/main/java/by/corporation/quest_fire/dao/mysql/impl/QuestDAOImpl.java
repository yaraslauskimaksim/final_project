package by.corporation.quest_fire.dao.mysql.impl;

import by.corporation.quest_fire.dao.exception.ConnectionPoolException;
import by.corporation.quest_fire.dao.mysql.QuestDAO;
import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.mysql.TransactionManager;
import by.corporation.quest_fire.dao.pool.ConnectionPool;
import by.corporation.quest_fire.dao.pool.PooledConnection;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.Quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for retrieving data connected with quests
 */
public class QuestDAOImpl implements QuestDAO {

    private static final Logger LOGGER = LogManager.getLogger(QuestDAOImpl.class);


    private static final String SELECT_ALL_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image, que_score,  que_room_name  FROM quest LIMIT ? OFFSET ?";
    private static final String SELECT_SINGLE_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name FROM quest where que_id=?";
    private static final String SELECT_QUEST_SCORE = "SELECT que_id, que_score from quest WHERE que_id = ?";
    private static final String UPDATE_SCORE = "UPDATE quest SET que_score = ? WHERE que_id = ?";
    private static final String DELETE_QUEST = "DELETE FROM quest WHERE que_id = ?";
    private static final String UPDATE_QUEST = "UPDATE quest SET que_genre = ?, que_name = ?, que_description = ? where que_id = ?";
    private static final String ADD_NEW_QUEST = "insert into quest(que_genre, que_name, que_description, que_user_id, que_room_name) values (?,?,?,?,?)";
    private static final String IS_QUEST_EXISTS = "SELECT EXISTS(SELECT 1 FROM quest WHERE que_name = ? )";
    private static final String SELECT_QUESTS_BY_ROOM_NAME = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id, que_score  FROM quest  WHERE que_room_name = ?  LIMIT ? OFFSET ?";
    private static final String SELECT_QUEST_ROOM_NAME = "SELECT que_room_name FROM quest where que_user_id =?";
    private static final String GET_ALL_QUEST_QUANTITY = "SELECT COUNT(*) FROM quest";
    private static final String SELECT_ALL_QUEST_QUANTITY_BY_ROOM_NAME = "SELECT COUNT(*) FROM quest where que_room_name=?";
    private static final String SELECT_ALL_QUESTS_BY_RATING = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id, que_score FROM quest ORDER BY que_score DESC LIMIT ? OFFSET ?";
    private static final String SEARCH_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id FROM quest WHERE que_name LIKE ?";
    private static final String ADD_IMAGE = "UPDATE quest SET que_image = ? WHERE que_id = ?";
    private static final String DELETE_COMMENT = "DELETE from comment WHERE com_quest_id = ?";
    private static final String UPDATE = "UPDATE quest SET que_name = ?, que_genre = ?, que_description = ?, que_image = ?, que_score = ?, que_room_name = ? where que_id = ?";

    /**
     * The method returns the collection of {@link Quest} from db.
     *
     * @param questPerPage is the constant for limiting quests per page
     * @param currentPage  is the page where user is currently
     * @throws DaoException the dao exception
     */
    @Override
    public List<Quest> fetchAllQuests(int questPerPage, int currentPage) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_QUEST);
            statement.setInt(1, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            statement.setInt(2, startIndex);
            resultSet = statement.executeQuery();
            List<Quest> quests = formQuests(resultSet);
            return quests;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving all quests", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    @Override
    public int fetchQuestQuantityByQuestRoom(String questRoomName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUEST_QUANTITY_BY_ROOM_NAME);
            preparedStatement.setString(1, questRoomName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of quest according to the search criteria", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }


    /**
     * The method returns the quantity of quests for pagination
     *
     * @throws DaoException the dao exception
     */
    @Override
    public int fetchQuestQuantity() throws DaoException {
        PooledConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int counter = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_QUEST_QUANTITY);
            while (resultSet.next()) {
                counter = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving the quantity of quests", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return counter;
    }

    /**
     * The method returns the collection of {@link Quest} from db accordingly to the search (quest name)
     *
     * @throws DaoException the dao exception
     */
    @Override
    public List<Quest> searchQuests(String name) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Quest> quests = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SEARCH_QUEST);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Quest quest = new Quest();
                quest.setQuestId(resultSet.getInt(Constants.QUEST_ID));
                quest.setName(resultSet.getString(Constants.QUEST_NAME));
                quest.setDescription(resultSet.getString(Constants.QUEST_DESCRIPTION));
                quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));
                quest.setImage(resultSet.getString(Constants.QUEST_IMAGE));
                quests.add(quest);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of quest according to the search criteria", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return quests;
    }

    @Override
    public void addImage(String image, Integer questId) throws DaoException {
        ResultSet resultSet = null;
        String addeImage = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_IMAGE);) {
            preparedStatement.setString(1, image);
            preparedStatement.setInt(2, questId);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException("Exception occurs during adding image", e);
        }
    }

    @Override
    public void update(Quest quest) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE);
            formUpdate(statement, quest);
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during adding image", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeStatement(statement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }

    /**
     * The method returns the collection of {@link Quest} from db order by rating.
     *
     * @param questPerPage is the constant for limiting quests per page
     * @param currentPage  is the page where user is currently
     * @throws DaoException the dao exception
     */
    @Override
    public List<Quest> fetchAllQuestByRating(int questPerPage, int currentPage) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Quest> quests = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_ALL_QUESTS_BY_RATING);
            statement.setInt(1, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            statement.setInt(2, startIndex);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Quest quest = new Quest();
                quest.setQuestId(resultSet.getInt(Constants.QUEST_ID));
                quest.setName(resultSet.getString(Constants.QUEST_NAME));
                quest.setDescription(resultSet.getString(Constants.QUEST_DESCRIPTION));
                quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));
                quest.setImage(resultSet.getString(Constants.QUEST_IMAGE));
                quest.setQuestRoomName(resultSet.getString(Constants.QUEST_ROOM_NAME));
                quest.setScore(resultSet.getInt(Constants.QUEST_SCORE));
                quests.add(quest);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, statement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return quests;
    }


    /**
     * The method returns the {@link Quest} from db by
     *
     * @param questId
     * @throws DaoException the dao exception
     */
    @Override
    public Quest fetchSingleQuest(int questId) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SINGLE_QUEST);
            preparedStatement.setInt(1, questId);
            resultSet = preparedStatement.executeQuery();
            Quest quest = formQuest(resultSet);
            return quest;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
    }


    /**
     * The method returns the score of the quest
     *
     * @param questId is for identifying for what quest
     * @throws DaoException the dao exception
     */
    @Override
    public int fetchScoreQuest(int questId) throws DaoException {
        PooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int score = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_QUEST_SCORE);
            preparedStatement.setInt(1, questId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Quest quest = new Quest();
                quest.setScore(resultSet.getInt(Constants.QUEST_SCORE));
                score = quest.getScore();
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data of quest score", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return score;
    }

    /**
     * The method update quest score
     *
     * @param questId is for identifying for what quest
     * @param score   is new score for updating
     * @throws DaoException the dao exception
     */
    @Override
    public void updateScore(int score, int questId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCORE);) {
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, questId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during updating score", e);
        }
    }

    /**
     * The method is for deleting quest.
     * There is a transaction here. Comments, connected to the quest,
     * are also deleted.
     *
     * @param questId is for verifying what quest needs to be deleted
     * @throws DaoException the dao exception
     */
    @Override
    public void deleteQuest(int questId) throws DaoException {
        ResultSet resultSet = null;
        PooledConnection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        TransactionManager transactionManager = null;
        try { connection = ConnectionPool.getInstance().getConnection();
             statement1 = connection.prepareStatement(DELETE_COMMENT);
             statement2 = connection.prepareStatement(DELETE_QUEST);
            transactionManager = new TransactionManager();
            transactionManager.startTransaction();
            statement1.setInt(1, questId);
            statement1.executeUpdate();

            statement2.setInt(1, questId);
            statement2.executeUpdate();

            transactionManager.commit();
            transactionManager.stopTransaction();

        } catch (SQLException e) {
            transactionManager.rollback();
            throw new DaoException("Exception occurs during deleting quest", e);
        }
    }

    @Override
    public void updateQuest(Quest quest) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUEST);) {

            preparedStatement.setString(1, quest.getGenre());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setInt(4, quest.getQuestId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Exception during rollback transaction of replacing product.", e);
        }

    }

    /**
     * The method returns the auto generated id for new quest
     *
     * @throws DaoException the dao exception
     */
    public Integer addQuest(Quest quest) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer idQuest = 0;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(ADD_NEW_QUEST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, quest.getGenre());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setInt(4, quest.getUserId());
            preparedStatement.setString(5, quest.getQuestRoomName());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            idQuest = resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DaoException("Exception during adding a new quest", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return idQuest;
    }

    @Override
    public boolean isQuestExists(Quest quest) {

        boolean exists = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_QUEST_EXISTS);) {
            preparedStatement.setString(1, quest.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                resultSet.next();
                exists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @Override
    public List<Quest> getAllQuestByQuestRoomName(String questRoomName, int questPerPage, int currentPage) throws DaoException {

        List<Quest> quests = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUESTS_BY_ROOM_NAME);) {
            preparedStatement.setString(1, questRoomName);
            preparedStatement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            preparedStatement.setInt(3, startIndex);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setQuestId(resultSet.getInt("que_id"));
                    quest.setGenre(resultSet.getString("que_genre"));
                    quest.setName(resultSet.getString("que_name"));
                    quest.setDescription(resultSet.getString("que_description"));
                    quest.setImage(resultSet.getString("que_image"));
                    quest.setUserId(resultSet.getInt("que_user_id"));
                    quest.setQuestRoomName(resultSet.getString("que_room_name"));
                    quest.setScore(resultSet.getInt("que_score"));
                    quests.add(quest);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return quests;
    }

    @Override
    public String getQuestRoomName(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String name = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_QUEST_ROOM_NAME);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString(Constants.QUEST_ROOM_NAME);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving quest room name", e);
        } finally {
            try {
                ConnectionPool.getInstance().closeDBResources(resultSet, preparedStatement);
                ConnectionPool.getInstance().closeConnectionPool();
            } catch (SQLException | ConnectionPoolException e) {
                LOGGER.warn("Exception during closing DB resources.", e);
            }
        }
        return name;
    }

    private void formUpdate(PreparedStatement statement, Quest quest) throws SQLException {
        statement.setString(1, quest.getName());
        statement.setString(2, quest.getGenre());
        statement.setString(3, quest.getDescription());
        statement.setString(4, quest.getImage());
        statement.setInt(5, quest.getScore());
        statement.setString(6, quest.getQuestRoomName());
        statement.setInt(7, quest.getQuestId());
        statement.executeUpdate();
    }

    private List<Quest> formQuests(ResultSet resultSet) throws SQLException {
        List<Quest> quests = new ArrayList<>();
        while (resultSet.next()) {
            Quest quest = new Quest();
            quest.setQuestId(resultSet.getInt(Constants.QUEST_ID));
            quest.setName(resultSet.getString(Constants.QUEST_NAME));
            quest.setDescription(resultSet.getString(Constants.QUEST_DESCRIPTION));
            quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));
            quest.setImage(resultSet.getString(Constants.QUEST_IMAGE));
            quest.setScore(resultSet.getInt(Constants.QUEST_SCORE));
            quest.setQuestRoomName(resultSet.getString(Constants.QUEST_ROOM_NAME));
            quests.add(quest);
        }
        return quests;
    }

    private Quest formQuest(ResultSet resultSet) throws SQLException {
        Quest quest = new Quest();
        while (resultSet.next()) {
            quest.setGenre(resultSet.getString(Constants.QUEST_GENRE));
            quest.setName(resultSet.getString(Constants.QUEST_NAME));
            quest.setDescription(resultSet.getString(Constants.QUEST_DESCRIPTION));
            quest.setImage(resultSet.getString(Constants.QUEST_IMAGE));
            quest.setQuestRoomName(resultSet.getString(Constants.QUEST_ROOM_NAME));
        }
        return quest;
    }

}
