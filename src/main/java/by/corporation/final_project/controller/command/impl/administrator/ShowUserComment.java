package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.entity.Comment;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;
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

public class ShowUserComment implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowUserComment.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {

        int page = ControllerUtil.getCurrentPage(request);

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        if (role.equals(Role.ADMINISTRATOR)) {
            List<Comment> comments = null;
            try {
                comments = CommentServiceImpl.getCommentService().showAllComments(page);
                int commentSize = CommentServiceImpl.getCommentService().getCommentQuantity();
                int numberOfPages = ControllerUtil.getNumberOfPage(commentSize, Constants.ITEMS_PER_PAGE);

                request.setAttribute("comments", comments);
                request.setAttribute("page", page);
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("commentSize", commentSize);

                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty("path.page.userComment"));
            } catch (ServiceException e) {
                request.setAttribute("error", "error");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty("path.page.admin"));
            }


            request.getRequestDispatcher(path.toString()).forward(request, response);
            path.setLength(0);

        }
    }
}