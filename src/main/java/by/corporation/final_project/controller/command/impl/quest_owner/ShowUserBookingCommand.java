package by.corporation.final_project.controller.command.impl.quest_owner;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.util.ControllerUtil;
import by.corporation.final_project.entity.Booking;
import by.corporation.final_project.entity.Role;
import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.BookingServiceImpl;
import by.corporation.final_project.service.impl.QuestServiceImpl;
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

public class ShowUserBookingCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowUserBookingCommand.class);
    StringBuilder path = new StringBuilder();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = ControllerUtil.getCurrentPage(request);

        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");

        if (role.equals(Role.QUEST_OWNER)) {
            List<Booking> booking = null;
            try {
                String questRoomName = QuestServiceImpl.getQuestService().getQuestRoomName(userId);
                booking = BookingServiceImpl.getBookingService().getAllBooking(page, questRoomName);
                if(!booking.isEmpty()) {

                    int numberOfRecords = BookingServiceImpl.getBookingService().getBookingQuantityByQuestRoom(questRoomName);
                    int numberOfPages = ControllerUtil.getNumberOfPage(numberOfRecords, Constants.ITEMS_PER_PAGE);

                    request.setAttribute("booking", booking);
                    request.setAttribute("page", page);
                    request.setAttribute("numberOfPages", numberOfPages);
                    path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_BOOKING));
                } else {
                    request.setAttribute("emptyList", "Sorry! But not booking yet!");
                    path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_BOOKING));

                }
            } catch (ServiceException e) {
                request.setAttribute("error", "error");
                path.append(request.getContextPath()).append(BundleResourceManager.getConfigProperty(Constants.PATH_BOOKING));

            }
            request.getRequestDispatcher(path.toString()).forward(request, response);
            path.setLength(0);

        }
    }
}
