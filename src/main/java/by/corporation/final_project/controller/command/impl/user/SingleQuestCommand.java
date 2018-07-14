package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.dao.exception.DaoException;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Quest;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;
import by.corporation.final_project.service.impl.QuestServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SingleQuestCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        int page = ControllerUtil.getCurrentPage(request);
        HttpSession session = request.getSession();
        int questId = Integer.parseInt(request.getParameter("questId").trim());
        Quest singleQuest = null;
        try {
            singleQuest = QuestServiceImpl.getQuestService().getSingleQuest(questId);
            List<Comment> comments = CommentServiceImpl.getCommentService().showAllCommentsByQuestId(questId);
            if (singleQuest != null) {
                request.getSession().setAttribute("quest", singleQuest);
                request.getSession().setAttribute("questId", questId);
                request.getSession().setAttribute("comment", comments);
                RequestDispatcher dispatcher = request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.singleQuest"));
                dispatcher.forward(request, response);
            }
        } catch (DaoException e) {
            response.sendRedirect(BundleResourceManager.getConfigProperty("path.page.quest"));
            throw new ControllerException("f", e);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
//
//          UserDetails user = (UserDetails) session.getAttribute("user");
//          Role role = (Role) session.getAttribute("role");

    }
}
