package core.api.testing;

import core.model.Signin;
import io.restassured.RestAssured;
import lombok.Value;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SiginTest {


public String pwd;

@Test
    public void sign(){
    HashMap<String, String> login = new HashMap<>();
    login.put("emailId","dsathish0223@gmail.com");
    login.put("password", "KOUshik@2627");
    login.put("grantType", "credentials");
    login.put("refreshToken", "string");
    //Login API
    RestAssured.baseURI = "https://scrum-api.fundsindia.com";
    Signin.Root response = given().log().all()
            .header("x-api-version", "1.0")
            .header("channel-id", 10)
            .header("Content-Type", "application/json")
            .body(login)
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
