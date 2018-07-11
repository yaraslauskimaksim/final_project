package by.corporation.final_project.controller.command.util;

import by.corporation.final_project.service.impl.QuestServiceImpl;


import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {

    public static int getCurrentPage(HttpServletRequest request) {
        int page = 0;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        return page;
    }
    public static int getNumberOfPage(int numberOfRecords, int itemsPerPage){

        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / itemsPerPage);
        return numberOfPages;
    }

}
