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
import core.dbconnection.DatabaseConnection;
import core.model.HoldingProfile;
import core.model.MFSearchForm;
import core.model.PortfolioDashboard;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static io.restassured.RestAssured.given;

public class OTIinvestment extends AccessPropertyFile {
    RequestSpecification req ;    ResponseSpecification respec;
    String Holdingid, InvestorId, otp_refid, dbotp, DB_refid, goalID,CartId,GroupId;
    String schemeCode, schemeName, schemeOption;
    public OTIinvestment() throws IOException {
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
    @Test(priority = 21)
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

    @Test(priority = 30)
    public void Dashboard_portfolio() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "183318");
        payload.put("showZeroHoldings", true);
        Map<String, Object> sort = new HashMap<>();
        sort.put("by", "investment_amount");
        sort.put("type", "desc");
        payload.put("sort", sort);
        payload.put("type", "portfolio");
        RequestSpecification res = given().spec(req)
                .body(payload);
        PortfolioDashboard.Root response = res.when().post("/core/investor/dashboard/portfolio")
                .then().log().all().spec(respec).extract().response().as(PortfolioDashboard.Root.class);
        goalID = response.getData().get(0).getId();
        System.out.println("Goal ID :" + goalID);
    }

    @Test(priority = 31)
    public void product_search_mf_form() {
        RequestSpecification res = given().log().all().spec(req)
                .body(Payload.product_Search());
        MFSearchForm.Root response = res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().as(MFSearchForm.Root.class);

        for (MFSearchForm.Content content : response.getData().getContent()) {
            if (content.getName().equalsIgnoreCase(expectedscheme)) {
                schemeName = content.getName();
                schemeCode = content.getSchemeCode();
                schemeOption = content.getOption();
                break; // Exit the loop since we found a matching content item
            } else {
                schemeName = response.getData().getContent().get(0).getName();
                schemeCode = response.getData().getContent().get(0).getSchemeCode();
                schemeOption = response.getData().getContent().get(0).getOption();
            }
        }
        // Print the results
        System.out.println(schemeName);
        System.out.println(schemeCode);
        System.out.println(schemeOption);
    }

    @Test(priority = 32)
    public void Investor_Cart() {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("product", "MF");
        payload.put("id", "");
            Map<String, Object> appInfo = new HashMap<>();
            appInfo.put("os", "Web-FI");
            appInfo.put("fcmId", "");
        payload.put("appInfo", appInfo);
        payload.put("holdingProfileId", Holdingid);

            Map<String, Object> oti = new LinkedHashMap<>();
            oti.put("totalAmount", inv_amount);
            oti.put("investmentType", "oti");
            oti.put("paymentId", "");

        List<Map<String, Object>> schemeList = new LinkedList<>();
            Map<String, Object> schemeData = new HashMap<>();
            schemeData.put("dividendOption", "Payout");
            schemeData.put("id", "");
            schemeData.put("folio", "-");
            schemeData.put("bankId", "1");
            schemeData.put("payment", true);
            schemeData.put("option", schemeOption);
            schemeData.put("goalId", goalID);
            schemeData.put("schemeCode", schemeCode);
            schemeData.put("schemeName", schemeName);
            schemeData.put("amount", inv_amount);
            schemeData.put("sipType", "");
            schemeData.put("sipDate", 0);
        schemeList.add(schemeData);

        oti.put("schemes", schemeList);

        Map<String, Object> investment = new LinkedHashMap<>();
            investment.put("oti", oti);
            payload.put("mf", investment);

        RequestSpecification res = given().log().all().spec(req)
            //    .queryParam("validation",true)
                .body(payload);
        AddScheme.Root response=res.when().post("/core/investor/cart")
                .then().log().all().spec(respec).extract().response().as(AddScheme.Root.class);
        CartId= response.getData().getCartId();
        System.out.println(CartId);
    }
    @Test(priority = 33)
    public void Folio_Group_ID() {
        RequestSpecification getres = given().log().all().spec(req)
                .queryParam("cartId", CartId);
        GetCart.Root response = getres.when().get("/core/investor/cart/folio-groups")
                .then().log().all().spec(respec).extract().response().as(GetCart.Root.class);
        GroupId = response.getData().getGroups().get(0).getGroupId();
        System.out.println(GroupId);
    }
    @Test(priority = 34)
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
    @Test(priority = 35)
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
    @Test(priority = 36)
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
    @Test(priority = 37)
    public void Buy_Cart() {
        RequestSpecification buyres = given().log().all().spec(req)
                .queryParam("cartId", CartId);
        buyres.when().get("/core/investor/cart/buy")
                .then().log().all().spec(respec);
    }

}
