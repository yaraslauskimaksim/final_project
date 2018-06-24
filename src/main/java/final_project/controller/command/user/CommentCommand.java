package final_project.controller.command.user;

import final_project.controller.command.Command;
import final_project.controller.command.util.ConfigurationManager;
import final_project.entity.Comment;
import final_project.service.CommentService;
import final_project.service.exception.CommentSavingException;
import final_project.service.exception.ServiceException;
import final_project.service.impl.CommentServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommentCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        CommentService commentService = new CommentServiceImpl();

        HttpSession session = request.getSession();
        //session
        Integer idUser = (Integer) session.getAttribute("id");
        //request
        Integer questId = (Integer) session.getAttribute("questId");
        String description = request.getParameter("description");
        Comment comment = new Comment(idUser, questId, description, false);
        try {
            commentService.saveComment(comment);
            response.sendRedirect(ConfigurationManager.getProperty("path.page.index"));
        } catch (ServiceException | CommentSavingException e) {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.login")).forward(request, response);
            throw new ControllerException("Exceiption hoccurs during saving commnet in controller layer", e);
        }

        }
}





