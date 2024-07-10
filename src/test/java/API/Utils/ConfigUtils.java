package API.Utils;

import java.io.*;
import java.util.Properties;

public class ConfigUtils {

    private static final String CONFIG_FILE_PATH =System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\apiConfig.properties";

    public static void updatePostIdProperty(String newId) {
        System.out.println("CONFIG_FILE_PATH : "+CONFIG_FILE_PATH);
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            // Load existing properties from file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Update the post.id property
        properties.setProperty("post.id", newId);

        try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            // Save updated properties to file
            properties.store(output, "Updated post.id after POST test execution");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
