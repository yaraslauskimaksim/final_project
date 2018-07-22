package by.corporation.quest_fire.controller.util;

public class Constants {

    public static final int ITEMS_PER_PAGE = 6;

    public static final String ERROR = "error";
    public static final String LOGIN_ERROR = "errorInLogin";
    public static final String EMPTY_FIELDS = "emptyFields";
    public static final String INVALID_FIELDS = "invalidFields";
    public static final String USER_EXISTS = "userExits";
    public static final String EMPTY_LIST = "emptyList";
    public static final String COMMENT_SAVED = "commentSaved";
    public static final String RATING_SAVED = "ratingSaved";
    public static final String SUCCESS = "success";
    public static final String QUEST_ALREADY_EXISTS = "duplicatedQuest";
    /**
     * These constants are for request parameters
     */
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_ID  = "userId";
    public static final String FIRSTNAME= "firstname";
    public static final String LASTNAME = "lastname";
    public static final String USER  = "user";
    public static final String ROLE = "role";
    public static final String QUEST = "allQuests";
    public static final String SINGLE_QUEST  = "quest";
    public static final String NUMBER_OF_PAGE= "numberOfPages";
    public static final String CURRENT_PAGE  = "currentPage";
    public static final String QUEST_ID  = "questId";
    public static final String COMMENT  = "comment";
    public static final String COMMENTS  = "comments";
    public static final String COMMENT_DESCRIPTION  = "description";
    public static final String SCORE = "score";
    public static final String SEARCH_NAME = "name";
    public static final String MESSAGE= "message";
    public static final String TIME = "time";
    public static final String DATE= "date";
    public static final String NUMBER_OF_GUESTS = "numberOfGuests";
    public static final String PAGE = "page";
    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String USERS = "users";
    public static final String MESSAGES = "messages";
    public static final String QUEST_DESCRIPTION = "description";
    public static final String QUEST_NAME = "name";
    public static final String QUEST_GENRE = "genre";
    public static final String QUEST_IMAGE = "image";
    public static final String QUEST_ROOM_NAME = "roomName";
    public static final String BOOKING = "booking";
    public static final String LANGUAGE = "lang";
    public static final String BOOKING_DATE = "bookingDate";



    /**
     * These constants are for path (User)
     */

    public static final String PATH_HOME = "path.page.home";
    public static final String PATH_QUEST_BY_RATING= "path.page.questByRating";
    public static final String PATH_REGISTER = "path.page.register";

    public static final String PATH_LOGIN = "path.page.login";
    public static final String PATH_INDEX = "path.page.index";
    public static final String PATH_QUEST = "path.page.quest";
    public static final String PATH_QUEST_SINGLE = "path.page.singleQuest";
    public static final String RETURN_TO_SINGLE_QUEST_PAGE = "/frontController?command=singleQuest&questId=";
    public static final String GO_TO_USER_BOOKING_PAGE = "/frontController?command=showSingleUserBooking&page=";
    public static final String GO_TO_SINGLE_USER_BOOKING_PAGE = "path.page.bookingUser";
    public static final String GO_TO_SEARCH_RESULT_PAGE = "path.page.searchResultPage";
    /**
     * These constants are for path (QUEST OWNER)
     */
    public static final String PATH_SINGLE_QUEST_OWNER = "path.page.singleQuestOwner";
    public static final String PATH_QUESTS_BY_ROOM_NAME = "path.page.questByRoomName";
    public static final String PATH_SHOW_ALL_QUESTS_BY_ROOM_NAME = "/frontController?command=showQuestsByRoomName&page=";
    public static final String PATH_OWNER = "path.page.owner";
    public static final String PATH_SHOW_USER_BOOKING = "/frontController?command=showUserBooking&page=";
    public static final String PATH_BOOKING = "path.page.booking";
    public static final String RETURN_TO_CREATED_QUEST_PAGE = "/frontController?command=showSingleQuest&questId=";
    /**
     * These constants are for path (Administrator)
     */
    public static final String PATH_USER_STATUS = "path.page.userByStatus";
    public static final String PATH_ADMIN= "path.page.admin";
    public static final String PATH_SHOW_USER_ACTIVE = "frontController?command=showUserByStatus&status=active&page=";
    public static final String PATH_SHOW_USER_FROZEN = "frontController?command=showUserByStatus&status=frozen&page=";
    public static final String PATH_SHOW_USER_MESSAGES = "frontController?command=showUserMessages&page=";
    public static final String PATH_SHOW_USER_MESSAGE = "path.page.userMessages";
    public static final String PATH_SHOW_USER_COMMENTS = "path.page.userComment";
    public static final String PATH_SHOW_USER_COMMENT_AFTER_ACTION = "/frontController?command=show&page=";
    public static final String PATH_FROZEN_USER = "path.page.frozenUser";

    /**
     * These constants are for path (Errors)
     */
    public static final String ERROR_503 = "path.page.530";
    public static final String ERROR_401  = "path.page.401";
    /**
     * These constants are for messages
     */
    public static final String INVALID_LOGIN_MESSAGE = "Sorry, Invalid Data! Please try again!";
    public static final String USER_ALREADY_EXITS_MESSAGE = "Sorry, but user with such email already exists. Please provide new email!";
    public static final String MESSAGE_WAS_NOT_SENT = "Sorry, but message wasn't sent, please try again";
    public static final String MESSAGE_WAS_SENT = "Your message is successfully sent. Thank you! Please wait for activating your account!";
    public static final String EMPTY_QUEST_LIST = "Sorry, but there aren't any quests available!";
    public static final String EMPTY_MESSAGE_LIST= "Sorry, but there aren't any message available!";
    public static final String NO_BOOKING_OF_QUEST_ROOM = "Sorry, but there aren't any bookings in Pending Status!";
    public static final String EMPTY_FIELD_MESSAGE = "Sorry, but fill in empty fields!";
    public static final String QUEST_ALREADY_EXISTS_MESSGE = "Sorry, but name for yout quest is already in use. Please, try once again, but with another one!";
    public static final String COMMENT_WAS_SENT_MESSAGE = "Your comment is successfully sent. Thank you!";
    public static final String RATING_WAS_SAVED_MESSAGE = "Thank you for your rating!";


    /**
     * These constants are for upload controller
     */

    public static final String ABSOLUTE_PATH = "E:\\IdeaProjects\\com\\corportion\\quest_fire\\src\\main\\webapp\\static\\img";

    /**
     * These constants are for controller util
     */
    public static final String REFERER = "referer";
}
