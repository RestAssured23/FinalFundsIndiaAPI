package core.api.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import core.model.otp.CommonOTP;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.otpRefID;
import static io.restassured.RestAssured.given;

public class NRIotpTest extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;

    public NRIotpTest() throws IOException {
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

    @Test               // NRI OTP check
    public void Common_OTP() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio");
        otppayload.put("referenceId","454545");
        otppayload.put("workflow", "redemption");       //redemption , stp, swp , switch

        RequestSpecification commonotp = given().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }
    @Test               // NRI OTP check
    public void Common_OTP_Invesmentflow() {
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "folio_group_id");
        otppayload.put("referenceId","10414");
        otppayload.put("workflow", "sip_oti_2fa");

        RequestSpecification commonotp = given().spec(req)
                .body(otppayload);
        CommonOTP.Root responce = commonotp.when().post("/core/investor/common/otp")
                .then().log().all().spec(respec).extract().response().as(CommonOTP.Root.class);
        otpRefID = responce.getData().getOtpReferenceId();
    }
    @Test               // NRI OTP check
    public void Nominee_New() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"holdingProfileId\": \"181201\",\n" +
                        "  \"optedOut\": false,\n" +
                        "  \"nominees\": [\n" +
                        "    {\n" +
                        "      \"dateOfBirth\": \"1981-10-08T00:00:00.000+0530\",\n" +
                        "      \"firstName\": \"test s\",\n" +
                        "      \"relationship\": \"Brother\",\n" +
                        "      \"percentage\": 100\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
         res.when().post("/core/investor/nominees")
                .then().log().all().spec(respec);
    }
    @Test               // NRI OTP check
    public void Existing_Nomine() {
        RequestSpecification res = given().spec(req)
                .queryParam("product","MF")
                .body("{\n" +
                        "  \"holdingProfileId\": \"181201\",\n" +
                        "  \"optedOut\": true,\n" +
                        "  \"folios\": [\n" +
                        "    {\n" +
                        "      \"amc\": \"400004\",\n" +
                        "      \"amcCode\": \"400004\",\n" +
                        "      \"folioNo\": \"454545\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"declarationType\": \"opt-out\",\n" +
                        "  \"processType\": \"online\"\n" +
                        "}");
        res.when().put("/core/investor/nominees")
                .then().log().all().spec(respec);
    }
}



