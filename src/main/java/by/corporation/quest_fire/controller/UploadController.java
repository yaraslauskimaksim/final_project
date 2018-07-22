package by.corporation.quest_fire.controller;


import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.ControllerUtil;
import by.corporation.quest_fire.service.QuestService;
import by.corporation.quest_fire.service.ServiceFactory;
import by.corporation.quest_fire.service.exception.ServiceException;
import by.corporation.quest_fire.util.BundleResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;


public class UploadController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);
    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Integer questId = (Integer) session.getAttribute(Constants.QUEST_ID);
        String fileName = null;

        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
        saveDirectory(savePath);

        for (Part part : request.getParts()) {
            fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);
        }
        QuestService questService = ServiceFactory.getInstance().getQuestService();
        try {
            questService.addImage(fileName, questId);
            response.sendRedirect(request.getContextPath() + ControllerUtil.getRefererPage(request));
        } catch (ServiceException e) {
            LOGGER.error("Image wasn't saved", e);
            response.sendRedirect(request.getContextPath() + BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }

    }


    private void saveDirectory(String savePath) {
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

}

