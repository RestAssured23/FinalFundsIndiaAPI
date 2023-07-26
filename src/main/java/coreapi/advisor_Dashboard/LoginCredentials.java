package coreapi.advisor_Dashboard;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import coreapi.model.Signin;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class LoginCredentials {
    static final String live = "https://api.fundsindia.com";
    static final String staging = "https://staging-api.fundsindia.com";
    static final String neo = "https://hotfix-api.fundsindia.com";
    static final String scrum2 = "https://scrum2-api.fundsindia.com";
    static RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri(neo)
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .setContentType(ContentType.JSON).build();
    static ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();

@Test
    public static String loginAndGetAccessToken(String emailId, String password) {
        HashMap<String, String> login = new HashMap<>();
        login.put("emailId", emailId);
        login.put("password", password);
        login.put("grantType", "credentials");
        login.put("refreshToken", "string");

        RequestSpecification res = given().spec(req)
                .body(login);
        Signin.Root response = res.when().post("/core/auth/sign-in")
                .then().spec(respec).extract().response().as(Signin.Root.class);
        return response.getData().getAccessToken();
    }

    public static String qateam() {
        return loginAndGetAccessToken("qateam@fundsindia.com", "Oct@#789");
    }
    public static String qualityTeam() {
        return loginAndGetAccessToken("ashwini.prabhu@fundsindia.com", "Ashes@149");
    }
    public static String localadmin() {
        return loginAndGetAccessToken("admin@wifs.com", "asdfasdf");
    }
}



