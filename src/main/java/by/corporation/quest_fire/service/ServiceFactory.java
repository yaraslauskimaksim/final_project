package by.corporation.quest_fire.service;


import by.corporation.quest_fire.service.impl.*;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static final QuestService questService = new QuestServiceImpl();
    private static final BookingService bookingService = new BookingServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final MessageService messageService = new MessageServiceImpl();
    private static final CommentService commnetService = new CommentServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public QuestService getQuestService() {
        return questService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public UserService getUserService() {
        return userService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public CommentService getCommnetService() {
        return commnetService;
    }
}