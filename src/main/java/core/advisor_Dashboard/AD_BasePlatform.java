package core.advisor_Dashboard;

import org.testng.annotations.Test;

import java.io.File;

public class AD_BasePlatform {
    public static String propertyfile="advisory.properties";
@Test
public String setUp() {
    String os = System.getProperty("os.name").toLowerCase();
    String path = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/advisory.properties";

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
       String Test_Login =setUp();
       return Test_Login;
}
}