package by.corporation.final_project.controller.command.util;

public class Constants {

    public static final int ITEMS_PER_PAGE = 6;
    public static final String MESSAGE_ID = "id";
    /**
     * These constants are for path (User)
     */
    public static final String PATH_OWNER = "path.page.owner";
    public static final String PATH_SINGLE_QUEST_OWNER = "path.page.singleQuestOwner";
    public static final String PATH_QUESTS_BY_ROOM_NAME = "path.page.questByRoomName";
    public static final String PATH_ERROR = "path.page.error";
    public static final String PATH_SHOW_ALL_QUESTS_BY_ROOM_NAME = "path.page.questByRoomNames";
    public static final String PATH_HOME = "path.page.home";
    public static final String PATH_QUEST_BY_RATING= "path.page.questByRating";
    public static final String PATH_REGISTER = "path.page.register";
    public static final String PATH_BOOKING = "path.page.booking";
    public static final String PATH_SHOW_USER_BOOKING = "/frontController?command=showUserBooking";
    public static final String PATH_LOGIN = "path.page.login";
    public static final String PATH_FROZEN_USER = "path.page.frozenUser";
    /**
     * These constants are for path (Administrator)
     */
    public static final String PATH_USER_STATUS = "path.page.userByStatus";
    public static final String PATH_ADMIN= "path.page.admin";
    public static final String PATH_SHOW_USER_ACTIVE = "frontController?command=showUserByStatus&status=active&page=";
    public static final String PATH_SHOW_USER_FROZEN = "frontController?command=showUserByStatus&status=frozen&page=";
    public static final String PATH_SHOW_USER_MESSAGES = "frontController?command=showUserMessages&page=";
    public static final String PATH_SHOW_USER_MESSAGE = "path.page.userMessages";

    /**
     * These constants are for messages
     */

    public static final String INVALID_LOGIN_MESSAGE = "Sorry, Invalid Data! Please try again!";
    public static final String MESSAGE_WAS_NOT_SENT = "Sorry, but message wasn't sent, please try again";
    public static final String MESSAGE_WAS_SENT = "Your message is successfully sent. Thank you! Please wait for activating your account!";
}
