package by.corporation.final_project.service;

import by.corporation.final_project.entity.Status;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.exception.ValidationException;

import java.util.List;


public interface UserService {
    int save(User user) throws ServiceException, ValidationException;
    User getUser(String email, String password) throws ServiceException;
    boolean isUserExists(User user);
    void makeUserActive(int userId) throws ServiceException;
    void frozeUser(int userId) throws ServiceException;
    List<User> getUsersByStatus(Status status, int currentPage);
    int getUserQuantatyByStatus(Status status) throws ServiceException;

}
