package core.api.testing;

import core.basepath.AccessPropertyFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Testing extends AccessPropertyFile {
    private Response response;
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private String Holdingid, InvestorId;

    public Testing() throws IOException {

        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "2.0")
                .addHeader("channel-id", "10")
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

    @Test(threadPoolSize = 5, invocationCount = 2)
    public void feature() {
        RequestSpecification res = given().spec(req);
        response = res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response();

        String responseBody = response.asString();
        Reporter.log(responseBody);

        long startTime = response.getTime();
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        System.out.println("Response time for request: " + responseTime + " ms");
    }
}