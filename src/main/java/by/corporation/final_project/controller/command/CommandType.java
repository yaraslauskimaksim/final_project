package by.corporation.final_project.controller.command;

import by.corporation.final_project.controller.command.impl.LocaleCommand;
import by.corporation.final_project.controller.command.impl.LoginCommand;
import by.corporation.final_project.controller.command.impl.LogoutCommand;
import by.corporation.final_project.controller.command.impl.SearhCommand;
import by.corporation.final_project.controller.command.impl.administrator.CommentApprovalCommand;
import by.corporation.final_project.controller.command.impl.administrator.CommentRejectionCommand;
import by.corporation.final_project.controller.command.impl.administrator.ShowUserComment;
import by.corporation.final_project.controller.command.impl.quest_owner.*;
import by.corporation.final_project.controller.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandType {
    private static Map<String , Command> commands = new HashMap<>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("singleQuest", new SingleQuestCommand());
        commands.put("comment", new CommentCommand());
        commands.put("show", new ShowUserComment());
        commands.put("quest", new QuestCommand());
        commands.put("local", new LocaleCommand());
        commands.put("setToApprovedStatus", new CommentApprovalCommand());
        commands.put("setToRejectedStatus", new CommentRejectionCommand());
        commands.put("rateQuest", new QuestRatingCommand());
        commands.put("showQuestByRoomName", new ShowAllQuestCommand());
        commands.put("deleteQuest", new DeleteCommand());
        commands.put("showSingleQuest", new ShowSingleQuestCommand());
        commands.put("addQuest", new AddCommand());
        commands.put("editQuest", new EditCommand());
        commands.put("showQuestByRating", new ShowQuestByRatingCommand());
        commands.put("booking", new BookingCommand());
        commands.put("search", new SearhCommand());
        commands.put("showUserBooking", new ShowUserBookingCommand());
        commands.put("approveBooking", new BookingApprovalCommand());
        commands.put("rejectBooking", new BookingRejectionCommand());
    }

    public static Command getCommands(String type) {
        return commands.get(type);
    }
}

