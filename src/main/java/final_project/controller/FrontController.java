package final_project.controller;

import final_project.controller.command.Command;
import final_project.controller.command.CommandFactory;
import final_project.controller.command.user.ControllerException;
import final_project.controller.command.util.ConfigurationManager;
import final_project.service.exception.UserAlreadyExistException;

import javax.servlet.RequestDispatcher;
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
        CommandFactory client = new CommandFactory();
        Command command = client.defineCommand(request);

        try {
            command.execute(request, response);
        } catch (ControllerException e) {
            e.printStackTrace();
        }

    }
}