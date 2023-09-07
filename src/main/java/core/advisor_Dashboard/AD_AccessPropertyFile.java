package core.advisor_Dashboard;

import core.basepath.BasePlatform;
import core.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AD_AccessPropertyFile {


    static Properties properties = new Properties();
    static String mailid,ccmailid;
    @Test
    public String getADBasePath() {
        AD_BasePlatform uri = new AD_BasePlatform();
        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            return properties.getProperty("baseURI");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public String getAdminAccessToken() {
        AD_BasePlatform uri = new AD_BasePlatform();
        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            mailid=properties.getProperty("investoremail");
            ccmailid=properties.getProperty("ccmail");
            if (properties.getProperty("accesstoken").isEmpty()) {
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
                        .body(login)
                        .when()
                        .post("/core/auth/sign-in")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .header("Content-Type", "application/json")
                        .extract()
                        .response()
                        .as(Signin.Root.class);
                return response.getData().getAccessToken();
            } else {
                return properties.getProperty("accesstoken");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}