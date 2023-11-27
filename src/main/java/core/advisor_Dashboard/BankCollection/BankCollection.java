package core.advisor_Dashboard.BankCollection;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  /*  manually --/core/investor/banks -- NRE , Joint
    eko --> NRO / RI : --> name score 1-5 checq updload true , 6-9 check upload false (we are not activate bank)
    qrcallback ,
*/
    @Test(priority = 1)
    public void ListBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287109);       //282306 5 bank active (sathi@gmail.com)
        response = res.when().get("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", "287603");
        response=  res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void QRBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287633)
              .queryParam("source", "ADD_BANK");     // ONBOARDING / ADD_BANK
        response = res.when().get("/core/investor/banks/qr")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void QRCallBack() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287623)
                .queryParam("id", "04761e90-f045-4204-ad25-694c35422a30");
        response = res.when().get("/core/investor/banks/qr/callback")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test(priority = 1)
    public void addBank_Manully() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Fi-User\\fi-repositories\\fi-test-automation\\src\\main\\java\\core\\advisor_Dashboard\\BankCollection\\test.txt")));

        Map<String, Object> paylaod=new HashMap<>();
        paylaod.put("investorId","287109");
        paylaod.put("bankId","");
        paylaod.put("type","others");                   // primary, secondary, others
        paylaod.put("ifsc","HDFC0000183");
        paylaod.put("accountNo","987456321021457");
        paylaod.put("accountType","Individual");        // Joint, Individual, Either_Or_Survivor
        paylaod.put("bankAccountType","Savings");       // NRE, NRO, Savings, Current

            Map<String, Object> paylaod1=new HashMap<>();
            paylaod1.put("name","IOS.jpg");
            paylaod1.put("content",content);
        paylaod.put("cheque",paylaod1);

        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287623)
                .queryParam("source","ADD_BANK")            // ADD_BANK / ONBOARDING
                .body(paylaod);

        response = res.when().post("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }

    @Test(priority = 1)
    public void QR_AddBank() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Fi-User\\fi-repositories\\fi-test-automation\\src\\main\\java\\core\\advisor_Dashboard\\BankCollection\\test.txt")));

        Map<String, Object> paylaod=new HashMap<>();
        paylaod.put("investorId","287633");
        paylaod.put("id","cc08983f-bbdf-45d5-a749-8eb62d79ae3f");
        paylaod.put("bankAccountType","Savings");       // NRE, NRO, Savings, Current

        Map<String, Object> paylaod1=new HashMap<>();
        paylaod1.put("name","IOS.jpg");
        paylaod1.put("content",content);
        paylaod.put("cheque",paylaod1);

        RequestSpecification res = given().spec(req)
                .body(paylaod);

        response = res.when().post("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }


    @Test
    public void Delete_Bank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 287623)
                .queryParam("userBankId",4);
        response = res.when().delete("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }

    @Test(priority = 1)
    public void User_StatusChange() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("investorId", "287633");
        payload.put("userBankId", "3");
        payload.put("type", "make_as_primary");      //  make_as_primary
        //  payload.put("remarks","Test");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().post("/core/investor/banks/features")
                .then().log().all().spec(respec).extract().response().asString();
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

//DocumentDownload
    @Test(priority = 1)
    public void admin_Doc_Download_GetAPI() {
        RequestSpecification res = given().spec(req)
                .queryParam("documentType","investor-cancelled-cheque");
        response = res.when().get("/core/investor/documents/download")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void admin_Doc_Download() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("documentId","3");
        payload.put("documentType","investor-cancelled-cheque");
        payload.put("referenceId","investorId");
        payload.put("idType","287096");

        RequestSpecification res = given().spec(req)
                .body(payload);
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


    @Test(priority = 1)
    public void EKO_verification() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("ifsc","");
        payload.put("accountNo","");
        payload.put("investorId","287096");
      //  payload.put("serviceProvider","");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().get("/core/investor/banks/verification")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

}

