package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.util.ControllerUtil;
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
        int page = ControllerUtil.getCurrentPage(request);
        List<Quest> quest = null;
        try {
            quest = QuestServiceImpl.getQuestService().showAllQuests(page);
            int numberOfRecords = QuestServiceImpl.getQuestService().getQuestQuantity();
            int numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);
            request.setAttribute("quest", quest);
            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("currentPage", page);
            request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.quest")).forward(request, response);

        } catch (ServiceException e) {
            throw new ControllerException("Exception occuts during gerring all quests from service", e);
        }

    }
}