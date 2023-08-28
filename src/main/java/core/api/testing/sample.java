package core.api.testing;

import com.beust.jcommander.Parameterized;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.basepath.AccessPropertyFile;
import core.model.*;
import core.model.otp.CommonOTP;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

import static core.api.CommonVariable.*;
import static core.api.CommonVariable.respec;
import static io.restassured.RestAssured.given;


public class sample extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;


    public sample() throws IOException {

        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "2.0")
                //  .addHeader("channel-id", Arrays.toString(channelIDs))
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
    public void testWithChannelID() {
        feature("10");
        feature("11");
        feature("12");
        // Add more channel IDs as needed
        userProfile("10");
        userProfile("11");
        userProfile("12");
    }

@Test
    public void feature(String channelID) {
        req.header("channel-id", channelID); // Set the current channel ID

        RequestSpecification res = given().spec(req);
        String response = res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();

        Reporter.log(response);
    }
    @Test
    public void userProfile(String channelID) {
        req.header("channel-id", channelID);
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/user-profile")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

  /*  @Test
    public void Recent_Transaction() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "0")        //1403821
                .queryParam("page", "1")
                .queryParam("size", "10");
        RecentTransaction.Root response = res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().as(RecentTransaction.Root.class);
    }
    @Test
    public void Cancel_Redeem() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo","40827099" );
        RequestSpecification res = given().spec(req).body(del);
        logResponse =  res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(logResponse);
    }*/
}

