package final_project.controller.command;

import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.Role;
import final_project.entity.UserDetails;
import final_project.service.exception.ServiceException;
import final_project.service.exception.UserAlreadyExistException;
import final_project.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginCommand implements Command {

    UserServiceImpl userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDetails user = new UserDetails(email, password);
        try {
            user = userService.authenticate(email,password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user!=null){
            request.getSession().setAttribute("id", user.getId());
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("role", user.getRole());
            response.sendRedirect(identifyRole(user.getRole()));

        }
         else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request, response);
        }

    }

    public String identifyRole(Role role) {
        switch (role){
            case CLIENT:
               String  path = ConfigurationManager.getProperty("path.page.index");
               return path;
            case QUEST_OWNER:
                String  pathR = ConfigurationManager.getProperty("path.page.register");
                return  pathR;
            case ADMINISTRATOR:
                String  pathRR =ConfigurationManager.getProperty("path.page.admin");
                return pathRR;
        }
        return null;
    }
}



