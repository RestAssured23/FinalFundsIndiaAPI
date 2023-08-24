package core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.basepath.AccessPropertyFile;
import core.dbconnection.DatabaseConnection;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import core.model.HoldingProfile;
import core.model.InvestedScheme;
import core.model.QuestionnaireResponse;
import core.model.RecentTransaction;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import static core.api.CommonVariable.*;

import static io.restassured.RestAssured.given;

public class Redeem extends AccessPropertyFile {

    public Redeem() {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "2.0")
                .addHeader("channel-id", getChannelID())
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

    @Test(priority = 2)
    public void Holding_Profile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            if (data.getHoldingProfileId().equalsIgnoreCase(holdingid_pro)) {
                holdingId = data.getHoldingProfileId();
                System.out.println("Holding ID is matched with the property file: " + holdingId);
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            Assert.fail("Holding ID is not matched with Investor. Stopping the test.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String holdResponseJson;
        try {
            holdResponseJson = objectMapper.writeValueAsString(holdResponse);
        } catch (Exception e) {
            holdResponseJson = "Failed to convert to JSON: " + e.getMessage();
        }
        Reporter.log(holdResponseJson);
    }
    @Test(priority = 3)
    public void InvestedSchemeAPI() {
            RequestSpecification res = given().spec(req)
                    .queryParam("holdingProfileId", holdingId);
            InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                    .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

            for (InvestedScheme.Datum data : response.getData()) {
                if (data.getFolio().equalsIgnoreCase(folio_pro)) {
                    System.out.println(data.getSchemeName());
                    folio = data.getFolio();
                    goalId = data.getGoalId();
                    goalName = data.getGoalName();
                    schemeCode = data.getSchemeCode();
                    schemeName = data.getSchemeName();
                    totalUnits = data.getUnits();
                    unintsformat = data.getUnitsFormatted();
                    dividendoption = data.getDividendOption();
                    option = data.getOption();
                    bankId = data.getBankId();
                    minUnit = data.getRedemption().getMinimumUnits();
                    minunitformat = data.getRedemption().getUnitsFormatted();
                    minAmt = data.getRedemption().getMinimumAmount();
                    minuamountformat = data.getRedemption().getMinimumAmountFormatted();
                    availableUnits = data.getRedemption().getUnits();
                    System.out.println(folio);
                }
            }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String holdResponseJson;
        try {
            holdResponseJson = objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            holdResponseJson = "Failed to convert to JSON: " + e.getMessage();
        }
        Reporter.log(holdResponseJson);
        }

    @Test(priority = 4)
    public void QuestionariAPI() {
        RequestSpecification res = given().spec(req)
                .body(Payload.questionnaire());
        QuestionnaireResponse.Root quesresponse = res.when().post("/core/questionnaire")
                .then().log().all().spec(respec).extract().response().as(QuestionnaireResponse.Root.class);
        qrefId = quesresponse.getData().getReferenceId();
        System.out.println(qrefId);
        // To print the log in html file
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String holdResponseJson;
        try {
            holdResponseJson = objectMapper.writeValueAsString(quesresponse);
        } catch (Exception e) {
            holdResponseJson = "Failed to convert to JSON: " + e.getMessage();
        }
        Reporter.log(holdResponseJson);
    }

    @Test(priority = 5)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId",folio);
        otppayload.put("workflow", "redemption");

        RequestSpecification commonotp = given().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();

        // To print the log in html file
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String holdResponseJson;
        try {
            holdResponseJson = objectMapper.writeValueAsString(responce);
        } catch (Exception e) {
            holdResponseJson = "Failed to convert to JSON: " + e.getMessage();
        }
        Reporter.log(holdResponseJson);
    }
    @Test(priority = 6)
    public void DB_Connection() throws SQLException {
        System.out.println("DB Connection Name: "+dbusr);
        Statement s1 = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            DatabaseConnection ds = new DatabaseConnection(dbusr, dbpwd, dburl, databasename, true, dbdrivername);
            con = ds.getConnection();
            Assert.assertNotNull(con, "Database connection failed!"); // Throw an error if the connection is null (failed)
            s1 = con.createStatement();
            rs = s1.executeQuery("select * from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otpRefID + "'");
            rs.next();
            dbOtp = rs.getString("otp");
            dbRefId = rs.getString("referenceid");
            System.out.println("OTP :" + dbOtp);
            System.out.println("OTPReferenceID :" + dbRefId);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (s1 != null) s1.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }
    @Test(priority = 7)
    public void Verify_OTP() {
        VerifyOtpRequest.Root payload = new VerifyOtpRequest.Root();
        VerifyOtpRequest.Otp otp = new VerifyOtpRequest.Otp();
        otp.setSms("");
        otp.setEmail("");
        otp.setEmail_or_sms(dbOtp);
        payload.setOtp(otp);
        payload.setOtpReferenceId(dbRefId);

        RequestSpecification res1 = given().spec(req)
                .body(payload);
        logResponse =res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec).extract().asString();
        // To print the log in html file
        Reporter.log(logResponse);
    }
    @Test(priority = 8)
    public void Redeem_API() {
        Map<String, Object> redeemParams = new HashMap<>();
        redeemParams.put("holdingProfileId", holdingId);
        redeemParams.put("folio", folio);
        redeemParams.put("goalId", goalId);
        redeemParams.put("goalName", goalName);
        redeemParams.put("schemeCode", schemeCode);
        redeemParams.put("schemeName", schemeName);
        redeemParams.put("dividendOption", dividendoption);
        redeemParams.put("option", option);
        redeemParams.put("bankId", bankId);
        redeemParams.put("redemptionType", "regular");
        redeemParams.put("otpReferenceId", dbRefId);
        redeemParams.put("questionnaireReferenceId", qrefId);

        if (units_pro.equalsIgnoreCase("0") && amount_pro.equalsIgnoreCase("0")) {
            redeemParams.put("units", availableUnits);
            redeemParams.put("unitsFormatted", unintsformat);
            redeemParams.put("redemptionMode", "full");                         //full / partial
        } else if (units_pro.equalsIgnoreCase("0")) {
            redeemParams.put("amount", Integer.parseInt(amount_pro));
            redeemParams.put("unitsFormatted", unintsformat);
            redeemParams.put("redemptionMode", "partial");
        } else {
            redeemParams.put("units", Integer.parseInt(units_pro));
            redeemParams.put("unitsFormatted", unintsformat);
            redeemParams.put("redemptionMode", "partial");
        }

        RequestSpecification redeem = given().spec(req)
                .body(redeemParams);
       logResponse = redeem.when().post("/core/investor/redeem").then().log().all().spec(respec).extract().response().asString();
        Reporter.log(logResponse);
    }
    @Test(priority = 9)
    public void Recent_Transaction() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "183318")
                .queryParam("page", "1")
                .queryParam("size", "10");
        RecentTransaction.Root response = res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().as(RecentTransaction.Root.class);

         firstReferenceNo = null;
        for (RecentTransaction.Datum data : response.getData()) {
            for (RecentTransaction.Mf mf : data.getMf()) {
                for (String action : mf.getActions()) {
                    boolean isCancelled = mf.getFolio().equalsIgnoreCase(folio_pro) &&
                            action.equalsIgnoreCase("cancel");
                    if (isCancelled) {
                        firstReferenceNo = mf.getReferenceNo();  // Update the first reference number
                        break;                      // Break out of the loop once the first reference number is found
                    }
                }
                if (firstReferenceNo != null) {
                    break;      // Break out of the loop once the first reference number is found
                }
            }
            if (firstReferenceNo != null) {
                break;              // Break out of the loop once the first reference number is found
            }
        }
        if (firstReferenceNo != null) {
            System.out.println("First Cancelled ReferenceNo: " + firstReferenceNo);
        }

        // To print the log in html file
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String holdResponseJson;
        try {
            holdResponseJson = objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            holdResponseJson = "Failed to convert to JSON: " + e.getMessage();
        }
        Reporter.log(holdResponseJson);

    }
    @Test(priority = 10)
    public void Cancel_Redeem() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", firstReferenceNo);

        RequestSpecification res = given().spec(req).body(del);
      logResponse =  res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(logResponse);
    }
}
