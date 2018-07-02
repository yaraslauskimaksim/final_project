package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.impl.QuestServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteCommand.class);
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, IOException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute(Constants.ROLE);
        //Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
        int questId = Integer.parseInt(request.getParameter(Constants.QUEST_ID).trim());

        if (role.equals(Role.QUEST_OWNER)) {
            QuestServiceImpl.getQuestService().deleteQuest(questId);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_ALL_QUESTS_BY_ROOM_NAME));
        } else {
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));
        }
        response.sendRedirect(path.toString());
        path.setLength(0);
    }
}
