package core.basepath;

import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBasePlatform {

    @Test
    public Properties setUp(String environment) {
        Properties properties = new Properties();

        String os = System.getProperty("os.name").toLowerCase();
        String basePath = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/";

        String path;

        if (os.contains("win")) {
            basePath = basePath.replace("/", "\\");
        } else if (!os.contains("mac") && !os.contains("linux")) {
            System.out.println("OS Not Detected");
            basePath = "";
        }

        if (environment.equals("dev")) {
            path = basePath + "dev.properties";
        } else if (environment.equals("qa")) {
            path = basePath + "qa.properties";
        } else if (environment.equals("scrum")) {
            path = basePath + "scrum.properties";
        } else if (environment.equals("scrum2")) {
            path = basePath + "scrum2.properties";
        } else if (environment.equals("scrum4")) {
            path = basePath + "scrum4.properties";
        } else if (environment.equals("hotfix")) {
            path = basePath + "hotfix.properties";
        } else if (environment.equals("stage")) {
            path = basePath + "stage.properties";
        } else if (environment.equals("prod")) {
                path = basePath + "prod.properties";
        } else {
            System.out.println("Invalid environment");
            return null;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Test
    public Properties getProperties(String environment) {
        return setUp(environment);
    }

}