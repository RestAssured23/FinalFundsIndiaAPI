package coreapi;

import org.testng.TestNG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException {
        TestNG runner = new TestNG();
        List<String> xmlFiles = new ArrayList<>();

        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
             xmlFiles.add(System.getProperty("user.dir") + "\\src\\main\\configfile\\testng.xml");  //For Windows
            runner.setTestSuites(xmlFiles);
            runner.run();

    } else if (os.contains("mac")) {
        xmlFiles.add(System.getProperty("user.dir") + "/src/main/configfile/testng.xml");      // For MAC
        runner.setTestSuites(xmlFiles);
        runner.run();
    } else {
                throw new RuntimeException("OS Not Detected");
    }
}
    catch (Exception e){
        System.out.println(e);
        }
    }
}
