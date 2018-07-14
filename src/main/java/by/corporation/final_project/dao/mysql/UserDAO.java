package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.entity.Status;
import by.corporation.final_project.entity.User;

import java.util.List;


public interface UserDAO {
    User getUser(String email, String password) throws DaoException;
    int registerUser(User user) throws DaoException;
    boolean isEmailExists(User user);
    void makeUserActive(int userId) throws DaoException;
    void frozeUser(int userId) throws DaoException;
    Status getStatus(int userId) throws DaoException;
    List<User> getAllUsersWithStatus(Status status, int currentPage, int usersPerPage) throws DaoException;
    int getUserQuantatyByStatus(Status status) throws DaoException;

}

