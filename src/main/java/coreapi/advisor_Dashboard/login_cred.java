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

public class login_cred {

    public static String neo = "https://hotfix-api.fundsindia.com";
    public static String staging = "https://staging-api.fundsindia.com";
    public static String live = "https://api.fundsindia.com";


    static RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri(neo)
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .setContentType(ContentType.JSON).build();

    static ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();

    @Test
    public static String qateam() {
        HashMap<String, String> login = new HashMap<String, String>();
        login.put("emailId", "qateam@fundsindia.com");
        login.put("password", "Oct@#789");
        login.put("grantType", "credentials");
        login.put("refreshToken", "string");

        RequestSpecification res = given().spec(req)
                .body(login);
        Signin.Root response = res.when().post("/core/auth/sign-in")
                .then().spec(respec).extract().response().as(Signin.Root.class);
        return response.getData().getAccessToken();
    }
    @Test
    public static String Quality_Team()
    {
        HashMap<String, String> login = new HashMap<String, String>();
        login.put("emailId", "ashwini.prabhu@fundsindia.com");
        login.put("password", "asdfasdf");
        login.put("grantType", "credentials");        login.put("refreshToken", "string");

        RequestSpecification res=given().spec(req)
                .body(login);
        Signin.Root response =res.when().post("/core/auth/sign-in")
                .then().spec(respec).extract().response().as(Signin.Root.class);
        return response.getData().getAccessToken();
    }
}