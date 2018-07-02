package by.corporation.final_project.dao.mysql;

import by.corporation.final_project.entity.User;


public interface UserDAO {
    User getUser(String email, String password) throws DaoException;
    int registerUser(User user) throws DaoException;
    boolean isEmailExists(User user);
}

