package core.api.testing;

import core.model.Signin;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class SiginTest {
    public String pwd;


    @Test       //(invocationCount = 18, threadPoolSize = 1)
    public void sign() {
        HashMap<String, String> liveLogin = new HashMap<>();
        liveLogin.put("emailId", "dsathish0223@gmail.com");
        liveLogin.put("password", "KOUshik@2627");
       /* liveLogin.put("emailId", "tri.sharon01@gmail.com");
        liveLogin.put("password", "Wealth007");*/
        liveLogin.put("grantType", "credentials");
        liveLogin.put("refreshToken", "string");

        HashMap<String, String> adminlogin = new HashMap<>();
        adminlogin.put("emailId", "qateam@partner.fundsindia.com");
        adminlogin.put("password", "asdfasdf14");
        adminlogin.put("grantType", "credentials");
        adminlogin.put("refreshToken", "string");

        HashMap<String, String> local = new HashMap<>();
        local.put("emailId", "bankflowtest2@gmail.com");
        local.put("password", "asdfasdf12");
        local.put("grantType", "credentials");
        local.put("refreshToken", "string");

     /*   @Test
        public void testlogin() {*/
            //Login API
            RestAssured.baseURI = "https://api.fundsindia.com";
            Signin.Root response = given().log().all()
                    .header("x-api-version", "1.0")
                    .header("channel-id", 11)
                    .header("Content-Type", "application/json")
                    .body(liveLogin)
                    .when()
                    .post("/core/auth/sign-in")
                  //  .post("/core-partner/auth/sign-in")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Type", "application/json")
                    .extract()
                    .response()
                    .as(Signin.Root.class);

        }
    @Test
    public void Sysplan(){
        RequestSpecification res = given().spec(req)
                .queryParam("")
                .queryParam("userId", 152389);
        response=res.when().get("/core/investor/systematic-plan")
                .then().log().all().spec(respec).extract().response().asString();
    }
    }
