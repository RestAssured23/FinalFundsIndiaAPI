package core.api.testing;

import core.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SiginTest {
    public String pwd;


    @Test
    public void sign() {

        HashMap<String, String> login = new HashMap<>();
        login.put("emailId", "dsathish0223@gmail.com");
        login.put("password", "KOUshik@2627");
        login.put("grantType", "credentials");
        login.put("refreshToken", "string");

        HashMap<String, String> adminlogin = new HashMap<>();
        login.put("emailId", "qateam@fundsindia.com");
        login.put("password", "Oct@#789");
        login.put("grantType", "credentials");
        login.put("refreshToken", "string");

        HashMap<String, String> local = new HashMap<>();
        local.put("emailId", "bankflowtest2@gmail.com");
        local.put("password", "asdfasdf12");
        local.put("grantType", "credentials");
        local.put("refreshToken", "string");

     /*   @Test
        public void testlogin() {*/
            //Login API
            RestAssured.baseURI = "https://scrum-api.fundsindia.com";
            Signin.Root response = given().log().all()
                    .header("x-api-version", "1.0")
                    .header("channel-id", 11)
                    .header("Content-Type", "application/json")
                    .body(local)
                    .when()
                    .post("/core/auth/sign-in")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Type", "application/json")
                    .extract()
                    .response()
                    .as(Signin.Root.class);

        }
    }
