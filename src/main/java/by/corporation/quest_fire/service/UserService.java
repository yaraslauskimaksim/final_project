package by.corporation.quest_fire.service;

import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.entity.User;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.exception.UserAlreadyExistException;
import by.corporation.quest_fire.service.exception.ValidationException;

import java.util.List;


public interface UserService {
    long register(User user) throws ServiceException, ValidationException, UserAlreadyExistException;
    User fetchUser(User user) throws ServiceException, ValidationException;
    void makeUserActive(long userId) throws ServiceException;
    void frozeUser(long userId) throws ServiceException;
    List<User> fetchUsersByStatus(Status status, int currentPage) throws ServiceException;
    int fetchUserQuantityByStatus(Status status) throws ServiceException;

}
