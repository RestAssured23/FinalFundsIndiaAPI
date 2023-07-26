package core.api.testing;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
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
            .setBaseUri(getBasePath())
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .addHeader("x-fi-access-token", getAccessToken())
            .setContentType(ContentType.JSON).build();
    ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid;
    String InvestorId, response;

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

        RequestSpecification res = given().spec(req);
        HoldingProfile.Root hold_response = res.when().get("/core/investor/holding-profiles")
                .then().spec(respec).extract().response().as(HoldingProfile.Root.class);
if(hold_response.getCode()==200) {
    if (hold_response.getCode() == 200) {
        System.out.println("pass");
    } else {
        System.out.println("test");
    }
}
else System.out.println("not matched ");
   }




    @Test
    public void holdingProfile() {
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);


        for (HoldingProfile.Datum abc : holdResponse.getData()) {
            System.out.println(abc.getMobile());
        }


    }

    @Test
    public void sample() {
        int a[] = {10, 20, 30, 40};

        switch (20) {
            case 10:
                System.out.println("10 success");
                break;
            case 20:
                System.out.println("20 success");
                break;
            case 30:
                System.out.println("30 success");
                break;
        }
    }
}

