package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.UserAlreadyExistException;
import by.corporation.quest_fire.service.exception.ValidationException;

import java.util.List;


public interface UserService {
    int register(User user) throws ServiceException, ValidationException, UserAlreadyExistException;
    User fetchUser(User user) throws ServiceException, ValidationException;
    void makeUserActive(int userId) throws ServiceException;
    void frozeUser(int userId) throws ServiceException;
    List<User> getUsersByStatus(Status status, int currentPage);
    int getUserQuantatyByStatus(Status status) throws ServiceException;

}
