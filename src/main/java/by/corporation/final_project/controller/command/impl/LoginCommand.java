package by.corporation.final_project.controller.command.impl;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.UserServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = formUser(email, password);
        try {
            user = UserServiceImpl.getUserService().getUser(email, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user!=null){
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("role", user.getRole());
            response.sendRedirect(identifyRole(user.getRole(), request));
        }
        else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            response.sendRedirect(BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
        }

    }

    public String identifyRole(Role role, HttpServletRequest request) {
        String path = null;
        switch (role){
            case CLIENT:
                path = request.getContextPath() + "/index.jsp";
                return path;
            case QUEST_OWNER:
                path = request.getContextPath() + BundleResourceManager.getConfigProperty(Constants.PATH_OWNER);
                return path;
            case ADMINISTRATOR:
                path = BundleResourceManager.getConfigProperty("path.page.admin");
                return path;
        }
        return null;
    }


    private User formUser(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}



