package coreapi.testapi;

import coreapi.basepath.AccessPropertyFile;
import coreapi.model.otp.CommonOTP;
import coreapi.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import coreapi.dbconnection.dbo;
import coreapi.model.HoldingProfile;
import coreapi.model.InvestedScheme;
import coreapi.model.QuestionnaireResponse;
import coreapi.model.RecentTransaction;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Redeem extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;

    public Redeem() throws IOException {
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
    String Holdingid, response,folio, otp_refid, dbotp, DB_refid, qref_id, firstReferenceNo;
    String goalid, goalname, schemcode, schemename, unintsformat, dividendoption, option, bankid, minunitformat, minuamountformat;
    double Total_units, minunit, minamount,available_units;

    @Test(priority = 2)
    public void Holding_Profile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            if (data.getHoldingProfileId().equalsIgnoreCase(holdingid_pro)) {
                Holdingid = data.getHoldingProfileId();
                System.out.println("Holding ID is matched with the property file: " + Holdingid);
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            Assert.fail("Holding ID is not matched with Investor. Stopping the test.");
        }
    }
    @Test(priority = 3)
    public void InvestedSchemeAPI() {
            RequestSpecification res = given().log().all().spec(req)
                    .queryParam("holdingProfileId", Holdingid);
            InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                    .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

            for (InvestedScheme.Datum data : response.getData()) {
                if (data.getFolio().equalsIgnoreCase(folio_pro)) {
                    System.out.println(data.getSchemeName());
                    folio = data.getFolio();
                    goalid = data.getGoalId();
                    goalname = data.getGoalName();
                    schemcode = data.getSchemeCode();
                    schemename = data.getSchemeName();
                    Total_units = data.getUnits();
                    unintsformat = data.getUnitsFormatted();
                    dividendoption = data.getDividendOption();
                    option = data.getOption();
                    bankid = data.getBankId();
                    minunit = data.getRedemption().getMinimumUnits();
                    minunitformat = data.getRedemption().getUnitsFormatted();
                    minamount = data.getRedemption().getMinimumAmount();
                    minuamountformat = data.getRedemption().getMinimumAmountFormatted();
                    available_units = data.getRedemption().getUnits();
                }
            }
        }

    @Test(priority = 4)
    public void QuestionariAPI() {
        RequestSpecification res = given().log().all().spec(req)
                .body(Payload.questionnaire());
        QuestionnaireResponse.Root quesresponse = res.when().post("/core/questionnaire")
                .then().spec(respec).extract().response().as(QuestionnaireResponse.Root.class);
        qref_id = quesresponse.getData().getReferenceId();
        System.out.println(qref_id);
    }
    @Test(priority = 5)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId",folio);
        otppayload.put("workflow", "redemption");

        RequestSpecification commonotp = given().log().all().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otp_refid = responce.getData().getOtpReferenceId();
    }
    @Test(priority = 6)
    public void DB_Connection() {
        try {
            dbo ds = new dbo();
            Connection con = ds.getConnection();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery("select * from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otp_refid + "'");

            if (rs.next()) {
                dbotp = rs.getString("otp");
                DB_refid = rs.getString("referenceid");
                System.out.println("OTP :" + dbotp);
                System.out.println("OTPReferenceID :" + DB_refid);
            }
            rs.close();
            s1.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    @Test(priority = 7)
    public void Verify_OTP() {
        VerifyOtpRequest.Root payload = new VerifyOtpRequest.Root();
        VerifyOtpRequest.Otp otp = new VerifyOtpRequest.Otp();
        otp.setSms("");
        otp.setEmail("");
        otp.setEmail_or_sms(dbotp);
        payload.setOtp(otp);
        payload.setOtpReferenceId(DB_refid);

        RequestSpecification res1 = given().log().all().spec(req)
                .body(payload);
        res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }

    @Test(priority = 8)
    public void Redeem_API() {
        Map<String, Object> redeemParams = new HashMap<>();
        redeemParams.put("holdingProfileId", Holdingid);
        redeemParams.put("folio", folio);
        redeemParams.put("goalId", goalid);
        redeemParams.put("goalName", goalname);
        redeemParams.put("schemeCode", schemcode);
        redeemParams.put("schemeName", schemename);
        redeemParams.put("dividendOption", dividendoption);
        redeemParams.put("option", option);
        redeemParams.put("bankId", bankid);
        redeemParams.put("redemptionType", "regular");
        redeemParams.put("otpReferenceId", DB_refid);
        redeemParams.put("questionnaireReferenceId", qref_id);

        if (units_pro.equalsIgnoreCase("0") && amount_pro.equalsIgnoreCase("0")) {
            redeemParams.put("units", available_units);
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

        RequestSpecification redeem = given().log().all().spec(req)
                .body(redeemParams);
        redeem.when().post("/core/investor/redeem").then().log().all().spec(respec);
    }

    @Test(priority = 9)
    public void Recent_Transaction() {
        RequestSpecification res = given().log().all().spec(req)
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
    }
    @Test(priority = 10)
    public void Cancel_Redeem() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", firstReferenceNo);

        RequestSpecification can = given().log().all().spec(req).body(del);
        can.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }
}
