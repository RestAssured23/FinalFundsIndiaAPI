package core.basepath;
import org.testng.annotations.Test;
import java.io.File;

public class BasePlatform {

 @Test
 public String setUp() {
     String os = System.getProperty("os.name").toLowerCase();
     String basepath = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/";

     String path ;

     if (os.contains("win")) {
         basepath = basepath.replace("/", "\\");
     } else if (!os.contains("mac") && !os.contains("linux")) {
         System.out.println("OS Not Detected");
         basepath = "";
     }
        path=basepath + "dev.properties";
     return path;
 }

    @Test
    public String platform() {
        String Test_Login = setUp();
        return Test_Login;
    }
}