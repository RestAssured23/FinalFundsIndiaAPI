package coreapi.testapi.testing;

import coreapi.basepath.AccessPropertyFile;
import coreapi.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;


import static io.restassured.RestAssured.given;

public class Testing extends AccessPropertyFile {
    //  basepath();
    RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri(Basepath())
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .addHeader("x-fi-access-token", accesstoken())
            .setContentType(ContentType.JSON).build().log().all();
    ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid;
    String InvestorId,response;

    public Testing() throws IOException {
    }

    @Test
    public void Feature() {
        RequestSpecification res = given().spec(req);
        String Feature = res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(Feature);
    }

    @Test
    public void Holding_Profile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root hold_response = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (int i = 0; i < hold_response.getData().size(); i++) {
            int foundIndex = -1;
            String id_list = hold_response.getData().get(i).getHoldingProfileId();
            if (id_list.equalsIgnoreCase(holdingid_pro)) {
                Holdingid = hold_response.getData().get(i).getHoldingProfileId();
                if (hold_response.getData().get(i).getHoldingProfileId().equalsIgnoreCase(Holdingid)) {
                    foundIndex = i;
                    InvestorId = hold_response.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true; // Set the flag to true
                break;
            }
        }
            if (!matchFound ) {
                Holdingid = hold_response.getData().get(0).getHoldingProfileId();
                InvestorId = hold_response.getData().get(0).getInvestors().get(0).getInvestorId();
                System.out.println("Holding ID is not matched with property file :"+Holdingid);
                System.out.println("Holding ID is not matched with property file :"+InvestorId);
            }
        }

    @Test(priority = 1)
    public void Dashboard() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=  res.when().get("/core/investor/dashboard")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
}

