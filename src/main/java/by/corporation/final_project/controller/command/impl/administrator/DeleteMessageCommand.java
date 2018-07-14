package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.controller.command.util.Constants;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.MessageServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteMessageCommand implements Command {

    StringBuilder path = new StringBuilder();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException, ControllerException {
        int page = 1;

        Integer messageId = Integer.parseInt(request.getParameter(Constants.MESSAGE_ID).trim());

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        if (role.equals(Role.ADMINISTRATOR)) {
            try {
                MessageServiceImpl.getContactFormService().deleteMessage(messageId);
                path.append(request.getContextPath()).append(Constants.PATH_SHOW_USER_MESSAGES).append(page);
            } catch (ServiceException e) {
                request.setAttribute("error", "error");
                path.append(request.getContextPath()).append(Constants.PATH_SHOW_USER_MESSAGES).append(page);
            }
        }

        response.sendRedirect(path.toString());
        path.setLength(0);
    }
}

