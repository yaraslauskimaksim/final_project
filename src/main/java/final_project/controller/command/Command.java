package final_project.controller.command;

import final_project.controller.command.user.ControllerException;
import final_project.service.exception.UserAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException;
}
