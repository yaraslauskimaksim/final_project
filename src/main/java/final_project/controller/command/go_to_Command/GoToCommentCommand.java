package final_project.controller.command.go_to_Command;

import final_project.controller.command.Command;
import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.UserDetails;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToCommentCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDetails user = (UserDetails) session.getAttribute("user");
        Integer questId = Integer.parseInt(request.getParameter("questId").trim());

        if(user!=null) {
            request.getSession().setAttribute("questId", questId);
            RequestDispatcher dispatcher = request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.comment"));
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(ConfigurationManager.getProperty("path.page.quest"));
        }

    }
}
