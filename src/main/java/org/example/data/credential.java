package org.example.data;

import io.restassured.RestAssured;
import org.example.model.Signin;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class credential {
    public static String baseURI, holdid, email, pwd;

    @Test
    public static String login() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        String path = "";
        Properties properties = new Properties();

        if (os.contains("win")) {
            path = (System.getProperty("user.dir") + "\\src\\main\\configfile\\config.properties");  //for Windows
        } else if (os.contains("mac")) {
            path = (System.getProperty("user.dir") + "/src/main/configfile/config.properties");  //for mac
        } else {
            System.out.println("OS Not Detected");
        }

        try {
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);

            // Access the values from the properties object
            baseURI = properties.getProperty("baseURI");
            email = properties.getProperty("email");
            pwd = properties.getProperty("password");
            holdid = properties.getProperty("holdingid");

            System.out.println(baseURI);
            System.out.println(email);
            System.out.println(pwd);
            System.out.println(holdid);


            //Login Payload
            HashMap<String, String> login = new HashMap<>();
            login.put("emailId", email);
            login.put("password", pwd);
            login.put("grantType", "credentials");
            login.put("refreshToken", "string");
            //Login API
            RestAssured.baseURI = baseURI;
            Signin.Root response = given().log().all()
                    .header("x-api-version", "2.0")
                    .header("channel-id", "10")
                    .header("Content-Type", "application/json")

                    .body(login).when().post("/core/auth/sign-in")
                    .then().assertThat().statusCode(200)
                    .header("Content-Type", "application/json")
                    .extract().response().as(Signin.Root.class);
            return response.getData().getAccessToken();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public static String URI() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String path = "";
        Properties properties = new Properties();

        if (os.contains("win")) {
            path = (System.getProperty("user.dir") + "\\src\\main\\configfile\\config.properties");  //for Windows
        } else if (os.contains("mac")) {
            path = (System.getProperty("user.dir") + "/src/main/configfile/config.properties");  //for mac
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
