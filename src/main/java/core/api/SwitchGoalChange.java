package core.api;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DBconnection;
import core.dbconnection.DatabaseConnection;
import core.model.HoldingProfile;
import core.model.InvestedScheme;
import core.model.MFscheme;
import core.model.RecentTransaction;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
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

public class SwitchGoalChange extends AccessPropertyFile {

   // String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MTQwNDciLCJzY29wZXMiOiJyZWFkLHdyaXRlIiwibmFtZSI6Ik5vbWluZWUgVGVzdCIsImVtYWlsIjoibm9taW5lQGdtYWlsLmNvbSIsIm1vYmlsZSI6Ijk4NDAwNDI0OTIiLCJtYW5hZ2VtZW50LXVzZXItaWQiOjM0ODYsIm1hbmFnZW1lbnQtdXNlci1yb2xlcyI6ImFkbWluIiwiaXNzIjoiZnVuZHNpbmRpYS5jb20iLCJqdGkiOiIxZTk2OTRjOC1iYjA2LTQ0OTQtODQ2NC0wYjJlZjE2OTdiOTgiLCJpYXQiOjE3MDAxOTY4MzMsImV4cCI6MTcwMDE5ODA5M30.kMySxWY_sFgKvf3XpaZv8HfaBC4eNa-ACmrGVPOumIjMJ-JwiFbQ-mWaeut96nq0vofwz10h6T4kBR8k35JuAA";




    String dbotp,DB_refid;

