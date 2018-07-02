package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.entity.Quest;
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

public class EditCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditCommand.class);
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException, IOException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute(Constants.ROLE);
        Quest quest = getQuest(request, session);
        if (role.equals(Role.QUEST_OWNER)) {
            //  QuestServiceImpl.getQuestService().deleteQuest(questId);
            QuestServiceImpl.getQuestService().updateQuest(quest);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SINGLE_QUEST_OWNER));
        } else {
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SINGLE_QUEST_OWNER));
        }
        response.sendRedirect(path.toString());
        path.setLength(0);
    }

    private Quest getQuest(HttpServletRequest request, HttpSession session) {
        Quest quest = new Quest();
        quest.setDescription(request.getParameter(Constants.QUEST_DESCRIPTION));
        quest.setName(request.getParameter(Constants.QUEST_NAME));
        quest.setGenre(request.getParameter(Constants.QUEST_GENRE));
        quest.setImage(request.getParameter(Constants.QUEST_IMAGE));
        quest.setUserId((Integer) session.getAttribute(Constants.USER_ID));
        quest.setQuestRoomName(request.getParameter(Constants.QUEST_ROOM_NAME));
        quest.setQuestId((Integer) session.getAttribute(Constants.QUEST_ID));
        return quest;
    }
}
