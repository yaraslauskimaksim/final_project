package by.corporation.final_project.controller.command;

import by.corporation.final_project.controller.command.impl.LocaleCommand;
import by.corporation.final_project.controller.command.impl.LoginCommand;
import by.corporation.final_project.controller.command.impl.LogoutCommand;
import by.corporation.final_project.controller.command.impl.SearhCommand;
import by.corporation.final_project.controller.command.impl.administrator.*;
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
        //user's commands
        commands.put("contact", new ContactCommand());
        ///
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
        //Administrator commands
        commands.put("showUserByStatus", new ShowUserByStatus());
        commands.put("frozeUser", new FrozeUserCommand());
        commands.put("makeUserActive", new MakeUserActiveCommand());
        commands.put("show", new ShowUserComment());
        commands.put("showUserMessages", new ShowUserMessageCommand());
        commands.put("deleteMessage", new DeleteMessageCommand());
    }

    public static Command getCommands(String type) {
        return commands.get(type);
    }
}

