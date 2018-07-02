package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.QuestServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowAllQuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowAllQuestCommand.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws  IOException, ServletException {
        int page = 0;

        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute(Constants.ROLE);
        Integer userId = (Integer) session.getAttribute(Constants.USER_ID);

        if (role.equals(Role.QUEST_OWNER)) {
            String questRoomName = QuestServiceImpl.getQuestService().getQuestRoomName(userId);
            int noOfRecords = QuestServiceImpl.getQuestService().getQuestQuantityByQuestRoom(questRoomName);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.QUESTS_PER_PAGE_DEFAULT_VALUE);
            List<Quest> quests = null;
            try {
                quests = QuestServiceImpl.getQuestService().showAllQuestsOfQuestRoom(questRoomName, page);
                request.setAttribute(Constants.QUESTS, quests);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", page);
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_QUESTS_BY_ROOM_NAME));

            } catch (ServiceException e) {
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));
            }
            request.getRequestDispatcher(path.toString()).forward(request, response);
            path.setLength(0);
        }
    }
}

