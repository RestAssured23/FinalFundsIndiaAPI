package core.accesspropertyfile;

import io.restassured.RestAssured;
import core.model.Signin;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class credential {
    public static String baseURI, holdid;
public static String propertyfile="local.properties";
    @Test
    public static String login() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
            String path = "";
            Properties properties = new Properties();

            if (os.contains("win")) {
                path = (System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                        File.separator + "configfile" + File.separator + propertyfile);  //for Windows

                //     path = (System.getProperty("user.dir") + "\\src\\main\\configfile\\local.properties");  //for Windows
            } else if (os.contains("mac")) {
                path = (System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                        File.separator + "configfile" + File.separator + propertyfile);     //MAC
                // path = (System.getProperty("user.dir") + "/src/main/configfile/local.properties");  //for mac
            } else {
                System.out.println("OS Not Detected");
            }

            try {
                    FileInputStream fis = new FileInputStream(path);
                    properties.load(fis);
                    // Access the values from the properties object
                  //  holdid = properties.getProperty("holdingid");

                    //Login Payload
                    HashMap<String, String> login = new HashMap<>();
                    login.put("emailId", properties.getProperty("email"));
                    login.put("password", properties.getProperty("password"));
                    login.put("grantType", "credentials");
                    login.put("refreshToken", "string");

                    //Login API
                    RestAssured.baseURI = properties.getProperty("baseURI");
                    Signin.Root response = given().log().all()
                            .header("x-api-version", "2.0")
                            .header("channel-id", "10")
                            .header("Content-Type", "application/json")

                            .body(login).when().post("/core/auth/sign-in")
                            .then().assertThat().statusCode(200)
                            .header("Content-Type", "application/json")
                            .extract().response().as(Signin.Root.class);
                    return response.getData().getAccessToken();

            }catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

    }

    @Test
    public static String URI() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String path = "";
        Properties properties = new Properties();

        if (os.contains("win")) {
            path = (System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                    File.separator + "configfile" + File.separator + propertyfile);  //for Windows

        } else if (os.contains("mac")) {
            path = (System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                    File.separator + "configfile" + File.separator + propertyfile);               //for MAC
        } else {
            System.out.println("OS Not Detected");
        }

        try {
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
            return baseURI = properties.getProperty("baseURI");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
