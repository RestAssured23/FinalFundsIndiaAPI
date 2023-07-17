package coreapi.testapi;

import coreapi.model.otp.CommonOTP;
import coreapi.model.twofa.AddScheme;
import coreapi.model.twofa.GetCart;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import coreapi.accesspropertyfile.Login;
import coreapi.dbconnection.dbo;
import coreapi.model.HoldingProfile;
import coreapi.model.MFSearchForm;
import coreapi.model.PortfolioDashboard;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static io.restassured.RestAssured.given;

public class OTIinvestment {
    RequestSpecification req =new RequestSpecBuilder()
            .setBaseUri(Login.URI())
            .addHeader("x-api-version","2.0")
            .addHeader("channel-id","10")
            .addHeader("x-fi-access-token", Login.Regression())
            .setContentType(ContentType.JSON).build();
    ResponseSpecification respec =new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid, InvestorId, otprefid, DB_Otp, DB_refid,Goal_ID,CartId,GroupId;
    String Scheme_code, Scheme_Name,Scheme_Option;
    public OTIinvestment() throws IOException {
    }
    @Test(priority = 29)
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
        }
    }

    @Test(priority = 30)
    public void Dashboard_portfolio() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", Holdingid);
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
    @Test(priority = 31)
    public void product_search_mf_form() {
        RequestSpecification res = given().log().all().spec(req)
                .body("{\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 10,\n" +
                        "  \"orderBy\": \"rating\",\n" +
                        "  \"orderType\": \"DESC\",\n" +
                        "  \"categories\": [],\n" +
                        "  \"subCategories\": [],\n" +
                        "  \"query\": \""+ Login.SchemeSearch+"\",\n" +
                        "  \"risk\": [],\n" +
                        "  \"ratings\": [],\n" +
                        "  \"amcs\": [],\n" +
                        "  \"searchCode\": [\n" +
                        "    {\n" +
                        "      \"value\": \"\",\n" +
                        "      \"sort\": true\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"oti\": true\n" +
                        "}");
        MFSearchForm.Root response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().as(MFSearchForm.Root.class);
        for(int i=0;i<response.getData().getContent().size();i++){
            if(response.getData().getContent().get(i).getName().equalsIgnoreCase(Login.Expected_Scheme)){
                Scheme_Name=response.getData().getContent().get(i).getName();
                Scheme_code=response.getData().getContent().get(i).getSchemeCode();
                Scheme_Option=response.getData().getContent().get(i).getOption();
            }else {
                Scheme_Name=response.getData().getContent().get(0).getName();
                Scheme_code=response.getData().getContent().get(0).getSchemeCode();
                Scheme_Option=response.getData().getContent().get(0).getOption();
            }
        }
    }
    @Test(priority = 32)
    public void Investor_Cart() {
        Map<String,Object> Payload=new LinkedHashMap<>();
        Payload.put("product","MF");
        Payload.put("id","");
        Map<String,Object> info=new HashMap<>();
        info.put("os","Web-FI");
        info.put("fcmId","");
        Payload.put("appInfo",info);
        Payload.put("holdingProfileId",Holdingid);
        Map<String,Object> oti=new LinkedHashMap<>();
        //oti.put("totalAmount",login.Inv_Amount);
        oti.put("totalAmount",Integer.parseInt(Login.Inv_Amount));
        oti.put("investmentType","oti");
        oti.put("paymentId","");
        List<Map<String, Object>> Schemelist = new LinkedList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("dividendOption","Payout");
        data.put("id","");
        data.put("folio","-");
        data.put("bankId","1");
        data.put("payment",true);
        data.put("option",Scheme_Option);
        data.put("goalId",Goal_ID);
        data.put("schemeCode",Scheme_code);
        data.put("schemeName",Scheme_Name);
        data.put("amount",Integer.parseInt(Login.Inv_Amount));
        data.put("sipType","");
        data.put("sipDate",0);
        Schemelist.add(data);
        oti.put("schemes",Schemelist);
        Map<String,Object> investment=new LinkedHashMap<>();
        investment.put("oti",oti);
        Payload.put("mf",investment);

        RequestSpecification res = given().log().all().spec(req)
            //    .queryParam("validation",true)
                .body(Payload);
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
        otprefid = response.getData().getOtpReferenceId();
        System.out.println(otprefid);
    }
    @Test(priority = 35)
    public void dbconnection() throws SQLException {
        // DB connection
        Statement s1 = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            dbo ds = new dbo();
            con = ds.getConnection();
            s1 = con.createStatement();
            rs = s1.executeQuery("select TOP 5* from dbo.OTP_GEN_VERIFICATION ogv where referenceId ='" + otprefid + "'");
            rs.next();
            DB_Otp = rs.getString("otp");
            DB_refid = rs.getString("referenceid");
            System.out.println("OTP :" + DB_Otp);
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
        payload2.put("email_or_sms", DB_Otp);
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
