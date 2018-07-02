package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.QuestServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QuestCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        int page = 0;

        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int noOfRecords = QuestServiceImpl.getQuestService().getQuestQuantity();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.QUESTS_PER_PAGE_DEFAULT_VALUE);


        List<Quest> quest = null;
        try {
            quest = QuestServiceImpl.getQuestService().showAllQuests(page);

        } catch (ServiceException e) {
            throw new ControllerException("Exception occuts during gerring all quests from service", e);
        }
        request.setAttribute("quest", quest);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.quest")).forward(request, response);

    }
}