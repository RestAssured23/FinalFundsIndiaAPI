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
import core.model.MFSearchForm;
import core.model.MandateAPI;
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

public class SIP extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    List<String> cartIdsList = new ArrayList<>(); int cartSize;
    List<String> groupIdList = new ArrayList<>(); int groupidSize;
    String holdinId, InvestorId, otpRefid, dbOtp, dbRefid,Goal_ID,CartId,GroupId;
    String schemeCode, schemeName, schemeOption;
    double minAmount;int minTenure; String ConsumerCode,Bank_Id; double Available_Amount;

    public SIP() throws IOException {
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
    @Test(priority = 40)
    public void Holding_Profile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            if (data.getHoldingProfileId().equalsIgnoreCase(holdingid_pro)) {
                holdinId = data.getHoldingProfileId();
                System.out.println("Holding ID is matched with the property file: " + holdinId);
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            Assert.fail("Holding ID is not matched with Investor. Stopping the test.");
        }
    }
    @Test(priority = 41)
    public void Dashboard_portfolio() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", holdinId);
        payload.put("showZeroHoldings", true);
        Map<String, Object> sort = new HashMap<>();
        sort.put("by", "investment_amount");
        sort.put("type", "desc");
        payload.put("sort", sort);
        payload.put("type", "portfolio");
        RequestSpecification res = given().log().all().spec(req)
                .body(payload);
        PortfolioDashboard.Root response = res.when().post("/core/investor/dashboard/portfolio")
                .then().log().all().spec(respec).extract().response().as(PortfolioDashboard.Root.class);
        Goal_ID = response.getData().get(0).getId();
        System.out.println("Goal ID :" + Goal_ID);
    }
    @Test(priority = 42)
    public void product_search_mf_form() {
        RequestSpecification res = given().log().all().spec(req)
                .body(Payload.product_Search());
        MFSearchForm.Root response = res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().as(MFSearchForm.Root.class);

        for (MFSearchForm.Content content : response.getData().getContent()) {
            if (content.getName().equalsIgnoreCase(amcschemeSearch)) {
                schemeName = content.getName();
                schemeCode = content.getSchemeCode();
                schemeOption = content.getOption();
                minTenure = content.getMinimumSipTenure();
                minAmount=content.getSipMinimumInvestment();
                break; // Exit the loop since we found a matching content item
            } else {
                schemeName = response.getData().getContent().get(0).getName();
                schemeCode = response.getData().getContent().get(0).getSchemeCode();
                schemeOption = response.getData().getContent().get(0).getOption();
                minTenure = response.getData().getContent().get(0).getMinimumSipTenure();
                minAmount=response.getData().getContent().get(0).getSipMinimumInvestment();
            }
        }
        // Print the results
        System.out.println(schemeName);
        System.out.println(schemeCode);
        System.out.println(schemeOption);
        System.out.println(minTenure);
        System.out.println(minAmount);
    }

    @Test(priority = 43)
    public void Mandate_API(){
        RequestSpecification res=given().log().all().spec(req)
                .queryParam("holdingProfileId", holdinId)
                .queryParam("ecsDate",1)
                .queryParam("sipType","regular");
        MandateAPI.Root response= res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().as(MandateAPI.Root.class);
        for(int i=0;i<response.getData().size();i++){
            if(response.getData().get(i).getStatus().equalsIgnoreCase("Approved")){
                ConsumerCode= response.getData().get(i).getConsumerCode();
                Available_Amount=response.getData().get(i).getAvailableAmount();
                Bank_Id=response.getData().get(i).getBank().getBankId();
                System.out.println(Bank_Id);
            }
        }
    }
    @Test(priority =44)
    public void Investor_Cart_SIP() {
        String[] sipTypes = { "regular", "flexi", "stepup", "alert" };

        for (String sipType : sipTypes) {
            Map<String, Object> payload = createPayloadForSIPType(sipType);

            RequestSpecification res = given().log().all().spec(req).body(payload);
            AddScheme.Root response = res.when().post("/core/investor/cart")
                    .then().log().all().spec(respec).extract().response().as(AddScheme.Root.class);

            CartId = response.getData().getCartId();
            cartIdsList.add(CartId); // Add the cart ID to the ArrayList
            System.out.println(cartIdsList);
            cartSize=cartIdsList.size();
            System.out.println(cartSize);
        }
    }
    private Map<String, Object> createPayloadForSIPType(String sipType) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("product", "MF");
        payload.put("id", "");
        Map<String, Object> info = new HashMap<>();
        info.put("os", "Web-FI");
        info.put("fcmId", "");
        payload.put("appInfo", info);
        payload.put("holdingProfileId", holdinId);

        Map<String, Object> sip = new LinkedHashMap<>();
        sip.put("totalAmount", inv_amount);
        sip.put("investmentType", "sip");
        sip.put("paymentId", "");
        List<Map<String, Object>> schemelist = new LinkedList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("folio", "-");
        data.put("bankId", Bank_Id);
        data.put("payment", false);
        data.put("option", schemeOption);
        data.put("goalId", Goal_ID);
        data.put("schemeCode", schemeCode);
        data.put("schemeName", schemeName);
        data.put("amount", inv_amount);
        data.put("sipType", sipType);
        data.put("sipDate", sipDate);

        Map<String, Object> regType = new HashMap<>();
        regType.put("amount", inv_amount);
        regType.put("frequency", "monthly");

        switch (sipType) {
            case "regular":
                regType.put("tenure", minTenure);
                regType.put("consumerCode", ConsumerCode);
                data.put("regular", regType);
                break;
            case "flexi":
                regType.put("maximumAmount", 3000);
                regType.put("flexiAmount", minAmount);
                regType.put("tenure", minTenure);
                regType.put("consumerCode", ConsumerCode);
                data.put("flexi", regType);
                break;
            case "stepup":
                regType.put("stepupFrequency", "half-yearly");  // annually/half-yearly
                regType.put("stepupAmount", Login.Inv_Amount);
                regType.put("finalAmount", 5000);
                regType.put("tenure", minTenure);
                regType.put("consumerCode", ConsumerCode);
                data.put("stepup", regType);
                break;
            case "alert":
                regType.put("startDate", alertStartdate);
                regType.put("endDate", alertEnddate);
                data.put("alert", regType);
                break;
        }
        schemelist.add(data);
        sip.put("schemes", schemelist);
        Map<String, Object> investment = new LinkedHashMap<>();
        investment.put("sip", sip);
        payload.put("mf", investment);
        return payload;
    }

    @Test(priority = 45)
    public void Folio_Group_ID() {

        for (int i = 0; i <= cartSize; i++) {
            RequestSpecification getres = given().log().all().spec(req)
                    .queryParam("cartId", CartId);
            GetCart.Root response = getres.when().get("/core/investor/cart/folio-groups")
                    .then().log().all().spec(respec).extract().response().as(GetCart.Root.class);
            String groupId = response.getData().getGroups().get(0).getGroupId();
            groupIdList.add(groupId);

        }// Print the list of group IDs
        for (int i = 0; i < groupIdList.size(); i++) {
            String cartId = String.valueOf(i);
            GroupId = groupIdList.get(i);
            System.out.println("Cart ID " + cartId + " - Group ID: " + GroupId);
        }
        }


    @Test(priority = 46)
    public void Common_Otp() {
        if (GroupId == null) {
            System.out.println("No group ID available. Please run 'Folio_Group_ID()' first.");
            return;
        }

        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio_group_id");
        otppayload.put("referenceId", GroupId);
        otppayload.put("workflow", "sip_oti_2fa");

        RequestSpecification otpres = given().log().all().spec(req)
                .body(otppayload);
        CommonOTP.Root response = otpres.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        String otpRefid = response.getData().getOtpReferenceId();
        System.out.println(otpRefid);
    }

    @Test(priority = 47)
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
            rs = s1.executeQuery("select * from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otpRefid + "'");
            rs.next();
            dbOtp = rs.getString("otp");
            dbRefid = rs.getString("referenceid");
            System.out.println("OTP :" + dbOtp);
            System.out.println("OTPReferenceID :" + dbRefid);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (s1 != null) s1.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }
    @Test(priority = 48)
    public void OTP_Verify() {
        Map<String, Object> payload1 = new HashMap<>();
        Map<String, Object> payload2 = new HashMap<>();
        payload2.put("email", "");
        payload2.put("sms", "");
        payload2.put("email_or_sms", dbOtp);
        payload1.put("otp", payload2);
        payload1.put("otpReferenceId", dbRefid);

        RequestSpecification res = given().log().all().spec(req)
                .body(payload1);
        res.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }
 /*  @Test(priority = 49)
    public void Buy_Cart() {
        RequestSpecification buyres = given().log().all().spec(req)
                .queryParam("cartId",CartId);
        buyres.when().get("/core/investor/cart/buy")
                .then().log().all().spec(respec);
    }*/
}
