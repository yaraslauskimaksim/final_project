package by.corporation.quest_fire.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigurationManager {

    //This class takes information from file config.properties

    private static final String FILENAME = "config";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(FILENAME, Locale.ENGLISH);
    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
