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
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static core.api.CommonVariable.InvestorId;
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
    @Test
    public void holdingProfile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            String idList = data.getHoldingProfileId();
            if (idList.equalsIgnoreCase(holdingid_pro)) {
                holdingId = idList;
                System.out.println("Holding ID is matched with the property file: " + holdingId);
                if (data.getHoldingProfileId().equalsIgnoreCase(holdingId)) {
                    int foundIndex = holdResponse.getData().indexOf(data);
                    InvestorId = holdResponse.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            holdingId = holdResponse.getData().get(0).getHoldingProfileId();
            InvestorId = holdResponse.getData().get(0).getInvestors().get(0).getInvestorId();
            System.out.println("Holding ID is not matched with the property file: " + holdingId);
            System.out.println("Holding ID is not matched with the property file: " + InvestorId);
        }
    }
    @Test
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 282306)
                .queryParam("consumerCode","10000000110025")
                .queryParam("sipType","flexi");
         res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();

    }
    @Test
    public void Delete_Mandate() {
        RequestSpecification res = given().spec(req)
                .queryParam("consumerCode", "10000000114660");
        res.when().delete("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void banks_Delete() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 284288)
                .queryParam("userBankId",2);
        res.when().delete("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void AddBanks() {
        String payload="{\n" +
                "  \"investorId\": \"284288\",\n" +
                "  \"accountHolderName\": \"Third Bank\",\n" +
                "  \"bankId\": \"581\",\n" +
                "  \"bankName\": \"IDFC BANK LIMITED\",\n" +
                "  \"type\": \"others\",\n" +
                "  \"ifsc\": \"IDFB0080108\",\n" +
                "  \"accountNo\": \"10028360100\",\n" +
                "  \"accountType\": \"Individual\",\n" +
                "  \"bankAccountType\": \"Savings\"\n" +
               /* "  \"cheque\": {\n" +
                "    \"content\": \"string\",\n" +
                "    \"name\": \"string\"\n" +
                "  }\n" +*/
                "}";
        RequestSpecification res = given().spec(req)
                        .body(payload);
        res.when().post("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void bankFeature() {
        Map<String,Object> payloadfeature=new HashMap<>();
        payloadfeature.put("investorId","284288");
        payloadfeature.put("userBankId","10");
        payloadfeature.put("type","make_as_primary");

        RequestSpecification res = given().spec(req)
                        .body(payloadfeature);
        res.when().post("/core/investor/banks/features")
                .then().log().all().spec(respec).extract().response().asString();
    }
}