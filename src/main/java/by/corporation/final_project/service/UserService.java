package by.corporation.final_project.service;

import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;


public interface UserService {
    int save(User user) throws ServiceException;
    User getUser(String email, String password) throws ServiceException;
    boolean isUserExists(User user);
}
