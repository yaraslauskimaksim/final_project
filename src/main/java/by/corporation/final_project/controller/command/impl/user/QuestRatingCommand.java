package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.service.impl.QuestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuestRatingCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException {

        HttpSession session = request.getSession();
        //session
        Integer questId = (Integer) session.getAttribute("questId");
        //request
        Integer score = Integer.parseInt(request.getParameter("score").trim());
        int newScore = QuestServiceImpl.getQuestService().getScore(questId, score);
        QuestServiceImpl.getQuestService().setScore(newScore, questId);
        request.getSession().setAttribute("questId", questId);
        response.sendRedirect("frontController?command=singleQuest&questId="+questId);

    }
}
