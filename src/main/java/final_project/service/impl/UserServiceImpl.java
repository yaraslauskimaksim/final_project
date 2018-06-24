package final_project.service.impl;

import final_project.dao.DAOFactory;
import final_project.dao.UserDAO;
import final_project.dao.db.SqlDaoException;
import final_project.entity.Role;
import final_project.entity.User;
import final_project.entity.UserDetails;
import final_project.service.UserService;
import final_project.service.exception.ServiceException;
import final_project.service.validation.PasswordHash;

public class UserServiceImpl implements UserService{

    private static UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    public  boolean validate(String email, String password) throws ServiceException {
        UserDetails userDetails = null;
        try {
            userDetails = userDAO.readByPasswordEmail(email, password);
        } catch (SqlDaoException e) {
            throw new ServiceException("Exception occurs during reading password and email on service layer", e);
        }
        return userDetails!=null;
    }

    public void save(User user, UserDetails userDetails) throws ServiceException {
        try {
            userDAO.registerUser(userDetails, user);
        } catch (SqlDaoException e) {
            throw new ServiceException("Exception occurs during saving a new user on service layer", e);
        }
    }

    public UserDetails authenticate(String email, String password) throws ServiceException {
        password = PasswordHash.hashPassword(password);
        UserDetails userDetails = null;
        try {
            userDetails = userDAO.readByPasswordEmailRole(email, password);
            if (userDetails == null) {
                return null;
            }
            Role role = userDetails.getRole();
            Integer id = userDetails.getId();
            UserDetails user = new UserDetails(email, password, role, id);
            if (validate(email, password)) {
                return user;
            }
        } catch (SqlDaoException e) {
            throw new ServiceException("", e);
        }
        return null;
    }
}

