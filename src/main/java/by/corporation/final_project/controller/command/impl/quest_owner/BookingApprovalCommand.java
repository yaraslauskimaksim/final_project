package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.BookingServiceImpl;
import by.corporation.final_project.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BookingApprovalCommand implements Command {
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException, ControllerException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        int bookingId = Integer.parseInt(request.getParameter("id").trim());
        if(role.equals(Role.QUEST_OWNER)){
            try {
                BookingServiceImpl.getBookingService().approveStatus(bookingId);
                path.append(request.getContextPath()).append(Constants.PATH_SHOW_USER_BOOKING);
            } catch (ServiceException e) {
                request.setAttribute("error", "error");
                path.append(request.getContextPath()).append(Constants.PATH_SHOW_USER_BOOKING);
            }
        }

        response.sendRedirect(path.toString());
        path.setLength(0);
    }
}
