package coreapi.basepath;
import org.testng.annotations.Test;
import java.io.File;

public class BasePlatform {
    public static String propertyfile="hotfix.properties";
@Test
    public String setUp() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                    File.separator + "configfile" + File.separator + propertyfile;  //for Windows

            //     path = (System.getProperty("user.dir") + "\\src\\main\\configfile\\local.properties");  //for Windows
        } else if (os.contains("mac")) {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                    File.separator + "configfile" + File.separator + propertyfile;
            // path = (System.getProperty("user.dir") + "/src/main/configfile/local.properties");  //for mac
        } else {
            System.out.println("OS Not Detected");
            return "";
        }
    }
    @Test
    public String platform() {
       String Test_Login =setUp();
       return Test_Login;
}
}