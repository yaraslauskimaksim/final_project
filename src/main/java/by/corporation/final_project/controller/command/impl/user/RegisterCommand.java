package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.exception.ValidationException;
import by.corporation.final_project.service.impl.UserServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException {
        User user = getUser(request);

        try {
            int userID = UserServiceImpl.getUserService().save(user);
            if (userID != 0) {
                request.getSession().setAttribute("userId", userID);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", Role.CLIENT);
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));

            }
        } catch (ServiceException e) {
            LOGGER.error("Error during user registration", e);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
        } catch (ValidationException e) {
            LOGGER.error("Invalid data", e);
            request.getSession().setAttribute("error", "Invalid data");
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
        }

        response.sendRedirect(path.toString());
        path.setLength(0);

    }

    private User getUser(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setRole(Role.CLIENT);
        return user;
    }
}