package core.basepath;
import org.testng.annotations.Test;
import java.io.File;

public class BasePlatform {
 //   public static String propertyfile="local.properties";
 @Test
 public String setUp() {
     String os = System.getProperty("os.name").toLowerCase();
     String path = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/mf.properties";

     if (os.contains("win")) {
         path = path.replace("/", "\\");
     } else if (!os.contains("mac") && !os.contains("linux")) {
         System.out.println("OS Not Detected");
         path = "";
     }

     return path;
 }
/*@Test
    public String setUp() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return System.getProperty("user.dir") + "\\data\\automation-testing\\wifs\\config\\properties\\local.properties";
        } else if (os.contains("mac")) {
            return System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/local.properties";
        }
        else if (os.contains("linux")) {
            return System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/local.properties";
        }
        else {
            System.out.println("OS Not Detected");
            return "";
        }*/

   /* if (os.contains("win")) {
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                File.separator + "configfile" + File.separator + propertyfile;
    } else if (os.contains("mac")) {
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                File.separator + "configfile" + File.separator + propertyfile;
    } else {
        System.out.println("OS Not Detected");
        return "";
    }*/

    @Test
    public String platform() {
       String Test_Login =setUp();
       return Test_Login;
}
}