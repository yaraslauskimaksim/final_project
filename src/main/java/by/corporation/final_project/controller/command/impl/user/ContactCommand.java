package by.corporation.final_project.controller.command.impl.user;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.util.Constants;
import by.corporation.final_project.entity.Message;
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


public class ContactCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ContactCommand.class);
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException, ControllerException {
       HttpSession session = request.getSession();

        Message message = form(request, session);

        try {
            int id = MessageServiceImpl.getContactFormService().saveMessage(message);
            request.getSession().setAttribute("success", Constants.MESSAGE_WAS_SENT);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_FROZEN_USER));
        } catch (ServiceException e) {
            LOGGER.error("message was not saved");
            request.getSession().setAttribute("error", Constants.MESSAGE_WAS_NOT_SENT);
            path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_FROZEN_USER));
        }
        response.sendRedirect(path.toString());
        path.setLength(0);
    }

    private Message form(HttpServletRequest request, HttpSession session){

        Integer userId = (Integer) session.getAttribute("userId");
        String message = request.getParameter("message");

        Message contactForm = new Message();
        contactForm.setUserId(userId);
        contactForm.setMessage(message);

        return contactForm;

    }
}
