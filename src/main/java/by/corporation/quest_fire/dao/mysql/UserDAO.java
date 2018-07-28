package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Comment;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;

import java.util.List;


public interface UserDAO {
    User fetchUser(String email, String password) throws DaoException;

    int registerUser(User user) throws DaoException;

    int fetchUserId(User user) throws DaoException;


    List<User> getAllUsersWithStatus(Status status, int currentPage, int usersPerPage) throws DaoException;

    int getUserQuantatyByStatus(Status status) throws DaoException;

    void update(User user, List<Comment> comments) throws DaoException;

    User fetchById(int userId) throws DaoException;

}

