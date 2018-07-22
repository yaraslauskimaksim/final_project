package by.corporation.quest_fire.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class BundleResourceManager {

    private BundleResourceManager () { }

    private static final String FILENAME_CONFIG = "config";
    private static final String FILENAME_DATABASE = "database";

    private final static ResourceBundle configBundle = ResourceBundle.getBundle(FILENAME_CONFIG, Locale.ENGLISH);
    private final static ResourceBundle databaseBundle = ResourceBundle.getBundle(FILENAME_DATABASE, Locale.ENGLISH);

    public static String getConfigProperty(String key) {
        return configBundle.getString(key);
    }

    public static String getDatabasegProperty(String key) {
        return databaseBundle.getString(key);
    }
}
