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
import org.example.model.MFscheme;
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

public class Switch {
    RequestSpecification req =new RequestSpecBuilder()
            .setBaseUri(Login.URI())
            .addHeader("x-api-version","2.0")
            .addHeader("channel-id","10")
            .addHeader("x-fi-access-token", Login.Regression())
            .setContentType(ContentType.JSON).build();
    ResponseSpecification respec =new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid, InvestorId, folio, otp_refid, dbotp, DB_refid,  RT_refno,Source_SchemeName;
    String goalid, goalname, bankid;
    double minamount, units, minunit, currentamount,Total_units;
    String fromschemename, fromschemecode, fromoption,   toschemename, toschemcode, tooption,AMC_Name, AMC_Code;
    public Switch() throws IOException {
    }

    @Test(priority = 11)
    public void Holding_Profile() {
        RequestSpecification res = given().log().all().spec(req);
        HoldingProfile.Root hold_response = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);
        int size = hold_response.getData().size();  // Data Size
        for (int i = 0; i < size; i++) {
            if (hold_response.getData().get(i).getHoldingProfileId().equalsIgnoreCase(Login.HoldID)) {
                Holdingid = hold_response.getData().get(i).getHoldingProfileId();
                System.out.println("Holding ID :" + Holdingid);
                for (int j = 0; j < hold_response.getData().get(i).getInvestors().size(); j++) {
                    InvestorId = hold_response.getData().get(i).getInvestors().get(j).getInvestorId();
                    System.out.println("Investor ID : " + InvestorId);
                }
            }
        }
    }
    @Test(priority = 12)
    public void InvestedSchem_API() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);
        int count = response.getData().size();

        for (int i = 0; i < count; i++) {
            if (response.getData().get(i).getFolio().equalsIgnoreCase(Login.Switch_Folio)) {
                fromschemename = response.getData().get(i).getSchemeName();
                fromschemecode = response.getData().get(i).getSchemeCode();
                folio = response.getData().get(i).getFolio();
                units = response.getData().get(i).getUnits();
                fromoption = response.getData().get(i).getOption();
                goalid = response.getData().get(i).getGoalId();
                bankid = response.getData().get(i).getBankId();
                minamount = response.getData().get(i).getSwitchOut().getMinimumAmount();
                minunit = response.getData().get(i).getSwitchOut().getMinimumUnits();
                currentamount = response.getData().get(i).getCurrentAmount();
                goalname = response.getData().get(i).getGoalName();
                Total_units = response.getData().get(i).getUnits();
                System.out.println(fromschemename);
                System.out.println(units);
            }
        }
    }
    @Test(priority = 13)
    public void product_search_mf_form() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("page", 1)
                .queryParam("size", 100)
                .queryParam("schemeCodes", fromschemecode);
        MFscheme.Root response = res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);
        for (int i = 0; i < response.getData().getContent().size(); i++) {
            AMC_Name = response.getData().getContent().get(i).getAmc();
            AMC_Code = response.getData().getContent().get(i).getAmcCode();
            Source_SchemeName=response.getData().getContent().get(i).getName();
            System.out.printf(AMC_Code+"\t"+AMC_Name+"\t"+Source_SchemeName);

        }
    }

    @Test(priority = 14)
    public void TargetScheme_Search_Test() {
        RequestSpecification res = given().log().all().spec(req)
                .body("{\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 10,\n" +
                        "  \"orderBy\": \"rating\",\n" +
                        "  \"orderType\": \"DESC\",\n" +
                        "  \"categories\": [],\n" +
                        "  \"subCategories\": [],\n" +
                        "  \"query\": \"\",\n" +
                        "  \"risk\": [],\n" +
                        "  \"ratings\": [],\n" +
                        "  \"amcs\": [\n" +
                        "    {\n" +
                        "      \"name\": \"" + AMC_Name + "\",\n" +
                        "      \"value\": \"" + AMC_Code + "\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"searchCode\": [\n" +
                        "    {\n" +
                        "      \"value\": \"\",\n" +
                        "      \"sort\": true\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        MFscheme.Root response = res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);
        int size = response.getData().getContent().size();
        if (Login.Switch_TargetScheme.equalsIgnoreCase("0")) {
            for (int i = 0; i <=2; i++) {
                toschemename = response.getData().getContent().get(i).getName();
                toschemcode = response.getData().getContent().get(i).getSchemeCode();
                tooption = response.getData().getContent().get(i).getOption();
                System.out.println("To SchemeName: " + toschemename);
                System.out.println("To schemecode: " + toschemcode);
                System.out.println("To Option: " + tooption);
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (response.getData().getContent().get(i).getName().equalsIgnoreCase(Login.Switch_TargetScheme)) {
                    toschemename = response.getData().getContent().get(i).getName();
                    toschemcode = response.getData().getContent().get(i).getSchemeCode();
                    tooption = response.getData().getContent().get(i).getOption();
                    System.out.println("To SchemeName: " + toschemename);
                    System.out.println("To schemecode: " + toschemcode);
                    System.out.println("To Option: " + tooption);
                }
            }
        }
    }


    @Test(priority = 15)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId",folio);
        otppayload.put("workflow", "switch");

        RequestSpecification commonotp = given().log().all().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otp_refid = responce.getData().getOtpReferenceId();
    }

    @Test(priority = 16)
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

    @Test(priority = 17)
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

    @Test(priority = 18)
    public void Switch_API() {
        Map<String, Object> Growth_Growth_Unit = new HashMap<>();
        Growth_Growth_Unit.put("holdingProfileId", Holdingid);
        Growth_Growth_Unit.put("folio", folio);
        Growth_Growth_Unit.put("goalId", goalid);
        Growth_Growth_Unit.put("goalName", goalname);
        Growth_Growth_Unit.put("fromSchemeName", fromschemename);
        Growth_Growth_Unit.put("fromSchemeCode", fromschemecode);
        Growth_Growth_Unit.put("toSchemeName", toschemename);
        Growth_Growth_Unit.put("toSchemeCode", toschemcode);
        Growth_Growth_Unit.put("units", Login.Switch_Units);
        Growth_Growth_Unit.put("fromOption", fromoption);
        Growth_Growth_Unit.put("toOption", tooption);
        Growth_Growth_Unit.put("switchMode", "partial");
        Growth_Growth_Unit.put("switchType", "regular");
        Growth_Growth_Unit.put("bankId", bankid);
        Growth_Growth_Unit.put("otpReferenceId", DB_refid);

        Map<String, Object> Growth_Growth_Amount = new HashMap<>();
        Growth_Growth_Amount.put("holdingProfileId", Holdingid);
        Growth_Growth_Amount.put("folio", folio);
        Growth_Growth_Amount.put("goalId", goalid);
        Growth_Growth_Amount.put("goalName", goalname);
        Growth_Growth_Amount.put("fromSchemeName", fromschemename);
        Growth_Growth_Amount.put("fromSchemeCode", fromschemecode);
        Growth_Growth_Amount.put("toSchemeName", toschemename);
        Growth_Growth_Amount.put("toSchemeCode", toschemcode);
        Growth_Growth_Unit.put("amount", Login.Switch_Amt);
        Growth_Growth_Amount.put("fromOption", fromoption);
        Growth_Growth_Amount.put("toOption", tooption);
        Growth_Growth_Amount.put("switchMode", "partial");
        Growth_Growth_Amount.put("switchType", "regular");
        Growth_Growth_Amount.put("bankId", bankid);
        Growth_Growth_Amount.put("otpReferenceId", DB_refid);

        Map<String, Object> Growth_Growth_All = new HashMap<>();
        Growth_Growth_All.put("holdingProfileId", Holdingid);
        Growth_Growth_All.put("folio", folio);
        Growth_Growth_All.put("goalId", goalid);
        Growth_Growth_All.put("goalName", goalname);
        Growth_Growth_All.put("fromSchemeName", fromschemename);
        Growth_Growth_All.put("fromSchemeCode", fromschemecode);
        Growth_Growth_All.put("toSchemeName", toschemename);
        Growth_Growth_All.put("toSchemeCode", toschemcode);
        Growth_Growth_Unit.put("units", Total_units);
        Growth_Growth_All.put("fromOption", fromoption);
        Growth_Growth_All.put("toOption", tooption);
        Growth_Growth_All.put("switchMode", "full");
        Growth_Growth_All.put("switchType", "regular");
        Growth_Growth_All.put("bankId", bankid);
        Growth_Growth_All.put("otpReferenceId", DB_refid);

        Map<String, Object> Growth_Div_Unit = new HashMap<>();
        Growth_Div_Unit.put("holdingProfileId", Holdingid);
        Growth_Div_Unit.put("folio", folio);
        Growth_Div_Unit.put("goalId", goalid);
        Growth_Div_Unit.put("goalName", goalname);
        Growth_Div_Unit.put("fromSchemeName", fromschemename);
        Growth_Div_Unit.put("fromSchemeCode", fromschemecode);
        Growth_Div_Unit.put("toSchemeName", toschemename);
        Growth_Div_Unit.put("toSchemeCode", toschemcode);
        Growth_Growth_All.put("units", Login.Switch_Units);
        Growth_Div_Unit.put("fromOption", fromoption);
        Growth_Div_Unit.put("toDividendOption", "Payout");       // Payout / Reinvestment
        Growth_Div_Unit.put("toOption", tooption);
        Growth_Div_Unit.put("switchMode", "partial");              //partial or all
        Growth_Div_Unit.put("switchType", "regular");
        Growth_Div_Unit.put("bankId", bankid);
        Growth_Div_Unit.put("otpReferenceId", DB_refid);

        Map<String, Object> Growth_Div_Amount = new HashMap<>();
        Growth_Div_Amount.put("holdingProfileId", Holdingid);
        Growth_Div_Amount.put("folio", folio);
        Growth_Div_Amount.put("goalId", goalid);
        Growth_Div_Amount.put("goalName", goalname);
        Growth_Div_Amount.put("fromSchemeName", fromschemename);
        Growth_Div_Amount.put("fromSchemeCode", fromschemecode);
        Growth_Div_Amount.put("toSchemeName", toschemename);
        Growth_Div_Amount.put("toSchemeCode", toschemcode);
        Growth_Div_Amount.put("amount", Login.Switch_Amt);
        Growth_Div_Amount.put("fromOption", fromoption);
        Growth_Div_Amount.put("toDividendOption", "Payout");       // Payout / Reinvestment
        Growth_Div_Amount.put("toOption", tooption);
        Growth_Div_Amount.put("switchMode", "partial");              //partial or all
        Growth_Div_Amount.put("switchType", "regular");
        Growth_Div_Amount.put("bankId", bankid);
        Growth_Div_Amount.put("otpReferenceId", DB_refid);

        Map<String, Object> Growth_Div_All = new HashMap<>();
        Growth_Div_All.put("holdingProfileId", Holdingid);
        Growth_Div_All.put("folio", folio);
        Growth_Div_All.put("goalId", goalid);
        Growth_Div_All.put("goalName", goalname);
        Growth_Div_All.put("fromSchemeName", fromschemename);
        Growth_Div_All.put("fromSchemeCode", fromschemecode);
        Growth_Div_All.put("toSchemeName", toschemename);
        Growth_Div_All.put("toSchemeCode", toschemcode);
        Growth_Growth_All.put("units", Total_units);
        Growth_Div_All.put("fromOption", fromoption);
        Growth_Div_All.put("toDividendOption", "Payout");       // Payout / Reinvestment
        Growth_Div_All.put("toOption", tooption);
        Growth_Div_All.put("switchMode", "full");              //partial or all
        Growth_Div_All.put("switchType", "regular");
        Growth_Div_All.put("bankId", bankid);
        Growth_Div_All.put("otpReferenceId", DB_refid);


        Map<String, Object> Div_Div_Unit = new HashMap<>();
        Div_Div_Unit.put("holdingProfileId", Holdingid);
        Div_Div_Unit.put("folio", folio);
        Div_Div_Unit.put("goalId", goalid);
        Div_Div_Unit.put("goalName", goalname);
        Div_Div_Unit.put("fromSchemeName", fromschemename);
        Div_Div_Unit.put("fromSchemeCode", fromschemecode);
        Div_Div_Unit.put("toSchemeName", toschemename);
        Div_Div_Unit.put("toSchemeCode", toschemcode);
        Div_Div_Unit.put("units", Login.Switch_Units);
        Div_Div_Unit.put("fromOption", fromoption);
        Div_Div_Unit.put("fromDividendOption", "Payout");
        Div_Div_Unit.put("toOption", tooption);
        Div_Div_Unit.put("toDividendOption", "Reinvestment");       // Payout / Reinvestment
        Div_Div_Unit.put("switchMode", "partial");              //partial or all
        Div_Div_Unit.put("switchType", "regular");
        Div_Div_Unit.put("bankId", bankid);
        Div_Div_Unit.put("otpReferenceId", DB_refid);

        Map<String, Object> Div_Div_Amount = new HashMap<>();
        Div_Div_Amount.put("holdingProfileId", Holdingid);
        Div_Div_Amount.put("folio", folio);
        Div_Div_Amount.put("goalId", goalid);
        Div_Div_Amount.put("goalName", goalname);
        Div_Div_Amount.put("fromSchemeName", fromschemename);
        Div_Div_Amount.put("fromSchemeCode", fromschemecode);
        Div_Div_Amount.put("toSchemeName", toschemename);
        Div_Div_Amount.put("toSchemeCode", toschemcode);
        Div_Div_Amount.put("amount", Login.Switch_Amt);
        Div_Div_Amount.put("fromOption", fromoption);
        Div_Div_Amount.put("fromDividendOption", "Payout");
        Div_Div_Amount.put("toOption", tooption);
        Div_Div_Amount.put("toDividendOption", "Reinvestment");       // Payout / Reinvestment
        Div_Div_Amount.put("switchMode", "partial");              //partial or all
        Div_Div_Amount.put("switchType", "regular");
        Div_Div_Amount.put("bankId", bankid);
        Div_Div_Amount.put("otpReferenceId", DB_refid);

        Map<String, Object> Div_Div_All = new HashMap<>();
        Div_Div_All.put("holdingProfileId", Holdingid);
        Div_Div_All.put("folio", folio);
        Div_Div_All.put("goalId", goalid);
        Div_Div_All.put("goalName", goalname);
        Div_Div_All.put("fromSchemeName", fromschemename);
        Div_Div_All.put("fromSchemeCode", fromschemecode);
        Div_Div_All.put("toSchemeName", toschemename);
        Div_Div_All.put("toSchemeCode", toschemcode);
        Div_Div_All.put("units", Total_units);
        Div_Div_All.put("fromOption", fromoption);
        Div_Div_All.put("fromDividendOption", "Payout");
        Div_Div_All.put("toOption", tooption);
        Div_Div_All.put("toDividendOption", "Reinvestment");       // Payout / Reinvestment
        Div_Div_All.put("switchMode", "full");              //partial or all
        Div_Div_All.put("switchType", "regular");
        Div_Div_All.put("bankId", bankid);
        Div_Div_All.put("otpReferenceId", DB_refid);


        Map<String, Object> Div_Growth_Unit = new HashMap<>();
        Div_Growth_Unit.put("holdingProfileId", Holdingid);
        Div_Growth_Unit.put("folio", folio);
        Div_Growth_Unit.put("goalId", goalid);
        Div_Growth_Unit.put("goalName", goalname);
        Div_Growth_Unit.put("fromSchemeName", fromschemename);
        Div_Growth_Unit.put("fromSchemeCode", fromschemecode);
        Div_Growth_Unit.put("toSchemeName", toschemename);
        Div_Growth_Unit.put("toSchemeCode", toschemcode);
        Div_Growth_Unit.put("units", Login.Switch_Units);
        Div_Growth_Unit.put("fromOption", fromoption);
        Div_Growth_Unit.put("fromDividendOption", "Payout");
        Div_Growth_Unit.put("toOption", tooption);
        Div_Growth_Unit.put("switchMode", "partial");              //partial or full
        Div_Growth_Unit.put("switchType", "regular");
        Div_Growth_Unit.put("bankId", bankid);
        Div_Growth_Unit.put("otpReferenceId", DB_refid);

        Map<String, Object> Div_Growth_Amount = new HashMap<>();
        Div_Growth_Amount.put("holdingProfileId", Holdingid);
        Div_Growth_Amount.put("folio", folio);
        Div_Growth_Amount.put("goalId", goalid);
        Div_Growth_Amount.put("goalName", goalname);
        Div_Growth_Amount.put("fromSchemeName", fromschemename);
        Div_Growth_Amount.put("fromSchemeCode", fromschemecode);
        Div_Growth_Amount.put("toSchemeName", toschemename);
        Div_Growth_Amount.put("toSchemeCode", toschemcode);
        Div_Growth_Amount.put("amount", Login.Switch_Amt);
        Div_Growth_Amount.put("fromOption", fromoption);
        Div_Growth_Amount.put("fromDividendOption", "Payout");
        Div_Growth_Amount.put("toOption", tooption);
        Div_Growth_Amount.put("switchMode", "partial");              //partial or full
        Div_Growth_Amount.put("switchType", "regular");
        Div_Growth_Amount.put("bankId", bankid);
        Div_Growth_Amount.put("otpReferenceId", DB_refid);

        Map<String, Object> Div_Growth_All = new HashMap<>();
        Div_Growth_All.put("holdingProfileId", Holdingid);
        Div_Growth_All.put("folio", folio);
        Div_Growth_All.put("goalId", goalid);
        Div_Growth_All.put("goalName", goalname);
        Div_Growth_All.put("fromSchemeName", fromschemename);
        Div_Growth_All.put("fromSchemeCode", fromschemecode);
        Div_Growth_All.put("toSchemeName", toschemename);
        Div_Growth_All.put("toSchemeCode", toschemcode);
        Div_Growth_All.put("units", Total_units);
        Div_Growth_All.put("fromOption", fromoption);
        Div_Growth_All.put("fromDividendOption", "Payout");
        Div_Growth_All.put("toOption", tooption);
        Div_Growth_All.put("switchMode", "full");              //partial or full
        Div_Growth_All.put("switchType", "regular");
        Div_Growth_All.put("bankId", bankid);
        Div_Growth_All.put("otpReferenceId", DB_refid);

        if ((Login.Switch_Units==0) && Login.Switch_Amt==0) {

            if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Growth_All);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Div_All);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Div_All);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Growth_All);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            }
        }
        // Units =0
        else if (Login.Switch_Units==0) {
            if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Growth_Amount);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Div_Amount);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Div_Amount);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Growth_Amount);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            }
        }
        else {
            if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Growth_Unit);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Growth") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Growth_Div_Unit);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Dividend"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Div_Unit);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            } else if (fromoption.equalsIgnoreCase("Dividend") && (tooption.equalsIgnoreCase("Growth"))) {
                RequestSpecification redeem = given().log().all().spec(req)
                        .body(Div_Growth_Unit);
                redeem.when().post("/core/investor/switch")
                        .then().log().all().spec(respec);
            }
        }
    }
    @Test(priority = 19)
    public void Recent_Transaction() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid)
                .queryParam("page", "1")
                .queryParam("size", "10");
        RecentTransaction.Root response = res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().as(RecentTransaction.Root.class);
        int count = response.getData().size();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < response.getData().get(i).getMf().size(); j++) {
                for (int k = 0; k < response.getData().get(i).getMf().get(j).getActions().size(); k++) {
                    if (response.getData().get(i).getMf().get(j).getFolio().equalsIgnoreCase(Login.Switch_Folio) ==
                            (response.getData().get(i).getMf().get(j).getActions().get(k).equalsIgnoreCase("cancel"))) {
                        RT_refno = response.getData().get(i).getMf().get(j).getReferenceNo();
                        System.out.println(RT_refno);
                    }
                }
            }
        }
    }

    @Test(priority = 20)
    public void Delete_API() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", RT_refno);

        RequestSpecification can=given().log().all().spec(req)
                .body(del);
        can.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }
}
