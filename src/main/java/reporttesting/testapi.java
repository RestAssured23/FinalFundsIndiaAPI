package reporttesting;

import core.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class testapi {

    @Test
    public void signinAPI(){
        HashMap<String, String> payload = new HashMap<>();
        payload.put("emailId", "regression@gmail.com");
        payload.put("password", "asdfasdf123");
        payload.put("grantType", "credentials");
        payload.put("refreshToken", "string");

        RestAssured.baseURI="https://scrum-api.fundsindia.com";
              Signin.Root response=given().log().all()
          //       given().log().all()
                        .header("x-api-version","1.0")
                .header("channel-id", 11)
                .header("Content-Type", "application/json")
                        .body(payload)
                        .when().post("/core/auth/sign-in")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .header("Content-Type", "application/json")
                        .extract().response()
                         .as(Signin.Root.class);
        System.out.println(response.getData().getAccessToken());
    }
}
