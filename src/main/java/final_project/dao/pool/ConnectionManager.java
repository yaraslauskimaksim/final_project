package final_project.dao.pool;

import java.util.Locale;
import java.util.ResourceBundle;

public final class ConnectionManager {

        //This class takes information from file database.properties

        private static final String FILENAME = "database";
        private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(FILENAME);

        private ConnectionManager() { }

        public static String getProperty(String key) {
            return resourceBundle.getString(key);
        }


}
