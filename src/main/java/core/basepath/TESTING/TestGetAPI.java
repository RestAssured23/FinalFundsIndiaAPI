package core.basepath.TESTING;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static core.api.CommonVariable.*;
import static core.api.CommonVariable.response;
import static io.restassured.RestAssured.given;

public class TestGetAPI extends AccessPropertyFileTest{
    public TestGetAPI(String profile) throws IOException {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath(profile))
                .addHeader("x-api-version", "2.0")
                .addHeader("channel-id", getChannelID(profile))
                .addHeader("x-fi-access-token", getAccessToken(profile))
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
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
}
