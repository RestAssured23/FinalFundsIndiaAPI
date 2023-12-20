package core.onboarding;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DatabaseConnection;
import core.model.otp.CommonOTP;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class onboardingCollectionAPI extends AccessPropertyFile {

    String baseurl="https://scrum4-api.fundsindia.com";
    String channelID="10";  String emailId="abcd@gmail.com";
    String otpReferenceId;
    public onboardingCollectionAPI() throws IOException {

        req = new RequestSpecBuilder()
                .setBaseUri(baseurl)
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", channelID)
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
    public void email(){
                                                       /*     fcmId - Fcm Id
                                                            deviceId - unique device identifier
                                                            platform - (ios/android/web)
                                                            bundleId - com.fundsindia.FundsIndia,com.fundsindia, com.fundsindia-web,com.fiadvisor-web)
                                                            os       - (ios/android/windows/linux...etc)
                                                            osVersion - 8.1.0
                                                            brandModel - ONEPLUS A5010
                                                            appVersionCode - 64
                                                            appVersionName - 4.7.1*/

        RequestSpecification res = given().spec(req)
                .header("platform","web")
                .header("bundleId","com.fundsindia-web")
                  .body("{\n" +
                        "  \"email\": \""+emailId+ "\"\n" +
                        "}");
        res.when().post("onboarding/registration/user/email/exists")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test
    public void commonOTP(){
        Map<String, Object> otppayload = new HashMap<>();
        otppayload.put("type", "mobile_and_email");
        otppayload.put("idType", "email");
        otppayload.put("referenceId",emailId);
        otppayload.put("workflow", "onboarding_new");
       otppayload.put("token",false);

        RequestSpecification res=given().spec(req)
                .body(otppayload);
       String response= res.post("/core/user/common/otp")
                .then().log().all().spec(respec).extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        otpReferenceId = jsonPath.getString("data.otpReferenceId");
        System.out.println("OTP Reference ID: " + otpReferenceId);
    }

}
