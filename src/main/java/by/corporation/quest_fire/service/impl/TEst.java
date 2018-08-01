package by.corporation.quest_fire.service.impl;


import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.dao.util.Constants;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TEst {
    private static final String UPDATE_COMMENT = "UPDATE comment SET com_quest_id = ?, com_user_id = ?, com_description = ?, com_status = ? WHERE com_user_id = ?";

    private static final String SELECT_ALL_COMMENTS_BY_USER_ID = "SELECT com_id, com_quest_id, com_user_id, com_description, com_status from comment WHERE com_user_id = ?";


    public static void main(String [] args) throws DaoException {


        update();
       List<Comment> comment =  fetchAllByUserId(45);
        for (Comment s:comment){
            System.out.println(s);
        }

    }

    public static void update() throws DaoException {
        Connection connection = null;
        PreparedStatement s = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1/quest_fire1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Minsk",
                    "root", "root123");

            s = connection.prepareStatement(UPDATE_COMMENT);

                s.setInt(1, 4);
                s.setInt(2, 45);
                s.setString(3,"cddg");
                s.setString(4, "pending");
                s.setInt(5, 1);
                s.executeUpdate();

            s.setInt(1, 4);
            s.setInt(2, 45);
            s.setString(3,"c213253ddg");
            s.setString(4, "pending");
            s.setInt(5, 2);
            s.executeUpdate();




        } catch (SQLException e) {
            throw new DaoException("Exception occurs during set status to frozen", e);
        } finally {
            try {

              s.close();
                connection.close();
            } catch (SQLException e) {

            }
        }
    }

    public static List<Comment> fetchAllByUserId(int userId) throws DaoException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                   "jdbc:mysql://127.0.0.1/quest_fire1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Minsk",
                    "root", "root123");
            statement = connection.prepareStatement(SELECT_ALL_COMMENTS_BY_USER_ID);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            List<Comment> comments = formCommentByUserId(resultSet);
            return comments;
        } catch (SQLException e) {
            throw new DaoException("Exception occurs during retrieving all comments by quest id", e);
        } finally {
            try {
                resultSet.close();
               statement.close();

               connection.close();
            } catch (SQLException e) {

            }
        }
    }

    private static List<Comment> formCommentByUserId(ResultSet resultSet) throws SQLException {
       List<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setUserId(resultSet.getInt(Constants.COMMENT_USER_ID));
            comment.setQuestId(resultSet.getInt(Constants.COMMENT_QUEST_ID));
            comment.setCommentId(resultSet.getInt(Constants.COMMENT_ID));
            comment.setStatus(Status.valueOf(resultSet.getString(Constants.COMMENT_STATUS).toUpperCase()));
            comment.setDescription(resultSet.getString(Constants.COMMENT_DESCRIPTION));
            comments.add(comment);
        }
        return comments;
    }
}
