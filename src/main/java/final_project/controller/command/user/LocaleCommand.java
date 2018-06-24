package final_project.controller.command.user;

import final_project.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {

    public static final String ADDRESS = "adr";
    public static final String SESSION_SCOPE = "lang";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_SCOPE, request.getParameter(SESSION_SCOPE));
        request.getRequestDispatcher(request.getParameter(ADDRESS)).forward(request, response);
    }
}
