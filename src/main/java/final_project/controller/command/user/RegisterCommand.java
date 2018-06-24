package final_project.controller.command.user;

import final_project.controller.command.Command;
import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.User;
import final_project.entity.UserDetails;
import final_project.service.exception.ServiceException;
import final_project.service.validation.PasswordHash;
import final_project.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterCommand implements Command {


    UserServiceImpl userServiceImpl = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException {
        User user = getUser(request);
        UserDetails userDetails = getUserDetails(request);

//        if (!Validator.validateEmail(email)) {
//            System.err.println("no");
//        }

        try {
            if(!userServiceImpl.validate(userDetails.getEmail(), userDetails.getPassword())){
                try {
                    userServiceImpl.save(user, userDetails);
                } catch (ServiceException e) {
                   throw new ControllerException("Exception occurs during saving new user on Controller layer", e);
                }
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("user", userDetails);
                response.sendRedirect(ConfigurationManager.getProperty("path.page.index"));
            }
            else {
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.register")).forward(request, response); // Go back to login page.
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    private User getUser(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        return user;
    }

    private UserDetails getUserDetails(HttpServletRequest request) {
        UserDetails userDetails = new UserDetails();
        String passwordHashed = PasswordHash.hashPassword(request.getParameter("password"));
        userDetails.setPassword(passwordHashed);
        userDetails.setEmail(request.getParameter("email"));
        return userDetails;
    }
    }
