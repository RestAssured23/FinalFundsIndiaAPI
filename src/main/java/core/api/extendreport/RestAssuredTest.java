package core.api.extendreport;

import com.aventstack.extentreports.Status;
import core.basepath.AccessPropertyFile;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static core.api.CommonVariable.*;
import static core.api.CommonVariable.response;
import static core.api.extendreport.ExtentManager.extentReports;
import static core.api.extendreport.ExtentManager.extentTest;
import static io.restassured.RestAssured.given;

public class RestAssuredTest extends AccessPropertyFile {
    public RestAssuredTest() throws IOException {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "2.0")
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
        public void feature() {
            extentTest = extentReports.createTest("Example Test");
            RequestSpecification res = given().spec(req);
            response=res.when().get("/core/features")
                    .then().log().all().spec(respec).extract().response().asString();
        }
    }

