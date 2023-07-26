package coreapi.testapi;

import coreapi.basepath.AccessPropertyFile;
import coreapi.model.HoldingProfile;
import coreapi.model.otp.CommonOTP;
import coreapi.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import coreapi.accesspropertyfile.Login;
import coreapi.dbconnection.DatabaseConnection;
import coreapi.model.InvestedScheme;
import coreapi.model.MFscheme;
import coreapi.model.RecentTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Switch extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private String response;
    String Holdingid, folio, otp_refid, dbotp, DB_refid,  firstReferenceNo,Source_SchemeName;
    String goalid, goalname, bankid;
    double minamount, units, minunit, currentamount,Total_units;
    String fromschemename, fromschemecode, fromoption,   toschemename, toschemcode, tooption,AMC_Name, AMC_Code;
    public Switch() {
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
    @Test(priority = 11)
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
    @Test(priority = 12)
    public void getInvestedSchemeDetails() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

        for(InvestedScheme.Datum data:response.getData()) {
            if (data.getFolio().equalsIgnoreCase(folio_pro)) {
                fromschemename = data.getSchemeName();
                fromschemecode = data.getSchemeCode();
                folio = data.getFolio();
                units = data.getUnits();
                fromoption = data.getOption();
                goalid = data.getGoalId();
                bankid = data.getBankId();
                minamount = data.getSwitchOut().getMinimumAmount();
                minunit = data.getSwitchOut().getMinimumUnits();
                currentamount = data.getCurrentAmount();
                goalname = data.getGoalName();
                Total_units = data.getUnits();
                System.out.println(fromschemename);
                System.out.println(folio);
            }
        }
    }
    @Test(priority = 13)
    public void productSearchMFForm() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("page", 1)
                .queryParam("size", 100)
                .queryParam("schemeCodes", fromschemecode);
        MFscheme.Root response = res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);
        for (MFscheme.Content content : response.getData().getContent()) {
            AMC_Name = content.getAmc();
            AMC_Code = content.getAmcCode();
            Source_SchemeName = content.getName();
            System.out.printf(AMC_Code + "\t" + AMC_Name + "\t" + Source_SchemeName);
        }
    }
    @Test(priority = 14)
    public void targetSchemeSearch() {
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

        if (targetscheme_pro.equalsIgnoreCase("0")) {
            printSchemeDetails(response, 3);
        } else {
            for (int i = 0; i < size; i++) {
                if (response.getData().getContent().get(i).getName().equalsIgnoreCase(switch_target)) {
                    printSchemeDetails(response, i);
                    break;
                }
            }
        }
    }
    private void printSchemeDetails(MFscheme.Root response, int index) {
        toschemename = response.getData().getContent().get(index).getName();
        toschemcode = response.getData().getContent().get(index).getSchemeCode();
        tooption = response.getData().getContent().get(index).getOption();
        System.out.println("To SchemeName: " + toschemename);
        System.out.println("To schemecode: " + toschemcode);
        System.out.println("To Option: " + tooption);
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
        System.out.println("DB Connection Name: "+dbusr);
        Statement s1 = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            DatabaseConnection ds = new DatabaseConnection(dbusr, dbpwd, dburl, databasename, true, dbdrivername);
            con = ds.getConnection();
            Assert.assertNotNull(con, "Database connection failed!"); // Throw an error if the connection is null (failed)
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
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("holdingProfileId", Holdingid);
        requestData.put("folio", folio);
        requestData.put("goalId", goalid);
        requestData.put("goalName", goalname);
        requestData.put("fromSchemeName", fromschemename);
        requestData.put("fromSchemeCode", fromschemecode);
        requestData.put("toSchemeName", toschemename);
        requestData.put("toSchemeCode", toschemcode);
        requestData.put("bankId", bankid);
        requestData.put("otpReferenceId", DB_refid);

        // Common fields for all switch types
        requestData.put("fromOption", fromoption);
        requestData.put("toOption", tooption);
        requestData.put("switchType", "regular");

        if (switch_unitpro == 0 && switch_amtpro == 0) {
            requestData.put("switchMode", "full");
            requestData.put("units", Total_units);
        } else if (switch_unitpro == 0) {
            requestData.put("switchMode", "partial");
            requestData.put("amount", switch_amtpro);
        } else {
            requestData.put("switchMode", "partial");
            requestData.put("units", switch_unitpro);
        }

        // Determine the dividend option based on the toOption value
        String dividendOption = tooption.equalsIgnoreCase("Dividend") ? "Reinvestment" : "Payout";

        switch (fromoption + "_" + tooption) {
            case "Growth_Growth":
                requestData.put("units", Total_units);
                break;
            case "Growth_Dividend":
                requestData.put("toDividendOption", dividendOption);
                break;
            case "Dividend_Growth":
                requestData.put("fromDividendOption", "Payout");
                break;
            case "Dividend_Dividend":
                requestData.put("fromDividendOption", "Payout");
                requestData.put("toDividendOption", dividendOption);
                break;
            default:
                throw new IllegalArgumentException("Invalid switch combination");
        }

        RequestSpecification redeem = given().log().all().spec(req).body(requestData);
        redeem.when().post("/core/investor/switch").then().log().all().spec(respec);
    }
    @Test(priority = 19)
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
    @Test(priority = 20)
    public void Cancel_Switch() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", firstReferenceNo);

        RequestSpecification can=given().log().all().spec(req)
                .body(del);
        can.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }
}
