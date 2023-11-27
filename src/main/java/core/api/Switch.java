package core.api;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import core.dbconnection.DatabaseConnection;
import core.model.InvestedScheme;
import core.model.MFscheme;
import core.model.RecentTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static core.api.CommonVariable.*;

public class Switch extends AccessPropertyFile {

    public Switch() {
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
    @Test(priority = 10)
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
        }
    }
    @Test(priority = 11)
    public void getInvestedSchemeDetails() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

        for(InvestedScheme.Datum data:response.getData()) {
            if (data.getFolio().equalsIgnoreCase(folio_pro)) {
                fromSchemeName = data.getSchemeName();
                fromSchemeCode = data.getSchemeCode();
                folio = data.getFolio();
                units = data.getUnits();
                fromOption = data.getOption();
                goalId = data.getGoalId();
                bankId = data.getBankId();
                minAmt = data.getSwitchOut().getMinimumAmount();
                minUnit = data.getSwitchOut().getMinimumUnits();
                currentAmt = data.getCurrentAmount();
                goalName = data.getGoalName();
                totalUnits = data.getUnits();
                System.out.println(fromSchemeName);
                System.out.println(folio);
            }
        }
    }
    @Test(priority = 12)
    public void productSearchMFForm() {
        RequestSpecification res = given().spec(req)
                .queryParam("page", 1)
                .queryParam("size", 100)
                .queryParam("schemeCodes", fromSchemeCode);
        MFscheme.Root response = res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);
        for (MFscheme.Content content : response.getData().getContent()) {
            amcName = content.getAmc();
            amcCode = content.getAmcCode();
            Source_SchemeName = content.getName();
            System.out.printf(amcCode + "\t" + amcName + "\t" + Source_SchemeName);
        }
    }
    @Test(priority = 13)
    public void targetSchemeSearch() {
        RequestSpecification res = given().spec(req)
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
                        "      \"name\": \"" + amcName + "\",\n" +
                        "      \"value\": \"" + amcCode + "\"\n" +
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

        if (targetscheme_pro.equalsIgnoreCase("0")) {
            printSchemeDetails(response, 1);
        } else {
            for (int i = 0; i < response.getData().getContent().size(); i++) {
                if (response.getData().getContent().get(i).getName().equalsIgnoreCase(switch_target)) {
                    printSchemeDetails(response, i);
                    break;
                }
            }
        }
    }
    private void printSchemeDetails(MFscheme.Root response, int index) {
        toSchemeName = response.getData().getContent().get(index).getName();
        toSchemeCode = response.getData().getContent().get(index).getSchemeCode();
        toOption = response.getData().getContent().get(index).getOption();
        System.out.println("To SchemeName: " + toSchemeName);
        System.out.println("To schemecode: " + toSchemeCode);
    }

    @Test(priority = 14)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId",folio);
        otppayload.put("workflow", "switch");

        RequestSpecification commonotp = given().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }

    @Test(priority = 15)
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
    @Test(priority = 16)
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
        res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }

    @Test(priority = 17)
    public void Switch_API() {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("holdingProfileId", holdingId);
        requestData.put("folio", folio);
        requestData.put("goalId", goalName);
        requestData.put("goalName", goalName);
        requestData.put("fromSchemeName", fromSchemeName);
        requestData.put("fromSchemeCode", fromSchemeCode);
        requestData.put("toSchemeName", toSchemeName);
        requestData.put("toSchemeCode", toSchemeCode);
        requestData.put("toGoalId","461366");
        requestData.put("bankId", bankId);
        requestData.put("otpReferenceId", dbRefId);

        // Common fields for all switch types
        requestData.put("fromOption", fromOption);
        requestData.put("toOption", toOption);
        requestData.put("switchType", "regular");

        if (switch_unitpro == 0 && switch_amtpro == 0) {
            requestData.put("switchMode", "full");
            requestData.put("units", totalUnits);
        } else if (switch_unitpro == 0) {
            requestData.put("switchMode", "partial");
            requestData.put("amount", switch_amtpro);
        } else {
            requestData.put("switchMode", "partial");
            requestData.put("units", switch_unitpro);
        }

        // Determine the dividend option based on the toOption value
        String dividendOption = toOption.equalsIgnoreCase("Dividend") ? "Reinvestment" : "Payout";

        switch (fromOption + "_" + toOption) {
            case "Growth_Growth":
                requestData.put("units", totalUnits);
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
        RequestSpecification redeem = given().spec(req).body(requestData);
        redeem.when().post("/core/investor/switch").then().log().all().spec(respec);
    }
    @Test(priority = 18)
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
    }
    @Test(priority = 19)
    public void Cancel_Switch() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", firstReferenceNo);

        RequestSpecification res=given().spec(req)
                .body(del);
        res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }
}
