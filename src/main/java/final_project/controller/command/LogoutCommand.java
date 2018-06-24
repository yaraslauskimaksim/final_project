package final_project.controller.command;

import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        session.removeAttribute("id");
        session.removeAttribute("user");
        session.removeAttribute("role");
        session.invalidate();
        response.sendRedirect(ConfigurationManager.getProperty("path.page.index"));

    }
}
