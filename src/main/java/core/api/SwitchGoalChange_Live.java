package core.api;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DBconnection;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class SwitchGoalChange_Live extends AccessPropertyFile {

   String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMzMxODcwIiwic2NvcGVzIjoicmVhZCx3cml0ZSIsIm5hbWUiOiJzYXRoaXNoIEQiLCJlbWFpbCI6ImRzYXRoaXNoMDIyM0BnbWFpbC5jb20iLCJtb2JpbGUiOiI4MDcyMDA3NTk5IiwibWFuYWdlbWVudC11c2VyLWlkIjoxODkwNzQzLCJtYW5hZ2VtZW50LXVzZXItcm9sZXMiOiJhZG1pbiIsImlzcyI6ImZ1bmRzaW5kaWEuY29tIiwianRpIjoiNWI4ZWJiMzUtM2I3NS00YTZiLWJkMTQtNmI2ZWZiODJmNDg4IiwiaWF0IjoxNzAxMTYyMzczLCJleHAiOjE3MDExNjYwMzN9.9lN7v2qQ0qGYkGjF3TJm8ZMH-4RObpZallzlEMTpnxcU7ZCXY9NgTqcerGZMKggTs6MeIr5KCJ2Nj_PzRVXH7g";

    String dbotp,DB_refid;

    public SwitchGoalChange_Live() {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
           //    .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", getChannelID())
          //   .addHeader("x-fi-access-token", token)
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
    @Test(priority = 14)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","79958299852");
        otppayload.put("workflow", "switch");
        RequestSpecification commonotp = given().spec(req)
                .header("x-api-version","1.0")
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }

    @Test(priority = 16)
    public void Verify_OTP() {
        VerifyOtpRequest.Root payload = new VerifyOtpRequest.Root();
        VerifyOtpRequest.Otp otp = new VerifyOtpRequest.Otp();
        otp.setSms("");
        otp.setEmail("");
        otp.setEmail_or_sms("959243");
        payload.setOtp(otp);
        payload.setOtpReferenceId("39c367b5-f748-4aae-9718-129321010abd");
        RequestSpecification res1 = given().spec(req)
                .header("x-api-version","1.0")
                .body(payload);
        res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }
    @Test(priority = 17)

    public void testswitch(){
        String livePayload="{\n" +
                "  \"goalName\": \"sathish D Portfolio\",\n" +
                "  \"toSchemeName\": \"Mirae Asset Equity Savings Fund-Reg(G)\",\n" +
                "  \"fromSchemeName\": \"Mirae Asset Emerging Bluechip-Reg(G)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromOption\": \"Growth\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \"39c367b5-f748-4aae-9718-129321010abd\",\n" +
                "  \"holdingProfileId\": \"1403821\",\n" +
                "  \"folio\": \"79958299852\",\n" +
                "  \"goalId\": \"2932872\",\n" +
                "  \"toGoalId\": \"3363297\",\n" +           //2932872   //3363297
                "  \"fromSchemeCode\": \"9767\",\n" +
                "  \"toSchemeCode\": \"39401\",\n" +
                "  \"toOption\": \"Growth\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";

        RequestSpecification redeem = given().spec(req)
              //  .addHeader("x-api-version", "1.0")
                .header("x-api-version","2.0")
                .body(livePayload);
        redeem.when().post("/core/investor/switch").then().log().all().spec(respec);
    }
//
    @Test(priority = 18)
    public void recentTransactions() {
        RequestSpecification res = given()
                .header("x-api-version","1.0")
                .spec(req)
                .queryParam("holdingProfileId", "1403821");
        response=res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
/*    @Test(priority = 19)
    public void Cancel_Switch() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", "1382766");

        RequestSpecification res=given().spec(req)
                .header("x-api-version","1.0")
                .body(del);
        res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }*/


/*
    @Test(priority = 17)
    public void testswitch_save(){
       String livePayload="{\n" +
               "  \"goalName\": \"sathish D Portfolio\",\n" +
               "  \"toSchemeName\": \"Mirae Asset Emerging Bluechip-Reg(IDCW)\",\n" +
               "  \"fromSchemeName\": \"Mirae Asset Emerging Bluechip-Reg(G)\",\n" +
               "  \"units\": 1,\n" +
               "  \"fromOption\": \"Growth\",\n" +
               "  \"switchType\": \"regular\",\n" +
               "  \"switchMode\": \"partial\",\n" +
               "  \"otpReferenceId\": \"\",\n" +
               "  \"holdingProfileId\": \"1403821\",\n" +
               "  \"folio\": \"79958299852\",\n" +
               "  \"goalId\": \"2932872\",\n" +
               "  \"fromSchemeCode\": \"9767\",\n" +
               "  \"toSchemeCode\": \"9768\",\n" +
               "  \"toDividendOption\": \"Payout\",\n" +
               "  \"toOption\": \"Dividend\",\n" +
               "  \"bankId\": \"1\"\n" +
               "}";
        RequestSpecification redeem = given()
                .header("x-api-version","2.0")
                .spec(req).body(livePayload);
        redeem.when().post("/core/investor/save").then().log().all().spec(respec);
    }*/

}
