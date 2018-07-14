package by.corporation.final_project.dao.mysql.impl;

import by.corporation.final_project.dao.mysql.QuestDAO;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.dao.pool.ConnectionPool;
import by.corporation.final_project.dao.pool.ConnectionPoolException;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.util.Constants;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOImpl implements QuestDAO {

    Connection connection = null;

    public QuestDAOImpl(ConnectionPool connectionPool) {
        connection = connectionPool.getConnection();
    }

    private static final String SELECT_ALL_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image FROM quest LIMIT ? OFFSET ?";
    private static final String SELECT_SINGLE_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name FROM quest where que_id=?";
    private static final String SELECT_QUEST_SCORE = "SELECT que_id, que_score from quest WHERE que_id = ?";
    private static final String UPDATE_SCORE = "UPDATE quest SET que_score = ? WHERE que_id = ?";
    private static final String DELETE_QUEST = "DELETE FROM quest WHERE que_id = ?";
    private static final String UPDATE_QUEST = "UPDATE quest SET que_genre = ?, que_name = ?, que_description = ?, que_image = ?, que_room_name = ?, que_user_id = ? where que_id = ?";
    private static final String ADD_NEW_QUEST = "insert into quest(que_genre, que_name, que_description, que_image,que_user_id, que_room_name) values (?,?,?,?,?,?)";
    private static final String IS_QUEST_EXISTS = "SELECT EXISTS(SELECT 1 FROM quest WHERE que_name = ? )";
    private static final String SELECT_QUESTS_BY_ROOM_NAME = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id FROM quest  WHERE que_room_name = ?  LIMIT ? OFFSET ?";
    private static final String SELECT_QUEST_ROOM_NAME = "SELECT que_room_name FROM quest where que_user_id =?";
    private static final String GET_ALL_QUEST_QUANTITY = "SELECT COUNT(*) FROM quest";
    private static final String GET_ALL_QUEST_QUANTITY_BY_ROOM_NAME = "SELECT COUNT(*) FROM quest where que_room_name=?";
    private static final String GET_ALL_QUESTS_BY_RATING = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id FROM quest ORDER BY que_score DESC LIMIT ? OFFSET ?";
    private static final String SEARCH_QUEST = "SELECT que_id, que_genre, que_name, que_description, que_image, que_room_name, que_user_id FROM quest WHERE que_name LIKE ? LIMIT ? OFFSET ?";
    private static final String GET_ALL_QUEST_QUANTITY_BY_QUEST_NAME = "SELECT COUNT(*) FROM quest where que_name=?";

    @Override
    public int getQuestQuantityByQuestRoom(String questRoomName) throws  DaoException {
        int counter = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUEST_QUANTITY_BY_ROOM_NAME);) {
            preparedStatement.setString(1, questRoomName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    counter =  resultSet.getInt(1);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return counter;
    }

    @Override
    public int getQuestQuantityByQuestName(String questName) throws DaoException {
        int counter = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUEST_QUANTITY_BY_QUEST_NAME)) {
            preparedStatement.setString(1, questName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    counter =  resultSet.getInt(1);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return counter;
    }


    @Override
    public int getQuestQuantity() throws ConnectionPoolException {
        int counter = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_QUEST_QUANTITY)) {
            while (resultSet.next()) {
                counter =  resultSet.getInt(1);

            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception occurs during retrieving data of all quests", e);
        }
        return counter;
    }

    @Override
    public List<Quest> getAllQuests(int questPerPage, int currentPage) throws ConnectionPoolException {
        List<Quest> quests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUEST)) {
            statement.setInt(1, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            statement.setInt(2, startIndex);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setQuestId(resultSet.getInt(Constants.QUE_ID));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    quest.setDescription(resultSet.getString(Constants.QUE_DESCRIPTION));
                    quest.setGenre(resultSet.getString(Constants.QUE_GENRE));
                    quest.setImage(resultSet.getString(Constants.QUE_IMAGE));
                    quests.add(quest);
                }
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception occurs during retrieving data of all quests", e);
        }
        return quests;
    }
    @Override
    public List<Quest> searchQuests(String  name, int questPerPage, int currentPage) throws ConnectionPoolException {
        List<Quest> quests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SEARCH_QUEST)) {
            statement.setString(1,  name + "%");
            statement.setInt(2, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            statement.setInt(3, startIndex);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setQuestId(resultSet.getInt(Constants.QUE_ID));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    quest.setDescription(resultSet.getString(Constants.QUE_DESCRIPTION));
                    quest.setGenre(resultSet.getString(Constants.QUE_GENRE));
                    quest.setImage(resultSet.getString(Constants.QUE_IMAGE));
                    quests.add(quest);
                }
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception occurs during retrieving data of all quests", e);
        }
        return quests;
    }
    @Override
    public List<Quest> getAllQuestByRating(int questPerPage, int currentPage) throws DaoException {
        List<Quest> quests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_QUESTS_BY_RATING)) {
            statement.setInt(1, questPerPage);
            int startIndex = (currentPage - 1) * questPerPage;
            statement.setInt(2, startIndex);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setQuestId(resultSet.getInt(Constants.QUE_ID));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    quest.setDescription(resultSet.getString(Constants.QUE_DESCRIPTION));
                    quest.setGenre(resultSet.getString(Constants.QUE_GENRE));
                    quest.setImage(resultSet.getString(Constants.QUE_IMAGE));
                    quests.add(quest);
                }
            }
        } catch (SQLException  e) {
            throw new DaoException("Exception occurs during retrieving data of all quests", e);
        }
        return quests;
    }


    @Override
    public Quest getSingleQuest(int questId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SINGLE_QUEST);) {
            preparedStatement.setInt(1, questId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setGenre(resultSet.getString(Constants.QUE_GENRE));
                    quest.setName(resultSet.getString(Constants.QUE_NAME));
                    quest.setDescription(resultSet.getString(Constants.QUE_DESCRIPTION));
                    quest.setImage(resultSet.getString(Constants.QUE_IMAGE));
                    quest.setQuestRoomName(resultSet.getString("que_room_name"));
                    return quest;
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return null;
    }


    @Override
    public int getScoreQuest(int questId) throws DaoException {
        int score = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUEST_SCORE);) {
            preparedStatement.setInt(1, questId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Quest quest = new Quest();
                    quest.setScore(resultSet.getInt(Constants.QUE_SCORE));
                    score = quest.getScore();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return score;
    }

    @Override
    public void rateQuest(int score, int questId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCORE);) {
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, questId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void deleteQuest(int questId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUEST);) {
            preparedStatement.setInt(1, questId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to be approved", e);
        }
    }

    @Override
    public void updateQuest(Quest quest) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUEST);) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, quest.getGenre());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setString(4, quest.getImage());
            preparedStatement.setString(5, quest.getQuestRoomName());
            preparedStatement.setInt(6, quest.getUserId());
            preparedStatement.setInt(7, quest.getQuestId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException("Exception during rollback transaction of replacing product.", e1);
            }
        }
    }

    public Integer addQuest(Quest quest) throws DaoException {
        Integer idQuest = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_QUEST, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, quest.getGenre());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setString(4, quest.getImage());
            preparedStatement.setInt(5, quest.getUserId());
            preparedStatement.setString(6, quest.getQuestRoomName());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                resultSet.next();
                idQuest = resultSet.getInt(1);
            }
        } catch (SQLException e) {
                throw new DaoException("Exception during rollback transaction of replacing product.", e);

        }
        return idQuest;
    }

    @Override
    public boolean isQuestExists(Quest quest) {
        boolean exists = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(IS_QUEST_EXISTS);) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUESTS_BY_ROOM_NAME);) {
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
        String name = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUEST_ROOM_NAME);) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    name = resultSet.getString("que_room_name");

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving data about one single quest", e);
        }
        return name;
    }


}
