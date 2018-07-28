package by.corporation.quest_fire.controller.util;

import javax.servlet.http.Part;
import java.io.File;

/**
 * This is a upload controller helper.
 */
public final class UploadUtil {

    private UploadUtil(){}

    /**
     * This method creates the save directory if it does not exists
     */

    public static void saveDirectory(String savePath) {
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
    }

    /**
     * This method extracts file name from HTTP header content-disposition
     * @return file name
     */
    public static String extractFileName(Part part) {
        String contentDisposition = part.getHeader(Constants.CONTENT_DISPOSITION);
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith(Constants.FILE_NAME)) {
                return s.substring(s.indexOf(Constants.EQUAL_SIGN) + 2, s.length()-1);
            }
        }
        return Constants.EMPTY_STRING;
    }

}
