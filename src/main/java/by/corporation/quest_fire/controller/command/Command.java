package by.corporation.quest_fire.controller.command;




import javax.servlet.ServletException;
import java.io.IOException;


public interface Command {
   CommandResult execute(RequestContent requestContent) throws ServletException, IOException;
}
