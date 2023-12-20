package core.BtoB;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class testBtoB extends AccessPropertyFile {

    public testBtoB() throws IOException {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", getChannelID())
                .addHeader("x-fi-access-token", getAccessToken())
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();
        respec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void userProfile() {
        RequestSpecification res = given().spec(req);
        response = res.when().get("/core/user-profile")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }


    @Test(invocationCount = 22, threadPoolSize = 1)
    public void Sysplan() {
        RequestSpecification res = given().spec(req)
                .queryParam("userId", 152389);
        response = res.when().get("/manage/ifa/client/systematic-plans")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void tripartite() {
        RequestSpecification res = given().spec(req);
        response = res.when().get("/manage/ifa/advisor/tripartite")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void InvestorSearch() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"name\": \"Triveni\",\n" +
                        "  \"email\": \"tri.sharon01@gmail.com\",\n" +
                        "  \"mobile\": \"9841534099\",\n" +
                        "  \"orderBy\": \"\",\n" +
                        "  \"orderType\": \"DESC\",\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 10\n" +
                        "}");
        response = res.when().post("/manage/ifa/client/users")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void callBack() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"ifaName\": \"Triveni\",\n" +
                        "  \"email\": \"tri.sharon01@gmail.com\",\n" +
                        "  \"mobile\": \"9841534099\",\n" +
                        "  \"officeContact\": \" \",\n" +
                        "  \"comments\": \"Callback\",\n" +
                        "  \"fundsTag\": \"IFA03\"\n" +
                        "}");
        response = res.when().post("/manage/ifa/advisor/callback")
                .then().log().all().spec(respec).extract().response().asString();
    }
}
