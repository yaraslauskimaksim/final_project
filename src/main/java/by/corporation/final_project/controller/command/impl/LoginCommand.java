package by.corporation.final_project.controller.command.impl;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.UserServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = UserServiceImpl.getUserService().getUser(email, password);
            if (user!=null){
                request.setAttribute("success", "success");
                request.getSession().setAttribute("userId", user.getId());
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", user.getRole());
                path.append(identifyRole(user.getRole(), request));
            }else {
                request.setAttribute("errorInLogin", Constants.INVALID_LOGIN_MESSAGE);
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_LOGIN));
            }
        } catch (ServiceException e) {
            LOGGER.error("ServiceException");
        }

        response.sendRedirect(path.toString());
        path.setLength(0);
    }

    private String identifyRole(Role role, HttpServletRequest request) {
        String path = null;
        switch (role){
            case CLIENT:
                path = request.getContextPath() + BundleResourceManager.getConfigProperty(Constants.PATH_HOME);
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



}



