package core.BankCollection;

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
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class Live_BankCollection extends AccessPropertyFile {

    public Live_BankCollection() throws IOException {
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
    String qrID, ekoUserBankID;

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
            System.out.println(InvestorId);
        }
    }

  /*  manually --/core/investor/banks -- NRE , Joint
    eko --> NRO / RI : --> name score 1-5 checq updload true , 6-9 check upload false (we are not activate bank)
    qrcallback ,
*/
    @Test(priority = 1)
    public void ListBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", 1401246);              // live : 1401246  // local 287822
        response = res.when().get("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 2)
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId);
        response=  res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 3)
    public void QRBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId)
              .queryParam("source", "ADD_BANK");     // ONBOARDING / ADD_BANK
        qrResponseBO.Root response = res.when().get("/core/investor/banks/qr")
                .then().log().all().spec(respec).extract().response().as(qrResponseBO.Root.class);
        qrID=response.getData().getId();
        System.out.println("QR ID: " +qrID);
    }
    @Test(priority = 4)
    public void QRCallBack() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId)
                .queryParam("id", qrID);
       response = res.when().get("/core/investor/banks/qr/callback")
                .then().log().all().spec(respec).extract().response().asString();

    }
    @Test(priority = 5)
    public void addBank_Manully_EKO() throws IOException {
        Map<String, Object> paylaod=new HashMap<>();
        paylaod.put("investorId",InvestorId);
        paylaod.put("accountNo","987456321021457");
        paylaod.put("accountType","Individual");
        paylaod.put("bankAccountType","Savings");       // NRE, NRO, Savings, Current
        paylaod.put("ifsc","HDFC0007165");
        paylaod.put("serviceProvider","EKO");

        RequestSpecification res = given().spec(req)
                .queryParam("source","ADD_BANK")            // ADD_BANK / ONBOARDING
                .body(paylaod);

        ekoResponseBO.Root response = res.when().post("core/investor/banks/verification")
                .then().log().all().spec(respec).extract().response().as(ekoResponseBO.Root.class);
        ekoUserBankID =response.getData().getUserBankId();
        System.out.println(ekoUserBankID);
    }
    @Test(priority = 6)
    public void addBank_Manully() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("C:\\Users\\Fi-User\\fi-repositories\\fi-test-automation\\src\\main\\java\\core\\advisor_Dashboard\\BankCollection\\test.txt")));

        Map<String, Object> paylaod=new HashMap<>();
        paylaod.put("referenceId",InvestorId);
        paylaod.put("idType","investorId");
        paylaod.put("type","investor_cancelled_cheque");
        paylaod.put("content",content);
        paylaod.put("fileName","IOS.jpg");

           Map<String, Object> data=new HashMap<>();
            data.put("userBankId", ekoUserBankID);
        paylaod.put("data",data);

        RequestSpecification res = given().spec(req)
                .body(paylaod);
        response = res.when().post("/core/investor/documents/upload")
                .then().log().all().spec(respec).extract().response().asString();
    }

        @Test(priority = 8)
    public void Delete_Bank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId)
                .queryParam("userBankId", ekoUserBankID);
        response = res.when().delete("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
    }

    @Test(priority = 7)
    public void postBank() throws IOException {
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



   /* @Test(priority = 1)
    public void User_StatusChange() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("investorId", "287642");
        payload.put("userBankId", "2");
        payload.put("type", "make_as_primary");      //  make_as_primary
        //  payload.put("remarks","Test");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response = res.when().post("/core/investor/banks/features")
                .then().log().all().spec(respec).extract().response().asString();
    }*/

}

