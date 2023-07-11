package org.example.apicollection;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.data.Login;
import org.example.model.HoldingProfile;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Testing {
    RequestSpecification req =new RequestSpecBuilder()
            .setBaseUri(Login.URI())
            .addHeader("x-api-version","2.0")
            .addHeader("channel-id","10")
            .addHeader("x-fi-access-token", Login.Regression())
            .setContentType(ContentType.JSON).build().log().all();
        ResponseSpecification respec =new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid;      String InvestorId;
    public Testing() throws IOException {
    }

    @Test(priority = 1)
    public void Feature() {
        RequestSpecification res = given().spec(req);
        String Feature=res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();

        Reporter.log(Feature);
    }

    @Test(priority = 1)
    public void Sys_plan() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "181557");
     res.when().get("/core/investor/systematic-plan/sips")
                .then().log().all().spec(respec);
    }
        }

