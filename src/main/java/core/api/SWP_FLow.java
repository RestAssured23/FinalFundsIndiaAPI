package core.api;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DatabaseConnection;
import core.model.*;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth;

public class SWP_FLow extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private String frequency ="monthly";      private int insEcsdate =5;

    String holdingId,InvestorId, otpRefid, dbotp, dbRefid, Swp_ID;
    int minInstallment;    int noInstallments;
    ArrayList<Integer> SWP_date;     double minAmount;
    String goalId,folio, schemeName, schemeCode, schemeFrequency;
    String startDate, endDate, ecsDate;

    public SWP_FLow() {
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
    @Test
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
    @Test(priority = 1)
    public void InvestedScheme_API() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", holdingId);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);
        int count = response.getData().size();
        for (int i = 0; i < count; i++) {
            if(response.getData().get(i).getFolio().equalsIgnoreCase(folio_pro)){
                schemeCode = response.getData().get(i).getSchemeCode();
                schemeName =response.getData().get(i).getSchemeName();
                goalId =response.getData().get(i).getGoalId();
                folio=response.getData().get(i).getFolio();
                System.out.println(folio);
            }
        }
    }
    @Test(priority = 2)
    public void product_search_mf_form() {
        RequestSpecification res = given().spec(req)
                .queryParam("page", 1)
                .queryParam("size", 100)
                .queryParam("schemeCodes", schemeCode);
        MFscheme.Root response = res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);

        for (MFscheme.Content contentItem : response.getData().getContent()) {
            for (MFscheme.SwpFrequency swpFrequency : contentItem.getSwpFrequencies()) {
                minInstallment = swpFrequency.getMinimum();
                schemeFrequency = swpFrequency.getFrequency();
                minAmount = swpFrequency.getAmounts().getMinimumAmount();
                SWP_date = contentItem.getSwpDates();
                System.out.println(minAmount);
                System.out.println(SWP_date);
                System.out.println(minInstallment);
            }
            break;
        }
    }
    @Test(priority = 3)
    public void Installment_dates() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("installments", minInstallment);
        payload.put("frequency", frequency);
        payload.put("feature", "SWP");
        payload.put("ecsDate", insEcsdate);

        RequestSpecification res = given().log().all().spec(req)
                .body(payload);
        InstallmentDates.Root response=res.when().post("/core/calculators/installment-dates")
                .then().log().body().spec(respec).extract().response().as(InstallmentDates.Root.class);

        noInstallments = response.getData().getInstallments();
        int ecs_date = response.getData().getEcsDate();
        ecsDate = String.valueOf(ecs_date);
        frequency = response.getData().getFrequency();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        startDate = df.format(response.getData().getStartDate());
        endDate = df.format(response.getData().getEndDate());
    }
    @Test(priority = 4)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<String, Object>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId", folio);
        otppayload.put("workflow", "swp");

        RequestSpecification commonotp = given().log().all().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefid = responce.getData().getOtpReferenceId();
    }
  /*  @Test(priority = 5)
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
            dbotp = rs.getString("otp");
            dbRefid = rs.getString("referenceid");
            System.out.println("OTP :" + dbotp);
            System.out.println("OTPReferenceID :" + dbRefid);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (s1 != null) s1.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        }
    }*/
    @Test(priority = 6)
    public void Verify_OTP() {
        VerifyOtpRequest.Root payload = new VerifyOtpRequest.Root();
        VerifyOtpRequest.Otp otp = new VerifyOtpRequest.Otp();
        otp.setSms("");
        otp.setEmail("");
        otp.setEmail_or_sms(dbotp);
        payload.setOtp(otp);
        payload.setOtpReferenceId(dbRefid);
        RequestSpecification res1 = given().log().all().spec(req)
                .body(payload);
        res1.when().post("/core/investor/common/otp/verify")
                .then().log().all().spec(respec);
    }
    @Test(priority = 7)
    public void SWP_API() {
        Map<String,Object> payload=new HashMap<String,Object>();
        payload.put("holdingProfileId", holdingId);
        payload.put("goalId", goalId);
        payload.put("folio",folio);
        payload.put("schemeCode", schemeCode);
        payload.put("dayOrDate", ecsDate);
        payload.put("numberOfInstallments", noInstallments);
        payload.put("frequency", frequency);
        payload.put("amount", minAmount);
        payload.put("startDate", startDate);
        payload.put("endDate", endDate);
        payload.put("otpReferenceId", dbRefid);

        RequestSpecification res = given().log().all().spec(req)
                .body(payload);
        SWPResponse.Root response=res.when().post("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().as(SWPResponse.Root.class);
        Swp_ID=response.getData().getSwpId();
        System.out.println(Swp_ID);
        System.out.println(response.getData().getSwpAction());
    }
    @Test(priority = 8)
    public void swp() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", holdingId)
                .queryParam("page", "1")
                .queryParam("size", "50");
        res.when().get("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().asString();

    }
}
