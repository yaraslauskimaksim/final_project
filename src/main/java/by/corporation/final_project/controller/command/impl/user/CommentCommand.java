package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.service.exception.CommentSavingException;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommentCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {

        HttpSession session = request.getSession();
        //session
        Integer idUser = (Integer) session.getAttribute("userId");
        //request
        Integer questId = (Integer) session.getAttribute("questId");
        String description = request.getParameter("description");
        Comment comment = new Comment(idUser, questId, description);
        try {
            CommentServiceImpl.getCommentService().saveComment(comment);
            request.getRequestDispatcher("frontController?command=singleQuest&questId=" + questId).forward(request, response);
        } catch (ServiceException | CommentSavingException e) {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.login")).forward(request, response);
            throw new ControllerException("Exceiption hoccurs during saving commnet in controller layer", e);
        }

    }
}





