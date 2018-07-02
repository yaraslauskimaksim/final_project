package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.impl.CommentServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUserComment implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        if (role.equals(Role.ADMINISTRATOR)) {
            List<Comment> comments = CommentServiceImpl.getCommentService().showAllComments();
            request.setAttribute("comment", comments);
            request.getRequestDispatcher(BundleResourceManager.getConfigProperty("path.page.userComment")).forward(request, response);
        } else {
            response.sendRedirect(BundleResourceManager.getConfigProperty("path.page.admin"));
        }
    }
}
