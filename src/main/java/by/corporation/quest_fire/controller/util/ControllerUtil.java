package by.corporation.quest_fire.controller.util;

import by.corporation.quest_fire.controller.command.RequestContent;
import by.corporation.quest_fire.service.CommentService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.service.impl.CommentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public final class ControllerUtil {

    private static final Logger LOGGER = LogManager.getLogger(ControllerUtil.class);

    private ControllerUtil(){}

    public static int getCurrentPage(RequestContent requestContent) {
        int page = 1;
        if (requestContent.getRequestParameter("page") != null) {
            page = Integer.parseInt(requestContent.getRequestParameter("page"));
        }
        return page;
    }
    public static int getNumberOfPage(int numberOfRecords, int itemsPerPage){

        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / itemsPerPage);
        return numberOfPages;
    }

    public static int getCommentQuantity(){
        int commentQuantity = 0;
        try {
            CommentService commentService = ServiceFactory.getInstance().getCommnetService();
             commentQuantity = commentService.getCommentQuantity();
        } catch (ServiceException e) {
           LOGGER.warn("No comments");
        }
        return commentQuantity;
    }


    public static String getRefererPage(HttpServletRequest request) {
        String refererPage = request.getHeader(Constants.REFERER);
        return refererPage;
    }

}
