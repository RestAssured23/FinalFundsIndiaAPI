package core.api.testing;
import org.testng.annotations.Test;

import java.util.Properties;

public class TestBasePlatform {
 private Properties properties = new Properties();

    @Test
    public String setUp() {
        String os = System.getProperty("os.name").toLowerCase();
        String path = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/";

        path += "prod/mf.properties"; // Assuming you have a config-uat.properties file

        if (os.contains("win")) {
            path = path.replace("/", "\\");
        } else if (!os.contains("mac") && !os.contains("linux")) {
            System.out.println("OS Not Detected");
            path = "";
        }

        return path;
    }

    @Test
    public String platform() {
        String Test_Login = setUp();
        return Test_Login;
    }
}