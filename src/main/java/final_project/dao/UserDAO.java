package final_project.dao;

import final_project.dao.db.SqlDaoException;
import final_project.entity.Role;
import final_project.entity.User;
import final_project.entity.UserDetails;


public interface UserDAO {
    UserDetails readByPasswordEmailRole(String email, String password) throws SqlDaoException;
    UserDetails readByPasswordEmail(String email, String password) throws SqlDaoException;
    void registerUser(UserDetails userDetails, User user) throws SqlDaoException;
}

