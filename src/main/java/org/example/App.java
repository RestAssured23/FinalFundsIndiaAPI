package org.example;

import org.testng.TestNG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException {

        TestNG runner = new TestNG();
        List<String> xmlfile = new ArrayList<>();

        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
             xmlfile.add(System.getProperty("user.dir") + "\\src\\main\\configfile\\testng.xml");  //For Windows
            runner.setTestSuites(xmlfile);
            runner.run();

    } else if (os.contains("mac")) {
        xmlfile.add(System.getProperty("user.dir") + "/src/main/configfile/testng.xml");      // For MAC
        runner.setTestSuites(xmlfile);
        runner.run();
    } else {
        System.out.println("OS Not Detected");
    }
}
    catch (Exception e){
        System.out.println(e);
        }
    }
}
