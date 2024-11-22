package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getDBConnectionString(String propertyFileName) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertyFileName)) {
            properties.load(fileInputStream);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            return "jdbc:mysql://" + url + "?user=" + username + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
