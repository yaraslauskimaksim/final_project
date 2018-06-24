package final_project.controller.command.user;

import final_project.controller.command.Command;
import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.Quest;
import final_project.service.exception.ServiceException;
import final_project.service.impl.QuestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QuestCommand implements Command {

    QuestServiceImpl questService = new QuestServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        List<Quest> quest = null;
        try {
            quest = questService.showAllQuests();
        } catch (ServiceException e) {
             throw new ControllerException("Exception occuts during gerring all quests from service", e);
        }
        request.setAttribute("quest", quest);
        request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.quest")).forward(request, response);


    }
}