package by.corporation.quest_fire.controller;

import by.corporation.quest_fire.controller.command.Command;
import by.corporation.quest_fire.controller.command.CommandFactory;
import by.corporation.quest_fire.controller.command.CommandResult;
import by.corporation.quest_fire.controller.command.RequestContent;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.REDIRECT;
import static by.corporation.quest_fire.controller.command.CommandResult.RoutingType.FORWARD;

public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 6696676044088262856L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //analyzing command which was returned from JSP
        RequestContent requestContent = new RequestContent(request);
        Command command =  CommandFactory.getCommandFactory().defineCommand(request);
        CommandResult commandResult = command.execute(requestContent);
        commandResult.updateRequest(request);


        if (REDIRECT.equals(commandResult.getRoutingType())) {
            response.sendRedirect(request.getContextPath() + commandResult.getPage());
        } else if (FORWARD.equals(commandResult.getRoutingType())) {
            getServletContext().getRequestDispatcher(commandResult.getPage()).forward(request, response);
        } else {
            response.sendError(commandResult.getErrorCode(), commandResult.getErrorMessage());
        }
    }
}