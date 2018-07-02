package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.UserServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException {
        User user = getUser(request);

        try {
            int userID = UserServiceImpl.getUserService().save(user);
            if (userID != 0) {
                request.getSession().setAttribute("userId", userID);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", Role.CLIENT);
                response.sendRedirect(BundleResourceManager.getConfigProperty("path.page.index"));
            } else {
                request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
                response.sendRedirect(BundleResourceManager.getConfigProperty("path.page.error"));
            }

        } catch (ServiceException e) {
            request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.register")).forward(request, response);
            throw new ControllerException("Exception occurs during saving new user on Controller layer", e);
        }

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