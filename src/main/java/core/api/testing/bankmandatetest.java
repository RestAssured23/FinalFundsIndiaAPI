package core.api.testing;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import core.model.otp.CommonOTP;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class bankmandatetest extends AccessPropertyFile {
    private Response response;
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private String Holdingid, InvestorId;

    public bankmandatetest() throws IOException {

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
    public void BankQR() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 1401246);
        res.when().get("/core/investor/banks/qr")
                .then().log().all().contentType(ContentType.XML);
    }
    @Test
    public void callback() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 1401246)
                .queryParam("id","");
        res.when().get("/core/investor/banks/qr/callback")
                .then().log().all().spec(respec);
    }
    @Test
    public void InvestorBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 1401246);
        res.when().get("/core/investor/banks")
                .then().log().all().spec(respec);
    }
    @Test
    public void webhook() {
        RequestSpecification res = given().spec(req)
                .body("{}");
            //    .queryParam("investorId", 1401246);
        res.when().post("/core/investor/banks/qr/webhook")
                .then().log().all().contentType(ContentType.XML);
    }



    @Test
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 1401246);

         res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void Delete_Mandate() {
        RequestSpecification res = given().spec(req)
                .queryParam("consumerCode", "100000001727822");
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

    @Test               // NRI OTP check
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","454545");
        otppayload.put("workflow", "swp");

        RequestSpecification commonotp = given().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }
    @Test
    public void Nominee_Add() {         // Post API

        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"holdingProfileId\": \"181201\",\n" +
                        "  \"optedOut\": false,\n" +
                        "  \"nominees\": [\n" +
                        "    {\n" +
                        "      \"dateOfBirth\": \"1981-10-08T00:00:00.000+0530\",\n" +
                        "      \"firstName\": \"test T\",\n" +
                        "      \"relationship\": \"Brother\",\n" +
                        "      \"percentage\": 100\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .cookie("Test","Test");
        res.when().log().body().log().method().post("/core/investor/nominees")
                .then()
                .log().ifValidationFails(LogDetail.STATUS)
                .log().body().spec(respec);
    }
}