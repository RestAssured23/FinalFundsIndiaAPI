package core.api;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DatabaseConnection;
import core.model.HoldingProfile;
import core.model.InvestedScheme;
import core.model.MFscheme;
import core.model.RecentTransaction;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class SwitchGoalChange extends AccessPropertyFile {

    public SwitchGoalChange() {
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
    @Test(priority = 14)
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","94764646/46");
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
    public void testswitch(){
        String payload="{\n" +
                "  \"goalName\": \"SwitchTest\",\n" +
                "  \"toSchemeName\": \"HDFC Corp Bond Fund(IDCW)\",\n" +
                "  \"fromSchemeName\": \"HDFC Banking and PSU Debt Fund-Reg(G)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromOption\": \"Growth\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \""+dbRefId+"\",\n" +
                "  \"holdingProfileId\": \"183318\",\n" +
                "  \"folio\": \"94764646/46\",\n" +
                "  \"goalId\": \"461041\",\n" +
                "  \"toGoalId\": \"461366\",\n" +
                "  \"fromSchemeCode\": \"23913\",\n" +
                "  \"toSchemeCode\": \"31059\",\n" +
                "  \"toDividendOption\": \"Payout\",\n" +
                "  \"toOption\": \"Dividend\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";
        RequestSpecification redeem = given().spec(req).body(payload);
        redeem.when().post("/core/investor/switch").then().log().all().spec(respec);
    }

    @Test(priority = 19)
    public void Cancel_Switch() {
        Map<String, String> del = new HashMap<>();
        del.put("action", "cancel");
        del.put("referenceNo", "1382270");

        RequestSpecification res=given().spec(req)
                .body(del);
        res.when().post("/core/investor/recent-transactions")
                .then().log().all().spec(respec);
    }

 /*   @Test(priority = 14)
    public void Common_OTP_Test() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","94764646/46");
        otppayload.put("workflow", "switch");

        RequestSpecification commonotp = given().spec(req).body(otppayload);
        CommonOTP.Root response = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);

        otpRefID = response.getData().getOtpReferenceId();

        // Extract request details
        RequestSpecification requestSpec = given().spec(req);
        String requestBody = otppayload.toString();

        // Build the curl command
        StringBuilder curlCommand = new StringBuilder("curl -X POST \"https://testapi.fundsindia.com/core/investor/common/otp\"");

        curlCommand.append(" -H \"x-fi-access-token: ").append(getAccessToken()).append("\"");
        curlCommand.append(" -H \"x-api-version: ").append("2.0").append("\"");

        // Add headers to the curl command
        for (Header header : requestSpec.get().getHeaders()) {

            curlCommand.append(" -H \"").append(header.getName()).append(": ").append(header.getValue()).append("\"");

        }

        // Add request body to the curl command
        curlCommand.append(" -d '").append(requestBody).append("'");

        // Print or use the curl command as needed
        System.out.println(curlCommand.toString());
    }
*/

}
