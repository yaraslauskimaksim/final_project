package by.corporation.quest_fire.controller.command;

import by.corporation.quest_fire.controller.command.impl.*;
import by.corporation.quest_fire.controller.command.impl.administrator.*;
import by.corporation.quest_fire.controller.command.impl.quest_owner.*;
import by.corporation.quest_fire.controller.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandType {
    private static Map<String , Command> commands = new HashMap<>();

    static {
        //general commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("quest", new FindAllQuestCommand());
        commands.put("singleQuest", new FindSingleQuestCommand());
        commands.put("showQuestByRating", new FindQuestsByRatingCommand());
        commands.put("search", new SearchCommand());
        commands.put("local", new LocaleCommand());

        //user's commands
        commands.put("comment", new UserCommentCommand());
        commands.put("rateQuest", new RatingCommand());
        commands.put("contact", new ContactCommand());
        commands.put("booking", new BookingCommand());
        commands.put("showSingleUserBooking", new FindBookingCommand());

        //administrator's command
        commands.put("show", new FindUserCommentCommand());
        commands.put("setToApprovedStatus", new CommentApprovalCommand());
        commands.put("setToRejectedStatus", new CommentRejectionCommand());
        commands.put("showUserByStatus", new FindUserByStatus());
        commands.put("frozeUser", new BanUserCommand());
        commands.put("makeUserActive", new ActivateCommand());
        commands.put("showUserMessages", new FindUserMessageCommand());
        commands.put("deleteMessage", new DeleteMessageCommand());

        //quest owner's command
        commands.put("showUserBooking", new FindUserBookingCommand());
        commands.put("approveBooking", new BookingApprovalCommand());
        commands.put("rejectBooking", new BookingRejectionCommand());
        commands.put("addQuest", new AddQuestCommand());
        commands.put("editQuest", new EditQuestCommand());
        commands.put("showQuestsByRoomName", new FindAllQuestByRoomCommand());
        commands.put("deleteQuest", new DeleteQuestCommand());
        commands.put("showSingleQuest", new FindSingleQuestByRoomNameCommand());


    }

    public static Command getCommands(String type) {
        return commands.get(type);
    }
}

