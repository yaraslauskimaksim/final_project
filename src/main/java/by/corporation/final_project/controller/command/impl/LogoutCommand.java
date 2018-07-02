package by.corporation.final_project.controller.command.impl;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.util.BundleResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(BundleResourceManager.getConfigProperty("path.page.index"));

    }
}
