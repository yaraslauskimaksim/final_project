package by.corporation.quest_fire.controller;


import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.controller.util.FrontControllerUtil;
import by.corporation.quest_fire.controller.util.UploadUtil;
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
     * This method handles file upload.
     * Firstly, it constructs path of the directory to save uploaded file.
     * Then using {@link UploadUtil} class, it creates the save directory if it does not exists.
     * After it using {@link UploadUtil} class, it extracts
     * file name from HTTP header content-disposition.
     * And it refines the fileName in case it is an absolute path.
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Integer questId = (Integer) session.getAttribute(Constants.QUEST_ID);
        String fileName = null;

        String appPath = request.getServletContext().getRealPath(Constants.EMPTY_STRING);

        String savePath = appPath + File.separator + Constants.SAVE_DIR;
        UploadUtil.saveDirectory(savePath);

        for (Part part : request.getParts()) {
            fileName = UploadUtil.extractFileName(part);
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);
        }

        QuestService questService = ServiceFactory.getInstance().getQuestService();
        try {
            questService.addImage(fileName, questId);
            response.sendRedirect(request.getContextPath() + FrontControllerUtil.getRefererPage(request));
        } catch (ServiceException e) {
            LOGGER.error("Image wasn't saved", e);
            response.sendRedirect(request.getContextPath() + BundleResourceManager.getConfigProperty(Constants.ERROR_503));
        }

    }


}

