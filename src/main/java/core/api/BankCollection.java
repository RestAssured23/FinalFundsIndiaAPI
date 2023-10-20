package core.api;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class BankCollection extends AccessPropertyFile {

    public BankCollection() throws IOException {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "1.0")
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
    public void ListBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287096);
        response = res.when().get("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", "287096");
        response=  res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void QRBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287096)
               .queryParam("source", "ADD_BANK");
        response = res.when().get("/core/investor/banks/qr")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void QRCallBack() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287096)
                .queryParam("id", "56e9bc0a-95ea-4fe5-8b9d-8f3381e4e2c9");
        response = res.when().get("/core/investor/banks/qr/callback")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

    @Test(priority = 1)
    public void User_StatusChange() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("investorId","287096");
        payload.put("userBankId","2");
        payload.put("type","make_as_primary");      //  make_as_primary
        //  payload.put("remarks","Test");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().post("/core/investor/banks/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

    @Test(priority = 1)
    public void admin_StatusChange() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("investorId","287096");
        payload.put("userBankId","4");
        payload.put("type","reject");      //  make_as_primary / activate / reject
      //  payload.put("remarks","Test");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().post("/core/investor/banks/admin/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

    @Test(priority = 1)
    public void admin_Doc_Download() {
        RequestSpecification res = given().spec(req)
                .queryParam("documentType","investor-cancelled-cheque")
                .queryParam("documentId","3");
        response = res.when().get("/core/investor/documents/download")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void admin_Download_Preview() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("documentId","3");
        payload.put("documentType","investor-cancelled-cheque");
        payload.put("referenceId","investorId");
        payload.put("idType","287096");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().get("/core/investor/documents/download/preview")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
}

