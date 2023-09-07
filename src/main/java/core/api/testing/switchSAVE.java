package core.api.testing;

import core.basepath.AccessPropertyFile;
import core.dbconnection.DatabaseConnection;
import core.model.*;
import core.model.otp.CommonOTP;
import core.model.otp.VerifyOtpRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class switchSAVE extends AccessPropertyFile {

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1MTUxNTkiLCJzY29wZXMiOiJyZWFkLHdyaXRlIiwibmFtZSI6IkF1dG9tYXRpb24iLCJlbWFpbCI6IlJlZ3Jlc3Npb25AZ21haWwuY29tIiwibW9iaWxlIjoiMDc5MDc5MDg3NiIsIm1hbmFnZW1lbnQtdXNlci1pZCI6MzQ4NiwibWFuYWdlbWVudC11c2VyLXJvbGVzIjoiYWRtaW4iLCJpc3MiOiJmdW5kc2luZGlhLmNvbSIsImp0aSI6IjFiM2M5ZjVkLWJlYTYtNDZkYi05ZWQ0LTk2NmZkMjhiZDUyMyIsImlhdCI6MTY5NDA4NjE3NSwiZXhwIjoxNjk0MDg3NDM1fQ.k4kAMSaFrgw5sQ7OG_vefcn0v_r4kuDNijdbr_U_uDCPq0ZKp60AM_qVAEVVaqW2Jg94CYMQXk1NcXd6PRjx0A";

    private String growth_amt, growth_unit, div_amt, div_unit;
    String cancelId;
    String savefolio = "343423/434";         // possible folio with same AMC ==> 343423/435
    String savegoalId = "459104";             //possible goalid==> 459100 / 459101 / 459102 / 459103 / 459104 / 459114 / 459706
    String holdid = "183318";

    public switchSAVE() {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", getChannelID())
                .addHeader("x-fi-access-token", token)
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
    public void Switch_API() {
        RequestSpecification res = given()
                .spec(req)
                .body(growth_unit);
        res.when().post("/core/investor/switch/save")
                .then().log().all().spec(respec);
     /*   String[] bodyValues = {growth_amt, growth_unit, div_unit, div_amt};

        for (String bodyValue : bodyValues) {
            RequestSpecification res = given()
                    .spec(req)
                    .body("bodyValue");
            res.when().post("/core/investor/switch/save")
                    .then().log().all().spec(respec);
        }*/
    }

    @BeforeClass
    public void setup() {
        growth_unit = "{\n" +
                "  \"goalName\": \"Test Portfolio\",\n" +
                "  \"toSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(G)\",\n" +
                "  \"fromSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(IDCW)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromDividendOption\": \"Payout\",\n" +
                "  \"fromOption\": \"Dividend\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \"\",\n" +
                "  \"holdingProfileId\": \"" + holdid + "\",\n" +
                "  \"folio\": \"" + savefolio + "\",\n" +
                "  \"goalId\": \"" + savegoalId + "\",\n" +
                "  \"fromSchemeCode\": \"20748\",\n" +
                "  \"toSchemeCode\": \"20747\",\n" +
                "  \"toOption\": \"Growth\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";
        growth_amt = "{\n" +
                "  \"goalName\": \"Test Portfolio\",\n" +
                "  \"toSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(G)\",\n" +
                "  \"fromSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(IDCW)\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"fromDividendOption\": \"Payout\",\n" +
                "  \"fromOption\": \"Dividend\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \"\",\n" +
                "  \"holdingProfileId\": \"" + holdid + "\",\n" +
                "  \"folio\": \"" + savefolio + "\",\n" +
                "  \"goalId\": \"" + savegoalId + "\",\n" +
                "  \"fromSchemeCode\": \"20748\",\n" +
                "  \"toSchemeCode\": \"20747\",\n" +
                "  \"toOption\": \"Growth\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";

        div_unit = "{\n" +
                "  \"goalName\": \"Test Portfolio\",\n" +
                "  \"toSchemeName\": \"Bandhan CRISIL IBX Gilt April 2028 Index Fund-Reg(IDCW)\",\n" +
                "  \"fromSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(IDCW)\",\n" +
                "  \"units\": 1,\n" +
                "  \"fromDividendOption\": \"Payout\",\n" +
                "  \"fromOption\": \"Dividend\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \"\",\n" +
                "  \"holdingProfileId\": \"" + holdid + "\",\n" +
                "  \"folio\": \"" + savefolio + "\",\n" +
                "  \"goalId\": \"" + savegoalId + "\",\n" +
                "  \"fromSchemeCode\": \"20748\",\n" +
                "  \"toSchemeCode\": \"45423\",\n" +
                "  \"toDividendOption\": \"Payout\",\n" +
                "  \"toOption\": \"Dividend\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";

        div_amt = "{\n" +
                "  \"goalName\": \"Test Portfolio\",\n" +
                "  \"toSchemeName\": \"Bandhan CRISIL IBX Gilt April 2028 Index Fund-Reg(IDCW)\",\n" +
                "  \"fromSchemeName\": \"Bandhan Banking & PSU Debt Fund-Reg(IDCW)\",\n" +
                "  \"amount\": 1000,\n" +
                "  \"fromDividendOption\": \"Payout\",\n" +
                "  \"fromOption\": \"Dividend\",\n" +
                "  \"switchType\": \"regular\",\n" +
                "  \"switchMode\": \"partial\",\n" +
                "  \"otpReferenceId\": \"\",\n" +
                "  \"holdingProfileId\": \"" + holdid + "\",\n" +
                "  \"folio\": \"" + savefolio + "\",\n" +
                "  \"goalId\": \"" + savegoalId + "\",\n" +
                "  \"fromSchemeCode\": \"20748\",\n" +
                "  \"toSchemeCode\": \"45423\",\n" +
                "  \"toDividendOption\": \"Payout\",\n" +
                "  \"toOption\": \"Dividend\",\n" +
                "  \"bankId\": \"1\"\n" +
                "}";
    }

/*    @Test(priority = 1)
    public void authorization_switchCancel() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdid);
        AuthorizationResponse.Root response = res.when().get("/core/investor/transactions/authorization")
                .then().log().all().spec(respec).extract().response().as(AuthorizationResponse.Root.class);
        for (AuthorizationResponse.Switch data : response.getData().getSwitches()) {
            cancelId = data.getId();
            System.out.println(cancelId);
          *//*  List id = new LinkedList();
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", cancelId);
            payload.put("action", "reject");
            payload.put("type", "switch");
            payload.put("portfolio", false);
            payload.put("switchType", "regular");
            payload.put("otpReferenceId", "");
            id.add(payload);
            RequestSpecification can = given().spec(req)
                    .body(id);
            can.when().post("/core/investor/transactions/authorization")
                    .then().log().all().spec(respec);*//*
        }
    }*/
    //Live
   /* @Test
    public void LiveSwitch_API() {   //goal ID : 2932872 / 3363297
        RequestSpecification res = given()
                .spec(req)
                .body("{\n" +
                        "  \"goalName\": \"sathish D Portfolio\",\n" +
                        "  \"toSchemeName\": \"Mirae Asset Cash Management-Reg(DD-IDCW)\",\n" +
                        "  \"fromSchemeName\": \"Mirae Asset Emerging Bluechip-Reg(G)\",\n" +
                        "  \"units\": 1,\n" +
                        "  \"fromOption\": \"Growth\",\n" +
                        "  \"switchType\": \"regular\",\n" +
                        "  \"switchMode\": \"partial\",\n" +
                        "  \"otpReferenceId\": \"\",\n" +
                        "  \"holdingProfileId\": \"1403821\",\n" +
                        "  \"folio\": \"79958299852\",\n" +
                        "  \"goalId\": \"2932872\",\n" +
                        "  \"fromSchemeCode\": \"9767\",\n" +
                        "  \"toSchemeCode\": \"7555\",\n" +
                        "  \"toDividendOption\": \"Reinvestment\",\n" +
                        "  \"toOption\": \"Dividend\",\n" +
                        "  \"bankId\": \"1\"\n" +
                        "}");
        res.when().post("/core/investor/switch/save")
                .then().log().all().spec(respec);
    }*/
}
