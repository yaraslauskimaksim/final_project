package by.corporation.final_project.controller.command.impl.administrator;


import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommentRejectionCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        int commentId = Integer.parseInt(request.getParameter("id").trim());
        int page = ControllerUtil.getCurrentPage(request);
        if(role.equals(Role.ADMINISTRATOR)){
            try {
                CommentServiceImpl.getCommentService().setStatusToRejectedStatus(commentId);
                response.sendRedirect(request.getContextPath()+ "/frontController?command=show&page="+page);
            } catch (ServiceException e) {
                throw new ControllerException("Exception occurs on controller layer", e);
            }
        }
    }
}