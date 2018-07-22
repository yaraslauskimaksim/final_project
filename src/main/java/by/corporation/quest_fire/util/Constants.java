package by.corporation.quest_fire.util;

public class Constants {
    /**
     * These constants are for command variables
     */
    public static final String ROLE = "role";
    public static final String USER_ID ="userId";
    public static final String QUEST_ID = "questId";
    public static final String QUESTS  = "quests";
    public static final String QUEST = "quest";
    public static final String QUEST_DESCRIPTION = "description";
    public static final String QUEST_NAME = "name";
    public static final String QUEST_GENRE = "genre";
    public static final String QUEST_IMAGE = "image";
    public static final String QUEST_ROOM_NAME = "roomName";
    public static final String CURRENT_PAGE= "currentPage";
    public static final int CURRENT_PAGE_DEFAULT = 1;
    public static final String QUESTS_PER_PAGE= "questsPerPage";
    public static final int ITEMS_PER_PAGE = 6;


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
    /**
     * These constants are for path (Administrator)
     */
    public static final String PATH_USER_STATUS = "path.page.userByStatus";
    public static final String PATH_ADMIN= "path.page.admin";
    public static final String PATH_SHOW_USER_ACTIVE = "frontController?command=showUserByStatus&status=active&page=";
    public static final String PATH_SHOW_USER_FROZEN = "frontController?command=showUserByStatus&status=frozen&page=";


    /**
     * These constants are for mysql queries (UserDAO)
     */

    public static final String FIRSTNAME = "usr_firstname";
    public static final String LASTNAME = "usr_lastname";
    public static final String USR_ID = "usr_id";
    public static final String USR_STATUS = "usr_status";
    public static final String USR_EMAIL = "usr_email";

    /**
     * These constants are for mysql queries (UserDAO)
     */
    public static final String QUE_NAME= "que_name";
    public static final String COMMENT_ID = "com_id";
    public static final String COMMENT_STATUS = "com_status";
    public static final String COMMENT_QUEST_ID = "com_quest_id";
    public static final String COMMENT_USER_ID = "com_user_id";
    public static final String COMMENT_DESCRIPTION = "com_description";
    public static final String QUE_ID = "que_id";
    public static final String QUE_DESCRIPTION = "que_description";
    public static final String QUE_GENRE = "que_genre";
    public static final String QUE_IMAGE = "que_image";
    public static final String QUE_SCORE = "que_score";




}
