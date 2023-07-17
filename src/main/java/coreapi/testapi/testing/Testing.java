package coreapi.testapi.testing;

import coreapi.basepath.accessproperty.AccessPropertyFile;
import coreapi.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;


import static io.restassured.RestAssured.given;

public class Testing extends AccessPropertyFile {
  //  basepath();
    RequestSpecification req =new RequestSpecBuilder()
            .setBaseUri(Basepath())
            .addHeader("x-api-version","2.0")
            .addHeader("channel-id","10")
            .addHeader("x-fi-access-token", accesstoken())
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
    @Test
    public void Holding_Profile() {
         try {
            RequestSpecification res = given().spec(req);
            HoldingProfile.Root hold_response = res.when().get("/core/investor/holding-profiles")
                    .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);
            Assert.assertEquals(200, hold_response.getCode());

            for (int i = 0; i < hold_response.getData().size(); i++) {
                for (int j = 0; j < hold_response.getData().get(i).getInvestors().size(); j++) {
                    if (hold_response.getData().get(i).getInvestors().get(j).getType().equalsIgnoreCase(holding_id)) {
                        InvestorId = hold_response.getData().get(i).getInvestors().get(j).getInvestorId();
                        Holdingid = hold_response.getData().get(i).getHoldingProfileId();
                        System.out.println(InvestorId);
                        System.out.println(Holdingid);
                    }
                }break;
            }
        } catch (AssertionError e) {
            System.out.println("Assertion error occurred: " + e.getMessage());
            System.exit(0);

        }
    }
}

