package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.dao.mysql.DaoException;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.entity.Role;
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


public class ShowSingleQuestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowSingleQuestCommand.class);

    private StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute(Constants.ROLE);
        int questId = Integer.parseInt(request.getParameter(Constants.QUEST_ID).trim());

        if (role.equals(Role.QUEST_OWNER)) {
            Quest singleQuest = null;
            try {
                singleQuest = QuestServiceImpl.getQuestService().getSingleQuest(questId);
                if (singleQuest != null) {
                    request.getSession().setAttribute(Constants.QUEST, singleQuest);
                    request.getSession().setAttribute(Constants.QUEST_ID, questId);
                    path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SINGLE_QUEST_OWNER));
                }
            } catch (DaoException e) {
                LOGGER.error("Exception during showing a single quest", e);
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_ERROR));
            }
        }
        request.getRequestDispatcher(path.toString()).forward(request, response);
        path.setLength(0);
    }
}

