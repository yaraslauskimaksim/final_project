package final_project.service;

import final_project.entity.User;
import final_project.entity.UserDetails;
import final_project.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService {
    void save(User user, UserDetails userDetails) throws SQLException, ServiceException;
}
