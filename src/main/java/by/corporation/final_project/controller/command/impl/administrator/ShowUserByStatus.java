package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.entity.Status;
import by.corporation.final_project.entity.User;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;
import by.corporation.final_project.service.impl.UserServiceImpl;
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

public class ShowUserByStatus implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowUserByStatus.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {

        int page = ControllerUtil.getCurrentPage(request);

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        Status status =  Status.valueOf(request.getParameter("status").toUpperCase().trim());

        if (role.equals(Role.ADMINISTRATOR)) {
            List<User> userList = null;
            try {
                userList = UserServiceImpl.getUserService().getUsersByStatus(status, page);
                int numberOfRecords = UserServiceImpl.getUserService().getUserQuantatyByStatus(status);
                int numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);

                request.setAttribute("status", String.valueOf(status).toLowerCase());
                request.setAttribute("users", userList);
                request.setAttribute("page", page);
                request.setAttribute("numberOfPages", numberOfPages);

                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_USER_STATUS));
            } catch (ServiceException e) {
                request.setAttribute("error", "error");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_ADMIN));
            }


            request.getRequestDispatcher(path.toString()).forward(request, response);
            path.setLength(0);

        }
    }
}
