package final_project.controller.command.administrator;

import final_project.controller.command.Command;
import final_project.controller.command.user.ControllerException;
import final_project.controller.command.util.ConfigurationManager;
import final_project.dao.AdministratorDAO;
import final_project.dao.db.SqlDaoException;
import final_project.dao.db.impl.AdministratorDAOImpl;
import final_project.entity.Comment;
import final_project.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUserComment implements Command {
    AdministratorDAO administratorDAO = new AdministratorDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        if(role.equals(Role.ADMINISTRATOR)){
            List<Comment> comments = null;
            try {
                comments = administratorDAO.getAllComment();
                request.setAttribute("comment", comments);
                request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.userComment")).forward(request, response);
            } catch (SqlDaoException e) {
                throw new ControllerException("hh", e);
            }

        } else {
            response.sendRedirect(ConfigurationManager.getProperty("path.page.admin"));
        }

    }
}
