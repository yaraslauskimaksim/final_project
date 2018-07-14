package by.corporation.final_project.controller.command.util;

import by.corporation.final_project.service.exception.ServiceException;
import by.corporation.final_project.service.impl.CommentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {

    private static final Logger LOGGER = LogManager.getLogger(ControllerUtil.class);

    public static int getCurrentPage(HttpServletRequest request) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
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
             commentQuantity = CommentServiceImpl.getCommentService().getCommentQuantity();
        } catch (ServiceException e) {
           LOGGER.warn("No comments");
        }
        return commentQuantity;
    }

}
