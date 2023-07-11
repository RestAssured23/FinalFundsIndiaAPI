package org.example.apicollection;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.data.Login;
import org.example.dbconnection.dbo;
import org.example.model.HoldingProfile;
import org.example.model.InvestedScheme;
import org.example.model.QuestionnaireResponse;
import org.example.model.RecentTransaction;
import org.example.model.otp.CommonOTP;
import org.example.model.otp.VerifyOtpRequest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Redeem {
    RequestSpecification req = new RequestSpecBuilder()
                       .setBaseUri(Login.URI())
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "12")
            .addHeader("x-fi-access-token", Login.Regression())
            .setContentType(ContentType.JSON).build();
    ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    public Redeem() throws IOException {
    }
    String Holdingid, InvestorId, folio, otp_refid, dbotp, DB_refid, qref_id, RT_refno;
    String goalid, goalname, schemcode, schemename, unintsformat, dividendoption, option, bankid, minunitformat, minuamountformat;
    double Total_units, minunit, minamount;

    @Test(priority = 2)
    public void Holding_Profile() {
        RequestSpecification res = given().log().all().spec(req);
        HoldingProfile.Root hold_response = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);
        int size = hold_response.getData().size();  // Data Size
        for (int i = 0; i < size; i++) {
            if(hold_response.getData().get(i).getHoldingProfileId().equalsIgnoreCase(Login.HoldID)){
                Holdingid = hold_response.getData().get(i).getHoldingProfileId();
                System.out.println("Holding ID :" + Holdingid);
                for(int j=0;j<hold_response.getData().get(i).getInvestors().size();j++){
                    InvestorId = hold_response.getData().get(i).getInvestors().get(j).getInvestorId();
                    System.out.println("Investor ID : " + InvestorId);
                }
            }
            else{
                System.out.println("HoldingProfile Not Matched");
            }
        }
    }
    @Test(priority = 3)
    public void InvestedSchem_API() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);
        int count = response.getData().size();

        for (int i = 0; i < count; i++) {
            if (response.getData().get(i).getFolio().equalsIgnoreCase(Login.Redeem_Folio)) {
                System.out.println(response.getData().get(i).getSchemeName());
                folio = response.getData().get(i).getFolio();
                goalid = response.getData().get(i).getGoalId();
                goalname = response.getData().get(i).getGoalName();
                schemcode = response.getData().get(i).getSchemeCode();
                schemename = response.getData().get(i).getSchemeName();
                Total_units = response.getData().get(i).getUnits();
                unintsformat = response.getData().get(i).getUnitsFormatted();
                dividendoption = response.getData().get(i).getDividendOption();
                option = response.getData().get(i).getOption();
                bankid = response.getData().get(i).getBankId();
                minunit = response.getData().get(i).getRedemption().getMinimumUnits();
                minunitformat = response.getData().get(i).getRedemption().getUnitsFormatted();
                minamount = response.getData().get(i).getRedemption().getMinimumAmount();
                minuamountformat = response.getData().get(i).getRedemption().getMinimumAmountFormatted();
                System.out.println(Total_units);
            }
        }
    }
    @Test(priority = 4)
    public void Questinoari_API() {
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
    public void DB_Connection() throws SQLException {
        Statement s1 = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            dbo ds = new dbo();
            con = ds.getConnection();
            s1 = con.createStatement();
            rs = s1.executeQuery("select * from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otp_refid + "'");
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
        Map<String, Object> Redeem_Units = new HashMap<>();
        Redeem_Units.put("holdingProfileId", Holdingid);
        Redeem_Units.put("folio", folio);
        Redeem_Units.put("goalId", goalid);
        Redeem_Units.put("goalName", goalname);
        Redeem_Units.put("schemeCode", schemcode);
        Redeem_Units.put("schemeName", schemename);
        Redeem_Units.put("units", Integer.parseInt(Login.Redeem_units));
        Redeem_Units.put("unitsFormatted", unintsformat);
        Redeem_Units.put("redemptionMode", "partial");            // full / partial
        Redeem_Units.put("dividendOption", dividendoption);
        Redeem_Units.put("option", option);
        Redeem_Units.put("bankId", bankid);
        Redeem_Units.put("redemptionType", "regular");
        Redeem_Units.put("otpReferenceId", DB_refid);
        Redeem_Units.put("questionnaireReferenceId", qref_id);

        Map<String, Object> Redeem_Amount = new HashMap<>();
        Redeem_Amount.put("holdingProfileId", Holdingid);
        Redeem_Amount.put("folio", folio);
        Redeem_Amount.put("goalId", goalid);
        Redeem_Amount.put("goalName", goalname);
        Redeem_Amount.put("schemeCode", schemcode);
        Redeem_Amount.put("schemeName", schemename);
        Redeem_Amount.put("amount",Integer.parseInt(Login.Redeem_amt));
        Redeem_Amount.put("unitsFormatted", unintsformat);
        Redeem_Amount.put("redemptionMode", "partial");            // full / partial
        Redeem_Amount.put("dividendOption", dividendoption);
        Redeem_Amount.put("option", option);
        Redeem_Amount.put("bankId", bankid);
        Redeem_Amount.put("redemptionType", "regular");
        Redeem_Amount.put("otpReferenceId", DB_refid);
        Redeem_Amount.put("questionnaireReferenceId", qref_id);

        Map<String, Object> Redeem_All = new HashMap<>();
        Redeem_All.put("holdingProfileId", Holdingid);
        Redeem_All.put("folio", folio);
        Redeem_All.put("goalId", goalid);
        Redeem_All.put("goalName", goalname);
        Redeem_All.put("schemeCode", schemcode);
        Redeem_All.put("schemeName", schemename);
        Redeem_All.put("units", Total_units);
        Redeem_All.put("unitsFormatted", unintsformat);
        Redeem_All.put("redemptionMode", "full");            // full / partial
        Redeem_All.put("dividendOption", dividendoption);
        Redeem_All.put("option", option);
        Redeem_All.put("bankId", bankid);
        Redeem_All.put("redemptionType", "regular");
        Redeem_All.put("otpReferenceId", DB_refid);
        Redeem_All.put("questionnaireReferenceId", qref_id);

        if (Login.Redeem_units.equalsIgnoreCase("0") && Login.Redeem_amt.equalsIgnoreCase("0")) {
            RequestSpecification redeem = given().log().all().spec(req)
                    .body(Redeem_All);
            redeem.when().post("/core/investor/redeem")
                    .then().log().all().spec(respec);
        } else if (Login.Redeem_units.equalsIgnoreCase("0")) {
            RequestSpecification redeem = given().log().all().spec(req)
                    .body(Redeem_Amount);
            redeem.when().post("/core/investor/redeem")
                    .then().log().all().spec(respec);
        } else {
            RequestSpecification redeem = given().log().all().spec(req)
                    .body(Redeem_Units);
            redeem.when().post("/core/investor/redeem")
                    .then().log().all().spec(respec);
        }
    }
    @Test(priority = 9)
    public void Recent_Transaction() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", "183318")
                .queryParam("page", "1")
                .queryParam("size", "10");
        RecentTransaction.Root response = res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().as(RecentTransaction.Root.class);
        int count = response.getData().size();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < response.getData().get(i).getMf().size(); j++) {
                for (int k = 0; k < response.getData().get(i).getMf().get(j).getActions().size(); k++) {
                    if (response.getData().get(i).getMf().get(j).getFolio().equalsIgnoreCase(Login.FolioID) ==
                            (response.getData().get(i).getMf().get(j).getActions().get(k).equalsIgnoreCase("cancel"))) {
                        RT_refno = response.getData().get(i).getMf().get(j).getReferenceNo();
                        System.out.println("Cancelled ReferenceNo"+RT_refno);
                    }
                }
            }
        }
    }
    @Test(priority = 10)
    public void Cancel_Redeem() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", RT_refno);

        RequestSpecification can = given().log().all().spec(req).body(del);
        can.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }
}
