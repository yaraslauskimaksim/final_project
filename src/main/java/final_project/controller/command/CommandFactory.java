package final_project.controller.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");

        // receive object corresponded to the command
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }
        return current;
    }
}

