package by.corporation.quest_fire.dao.mysql;

import by.corporation.quest_fire.dao.exception.DaoException;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;

import java.util.List;


public interface UserDAO {
    User fetchUser(String email, String password) throws DaoException;
    int registerUser(User user) throws DaoException;
    int fetchUserId(User user) throws DaoException;
    void makeUserActive(int userId) throws DaoException;
    void frozeUser(int userId) throws DaoException;
    Status getStatus(int userId) throws DaoException;
    List<User> getAllUsersWithStatus(Status status, int currentPage, int usersPerPage) throws DaoException;
    int getUserQuantatyByStatus(Status status) throws DaoException;

}

