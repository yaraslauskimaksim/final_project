package final_project.controller.command.user;

import final_project.controller.command.Command;
import final_project.controller.command.util.ConfigurationManager;
import final_project.dao.db.SqlDaoException;
import final_project.dao.db.impl.QuestDAOImpl;
import final_project.entity.Quest;
import final_project.service.QuestService;
import final_project.service.exception.UserAlreadyExistException;
import final_project.service.impl.QuestServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SingleQuestCommand implements Command {
    QuestService questService = new QuestServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {

        HttpSession session = request.getSession();
        int questId = Integer.parseInt(request.getParameter("questId").trim());
        Quest singleQuest = null;
        try {
            singleQuest = questService.getSingleQuest(questId);
            if (singleQuest != null) {
                request.setAttribute("quest", singleQuest);
                request.setAttribute("questId", questId);
                RequestDispatcher dispatcher = request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.singleQuest"));
                dispatcher.forward(request, response);
            }
        } catch (SqlDaoException e) {
            response.sendRedirect(ConfigurationManager.getProperty("path.page.quest"));
            throw new ControllerException("f", e);
        }
//
//          UserDetails user = (UserDetails) session.getAttribute("user");
//          Role role = (Role) session.getAttribute("role");

    }
}
