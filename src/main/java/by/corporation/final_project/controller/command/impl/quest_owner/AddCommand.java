package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.QuestServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;
import by.corporation.final_project.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddCommand.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Quest quest = getQuest(request, session);
        try {
            int questId= QuestServiceImpl.getQuestService().addQuest(quest);
            if (questId!=0) {
                request.getSession().setAttribute(Constants.QUEST_ID, questId);
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));
            } else {
                request.getSession().setAttribute("error", "Such quest already exists");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_OWNER));
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception during adding new quest", e);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_ERROR));
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
        return quest;
    }
}