    public SwitchGoalChange() {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
           //    .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", getChannelID())
              //  .addHeader("x-fi-access-token", token)
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
        otppayload.put("referenceId","343423/434");
        otppayload.put("workflow", "switch");
        RequestSpecification commonotp = given().spec(req)
                .header("x-api-version","1.0")
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }
    @Test(priority = 15)
    public void DB_Connection() throws SQLException {
        Statement s1 = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            DBconnection ds = new DBconnection();
            con = ds.getConnection();
            s1 = con.createStatement();
            rs = s1.executeQuery("select * from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otpRefID + "'");
            rs.next();
            dbotp = rs.getString("otp");
            DB_refid = rs.getString("referenceid");
            System.out.println("OTP :" + dbotp);
            System.out.println("OTPReferenceID :" + DB_refid);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (s1 != null) s1.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }
    @Test(priority = 16)
    public void Verify_OTP() {
        VerifyOtpRequest.Root payload = new VerifyOtpRequest.Root();
        VerifyOtpRequest.Otp otp = new VerifyOtpRequest.Otp();
        otp.setSms("");
        otp.setEmail("");
        otp.setEmail_or_sms(dbotp);
        payload.setOtp(otp);
        payload.setOtpReferenceId(DB_refid);
        RequestSpecification res1 = given().spec(req)
                .header("x-api-version","1.0")
                .body(payload);
        res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }
    @Test(priority = 17)

    public void testswitch(){
        String payload2="{\n" +
                "  \"goalName\": \" Emergency portfolio\",\n" +
                "  \"toSchemeName\": \"Bandhan CRISIL IBX Gilt April 2028 Index Fund-Reg(IDCW)\",\n" +
                "  \"fromSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(IDCW)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromDividendOption\": \"Payout\",\n" +
                "  \"fromOption\": \"Dividend\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \""+DB_refid+"\",\n" +
                "  \"holdingProfileId\": \"183318\",\n" +
                "  \"folio\": \"343423/434\",\n" +
                "  \"goalId\": \"459101\",\n" +
                "  \"toGoalId\": \"461041\",\n" +
                "  \"fromSchemeCode\": \"20748\",\n" +
                "  \"toSchemeCode\": \"45423\",\n" +
                "  \"toDividendOption\": \"Payout\",\n" +
                "  \"toOption\": \"Dividend\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";
        String payload="{\n" +
                "  \"goalName\": \"SwitchTest\",\n" +
                "  \"toSchemeName\": \"HDFC Corp Bond Fund(IDCW)\",\n" +
                "  \"fromSchemeName\": \"HDFC Banking and PSU Debt Fund-Reg(G)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromOption\": \"Growth\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \""+DB_refid+"\",\n" +
                "  \"holdingProfileId\": \"181557\",\n" +
                "  \"folio\": \"1234/462\",\n" +
                "  \"goalId\": \"455936\",\n" +
                "  \"toGoalId\": \"459901\",\n" +                       //456053
                "  \"fromSchemeCode\": \"2271\",\n" +                       //2271   HDFC Large and Mid Cap Fund-Reg(G)
                "  \"toSchemeCode\": \"7578\",\n" +                         //1304   HDFC Mid-Cap Opportunities Fund(IDCW)
                "  \"toDividendOption\": \"Payout\",\n" +                   //7578   HDFC Large and Mid Cap Fund-Reg(IDCW)
                "  \"toOption\": \"Dividend\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";
        RequestSpecification redeem = given().spec(req)
              //  .addHeader("x-api-version", "1.0")
                .header("x-api-version","2.0")
                .body(payload2);
        redeem.when().post("/core/investor/switch").then().log().all().spec(respec);
    }
//
    @Test(priority = 18)
    public void recentTransactions() {
        RequestSpecification res = given()
                .header("x-api-version","1.0")
                .spec(req)
                .queryParam("holdingProfileId", "183318");
        response=res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 19)
    public void Cancel_Switch() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
     //   del.put("referenceNo", "1382766");

        RequestSpecification res=given().spec(req)
                .header("x-api-version","1.0")
                .body(del);
        res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }



/*
    @Test(priority = 17)
    public void testswitch_save(){
        String payload="{\n" +
                "    \"goalName\": \"SwitchTest\",\n" +
                "    \"toSchemeName\": \"HDFC Corp Bond Fund(IDCW)\",\n" +
                "    \"fromSchemeName\": \"HDFC Banking and PSU Debt Fund-Reg(G)\",\n" +
                "    \"units\": 1,\n" +
                "    \"fromOption\": \"Growth\",\n" +
                "    \"switchType\": \"regular\",\n" +
                "    \"switchMode\": \"partial\",\n" +
                "    \"otpReferenceId\": \"null\",\n" +
                "    \"holdingProfileId\": \"181557\",\n" +
                "    \"folio\": \"1234/462\",\n" +
                "    \"goalId\": \"455936\",\n" +
                "    \"toGoalId\": \"459901\",\n" +
                "    \"fromSchemeCode\": \"2271\",\n" +
                "    \"toSchemeCode\": \"7578\",\n" +
                "    \"toDividendOption\": \"Payout\",\n" +
                "    \"toOption\": \"Dividend\",\n" +
                "    \"bankId\": \"1\"\n" +
                "}";
        RequestSpecification redeem = given()
                .header("x-api-version","1.0")
                .spec(req).body(payload);
        redeem.when().post("/core/investor/save").then().log().all().spec(respec);
    }
*/

 /*   @Test(priority = 14)
    public void Common_OTP_Test() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","94764646/46");
        otppayload.put("workflow", "switch");

        RequestSpecification commonotp = given().spec(req).body(otppayload);
        CommonOTP.Root response = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);

        otpRefID = response.getData().getOtpReferenceId();

        // Extract request details
        RequestSpecification requestSpec = given().spec(req);
        String requestBody = otppayload.toString();

        // Build the curl command
        StringBuilder curlCommand = new StringBuilder("curl -X POST \"https://testapi.fundsindia.com/core/investor/common/otp\"");

        curlCommand.append(" -H \"x-fi-access-token: ").append(getAccessToken()).append("\"");
        curlCommand.append(" -H \"x-api-version: ").append("2.0").append("\"");

        // Add headers to the curl command
        for (Header header : requestSpec.get().getHeaders()) {

            curlCommand.append(" -H \"").append(header.getName()).append(": ").append(header.getValue()).append("\"");

        }

        // Add request body to the curl command
        curlCommand.append(" -d '").append(requestBody).append("'");

        // Print or use the curl command as needed
        System.out.println(curlCommand.toString());
    }
*/

}
