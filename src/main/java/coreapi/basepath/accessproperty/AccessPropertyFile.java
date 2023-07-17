package coreapi.basepath.accessproperty;

import coreapi.basepath.BasePlatform;
import coreapi.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AccessPropertyFile {
    public static String holding_id;
    static Properties properties = new Properties();
    @Test
    public String Basepath() {
        BasePlatform uri = new BasePlatform();

        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            return properties.getProperty("baseURI");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public String accesstoken() throws IOException {
        BasePlatform uri = new BasePlatform();
        holding_id = properties.getProperty("holdingid");


try{
        FileInputStream fis = new FileInputStream(uri.platform());
        properties.load(fis);
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

}
