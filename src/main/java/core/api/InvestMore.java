package core.api;

import core.basepath.AccessPropertyFile;
import core.model.otp.CommonOTP;
import core.model.twofa.AddScheme;
import core.model.twofa.GetCart;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import core.accesspropertyfile.Login;
import core.dbconnection.DatabaseConnection;
import core.model.HoldingProfile;
import core.model.InvestedScheme;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static io.restassured.RestAssured.given;

public class InvestMore extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    String Holdingid, InvestorId, folio, dbotp, DB_refid,bankid,goalid,option,CartId,GroupId;
    String schemeName, schemeCode,otp_refid;
    public InvestMore() throws IOException {
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
    @Test(priority = 20)
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
    @Test(priority = 21)
    public void getInvestedSchemeInfo() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

        for (InvestedScheme.Datum entry : response.getData()) {
            if (entry.getFolio().equalsIgnoreCase(folio_pro)) {
                 schemeName = entry.getSchemeName();
                 schemeCode = entry.getSchemeCode();
                 folio = entry.getFolio();
                 bankid = entry.getBankId();
                 goalid = entry.getGoalId();
                 option = entry.getOption();

                System.out.println("Scheme_Name: " + schemeName + "\n"
                        + "Scheme_Code: " + schemeCode + "\n"
                        + "Folio: " + folio + "\n"
                        + "Goal_ID: " + goalid + "\n"
                        + "Option: " + option);
            }
        }
    }
    @Test(priority = 22)
    public void createInvestorCart() {
        // Create payloadGrowth map
        Map<String, Object> payloadGrowth = new LinkedHashMap<>();
        payloadGrowth.put("product", "MF");
        payloadGrowth.put("id", "");

        // Create appInfo map
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("os", "Web-FI");
        appInfo.put("fcmId", "");
        payloadGrowth.put("appInfo", appInfo);

        // Add Holdingid to payloadGrowth
        payloadGrowth.put("holdingProfileId", Holdingid);

        // Create oti map
        Map<String, Object> oti = new LinkedHashMap<>();
        oti.put("totalAmount", inv_amount);
        oti.put("investmentType", "oti");
        oti.put("paymentId", "");

        // Create scheme data
        Map<String, Object> schemeData = new HashMap<>();
        schemeData.put("dividendOption", "Payout");
        schemeData.put("folio", folio);
        schemeData.put("bankId", bankid);
        schemeData.put("payment", true);
        schemeData.put("option", option);
        schemeData.put("goalId", goalid);
        schemeData.put("schemeCode", schemeCode);
        schemeData.put("schemeName", schemeName);
        schemeData.put("amount", inv_amount);
        schemeData.put("sipType", "");
        schemeData.put("sipDate", 0);

        // Create schemeList and add schemeData to it
        List<Map<String, Object>> schemeList = new LinkedList<>();
        schemeList.add(schemeData);
        oti.put("schemes", schemeList);

        // Create investment map and add oti to it
        Map<String, Object> investment = new LinkedHashMap<>();
        investment.put("oti", oti);
        payloadGrowth.put("mf", investment);

        RequestSpecification res = given().spec(req)
                .body(payloadGrowth);
        AddScheme.Root response=res.when().post("/core/investor/cart")
                .then().log().body().spec(respec).extract().response().as(AddScheme.Root.class);
        CartId= response.getData().getCartId();
        System.out.println(CartId);
    }

    @Test(priority = 23)
    public void Folio_Group_ID() {
        RequestSpecification getres = given().log().all().spec(req)
                .queryParam("cartId", CartId);
        GetCart.Root response = getres.when().get("/core/investor/cart/folio-groups")
                .then().log().all().spec(respec).extract().response().as(GetCart.Root.class);
        GroupId = response.getData().getGroups().get(0).getGroupId();
        System.out.println(GroupId);
    }
    @Test(priority = 24)
    public void Common_Otp() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio_group_id");
        otppayload.put("referenceId", GroupId);
        otppayload.put("workflow", "sip_oti_2fa");

        RequestSpecification otpres = given().log().all().spec(req)
                .body(otppayload);
        CommonOTP.Root response = otpres.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otp_refid = response.getData().getOtpReferenceId();
        System.out.println(otp_refid);
    }
    @Test(priority = 25)
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
    @Test(priority = 26)
    public void OTP_Verify() {
        Map<String, Object> payload1 = new HashMap<>();
        Map<String, Object> payload2 = new HashMap<>();
        payload2.put("email", "");
        payload2.put("sms", "");
        payload2.put("email_or_sms", dbotp);
        payload1.put("otp", payload2);
        payload1.put("otpReferenceId", DB_refid);
        RequestSpecification res = given().log().all().spec(req)
                .body(payload1);
        res.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }
    @Test(priority = 27)
    public void Buy_Cart() {
        RequestSpecification buyres = given().log().all().spec(req)
                .queryParam("cartId", CartId);
        buyres.when().get("/core/investor/cart/buy")
                .then().log().all().spec(respec);
    }
}

