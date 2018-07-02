package by.corporation.final_project.controller;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.CommandFactory;
import by.corporation.final_project.controller.command.impl.user.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
        Command command = CommandFactory.getCommandFactory().defineCommand(request);

        try {
            command.execute(request, response);
        } catch (ControllerException e) {
            e.printStackTrace();
        }

    }
}