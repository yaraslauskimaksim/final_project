package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.controller.command.util.Constants;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.entity.Message;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.MessageServiceImpl;
import by.corporation.final_project.util.BundleResourceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUserMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowUserComment.class);
    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ControllerException {

        int page = ControllerUtil.getCurrentPage(request);

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        if (role.equals(Role.ADMINISTRATOR)) {
            List<Message> messages = null;
            try {
                messages = MessageServiceImpl.getContactFormService().getAllMessages(page);
                int messageSize = MessageServiceImpl.getContactFormService().getMessageQuantity();
                int numberOfPages = ControllerUtil.getNumberOfPage(messageSize, Constants.ITEMS_PER_PAGE);

                request.setAttribute("messages", messages);
                request.setAttribute("page", page);
                request.setAttribute("numberOfPages", numberOfPages);

                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_MESSAGE));
            } catch (ServiceException e) {
                request.setAttribute("emptyMessages", "error");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_SHOW_USER_MESSAGE));
            }
            request.getRequestDispatcher(path.toString()).forward(request, response);
            path.setLength(0);
        }
    }
}
