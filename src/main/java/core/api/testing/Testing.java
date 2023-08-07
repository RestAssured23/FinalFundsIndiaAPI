package core.api.testing;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
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

    @Test //(threadPoolSize = 5, invocationCount = 2)
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
    @Test
    public void holdingProfile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            String idList = data.getHoldingProfileId();
            if (idList.equalsIgnoreCase(holdingid_pro)) {
                Holdingid = idList;
                System.out.println("Holding ID is matched with the property file: " + Holdingid);
                if (data.getHoldingProfileId().equalsIgnoreCase(Holdingid)) {
                    int foundIndex = holdResponse.getData().indexOf(data);
                    InvestorId = holdResponse.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            Holdingid = holdResponse.getData().get(0).getHoldingProfileId();
            InvestorId = holdResponse.getData().get(0).getInvestors().get(0).getInvestorId();
            System.out.println("Holding ID is not matched with the property file: " + Holdingid);
        }
    }
 //Bank
 @Test
 public void Bank_Details() {
     RequestSpecification res = given().spec(req)
             .queryParam("investorId ","285961");
     res.when().get("/core/investor/banks")
             .then().log().all().spec(respec);
 }

//STP
    @Test
public void stp() {
    RequestSpecification res = given().spec(req)
            .queryParam("holdingProfileId", "183318")
            .queryParam("page", "1")
            .queryParam("size", "50");
    res.when().get("/core/investor/current-stps")
            .then().log().all().spec(respec);
}
}