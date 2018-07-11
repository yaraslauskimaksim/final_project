package by.corporation.final_project.controller.command.impl.administrator;

import by.corporation.final_project.controller.command.Command;
import by.corporation.final_project.controller.command.impl.user.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrozeUserCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ControllerException, IOException, ControllerException {

    }
}
