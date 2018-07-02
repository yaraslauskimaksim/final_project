package by.corporation.final_project.controller.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final CommandFactory COMMAND_FACTORY = new CommandFactory();

    public static CommandFactory getCommandFactory() {
        return COMMAND_FACTORY;
    }

    private CommandFactory(){};

    public Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");

        // receive object corresponded to the command
        try {
            current = CommandType.getCommands(action);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return current;
    }
}

