package core.advisor_Dashboard.portfoliobuilder;
import core.advisor_Dashboard.AD_AccessPropertyFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static io.restassured.RestAssured.given;

public class PortfolioBuilder extends AD_AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();



    public PortfolioBuilder() {
        req = new RequestSpecBuilder()
                .setBaseUri(getADBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", "10")
              // .addHeader("x-fi-access-token", token)
               .addHeader("x-fi-access-token", getAdminAccessToken())
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();
        respec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test   // (invocationCount = 10, threadPoolSize = 1)
    public void postRequirement(){
        RequestSpecification res=given().spec(req)
                        .body(portFolioBuilderPayload.postReq());
        res.post("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }
    @Test
    public void getRequirement(){      // requirementId , email , mobile any one is mandatory
        RequestSpecification res=given().spec(req)
                 .queryParam("requirementId","33ac5dba-ec51-4c66-ab3f-6ad58e2d7b59");
     //  .queryParam("email","post123@gmail.com");
      //      .queryParam("mobile","7000000001");
        res.get("/tools/portfolios/builder/requirements").then().log().all().spec(respec);
    }
    @Test
    public void putRequirement(){
        RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.updateReq());
        res.put("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteRequirement(){
        RequestSpecification res=given().spec(req)
            .queryParam("requirementId","fd5dac0b-da77-4a0f-87db-d24a1952a157");
        res.delete("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }
    @Test
    public void postMoney_Box(){
        RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postMoneyBox());
        res.post("/tools/portfolios/builder/money-box").then().log().all().spec(respec);
    }
    @Test
    public void getMoney_Box(){
        RequestSpecification res=given().spec(req)
                .queryParam("requirementId","116819f5-7e75-48ad-bc29-db546fa7dbfc");
        res.get("/tools/portfolios/builder/money-box").then().log().all().spec(respec);
    }
    @Test
    public void postWealth_Equation(){
        RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postWealthEquation());
        res.post("/tools/portfolios/builder/wealth-equation").then().log().all().spec(respec);
    }
    @Test
    public void getWealth_Equation(){
        RequestSpecification res=given()
                .queryParam("requirementId","c038c573-fb6f-4b10-bed1-5149758d8e9c").spec(req);
        res.get("/tools/portfolios/builder/wealth-equation").then().log().all().spec(respec);
    }
    @Test
    public void postSafetyBox(){
        RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postSafetyBox());
        res.post("/tools/portfolios/builder/suggested-portfolio/safety-box")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void getSafetyBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","2e33b880-3a60-4196-96ed-259e6442d2ab")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/safety-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void postShortTerm(){
       RequestSpecification res=given().spec(req)
                        .body(portFolioBuilderPayload.postShortterm());
        res.post("/tools/portfolios/builder/suggested-portfolio/short-term-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void getShortTermBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","2e33b880-3a60-4196-96ed-259e6442d2ab")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/short-term-box")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void postLongTermBox(){
       RequestSpecification res=given() .spec(req)
                .body(portFolioBuilderPayload.postLongterm());
        res.post("/tools/portfolios/builder/suggested-portfolio/long-term")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void getLongTermBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","2e33b880-3a60-4196-96ed-259e6442d2ab")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/long-term")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void postHighRisk(){
       RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postHighRisk());
        res.post("/tools/portfolios/builder/suggested-portfolio/high-risk")
                .then().log().all().spec(respec);
    }
    @Test
    public void getHighRisk(){
        RequestSpecification res=given()
                .queryParam("requirementId","2e33b880-3a60-4196-96ed-259e6442d2ab")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/high-risk")
                .then().log().all().spec(respec);
    }
    @Test
    public void getDiscussion() {
        RequestSpecification res = given()
                       .queryParam("requirementId","f8edfbc0-de03-4959-b477-d4665fd2ec53")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion")
                .then().log().all().spec(respec);
    }

    @Test
    public void postDiscussionAttachement(){
        Map<String,Object> payload=new HashMap<>();
        payload.put("requirementId","f8edfbc0-de03-4959-b477-d4665fd2ec53");
        payload.put("documentId","3");
        payload.put("name","ABC Strategy");
        payload.put("url","d1yefw7zveme14.cloudfront.net/Advisory_Dashboard/Portfolio_Builder/ABC_Framework.pdf");
        payload.put("category","abc_strategy");

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void getDiscussionAttachement(){
        RequestSpecification res=given()
                      .queryParam("requirementId","f8edfbc0-de03-4959-b477-d4665fd2ec53")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void DeleteDiscussionAttachement(){
        RequestSpecification res=given().spec(req)
              .queryParam("requirementId","f8edfbc0-de03-4959-b477-d4665fd2ec53")
               .queryParam("documentId","2");
        res.delete("/tools/portfolios/builder/discussion/attachments")
                .then()
                .log().all()
                .spec(respec);
    }
    @Test
    public void postLongTermPreset(){
       RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postLongTermPreset());
        res.post("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void getLongTermPreset(){
        RequestSpecification res=given().spec(req)
                        .queryParam("presetId","aaf9f22f-9aa4-42aa-987f-5287c4bf5083");
        res.get("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteLongTermPreset(){
        RequestSpecification res=given().spec(req)
           .queryParam("presetId","866aa43a-14a4-48b4-bfd0-a8e06f25e6d8");
        res.delete("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void postHighRiskPreset(){
        RequestSpecification res=given().spec(req)
                .body(portFolioBuilderPayload.postHighRiskPreset());
        res.post("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void getHighRiskPreset(){
        RequestSpecification res=given().spec(req)
                .queryParam("presetId","5b351105-af6c-4e08-b65d-cadf44f3a4a0");
        res.get("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteHighRiskPreset(){
        RequestSpecification res=given().spec(req)
               .queryParam("presetId","357531dc-362d-4360-bbb7-b5fe1ea35c29");
        res.delete("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }

    @Test
    public void postGoldPlanner(){
        RequestSpecification res=given().spec(req)
                .body("[\n" +
                        "    {\n" +
                        "        \"requirementId\": \"2e33b880-3a60-4196-96ed-259e6442d2ab\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"Retirement \",\n" +
                       "        \"currentCost\": 1000000000,\n" +
                       "        \"years\": 10,\n" +
                        "        \"yearlySipIncrease\": 10,\n" +
                        "        \"targetAmount\": 10000,\n" +
               //         "        \"existing\": 50000,\n" +
               //         "        \"future\": 108000,\n" +
                        "        \"target\": 100000,\n" +
                        "        \"assumed\": {\n" +
                        "            \"inflation\": 6,\n" +
                        "            \"returns\": 10\n" +
                        "        },\n" +
                        "        \"allocations\": {\n" +
                        "            \"equity\": 40,\n" +
                        "            \"debt\": 50,\n" +
                        "            \"others\": 10\n" +
                        "        }\n" +
                        "    },\n" +
                        "    {\n" +

                        "        \"requirementId\": \"2e33b880-3a60-4196-96ed-259e6442d2ab\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"Education\",\n" +
                        "        \"currentCost\": 100000,\n" +
                        "        \"years\": 10,\n" +
                        "        \"yearlySipIncrease\": 10,\n" +
                        "        \"targetAmount\": 5000,\n" +
                        "        \"existing\": 50000,\n" +
                        "        \"future\": 100000,\n" +
                        "        \"target\": 100000,\n" +
                        "        \"assumed\": {\n" +
                        "            \"inflation\": 6,\n" +
                        "            \"returns\": 8\n" +
                        "        },\n" +
                        "        \"allocations\": {\n" +
                        "            \"equity\": 99,\n" +
                        "            \"debt\": 1,\n" +
                        "            \"others\": 0\n" +
                        "        }\n" +
                        "    },\n" +

                        "    {\n" +
                        "        \"requirementId\": \"2e33b880-3a60-4196-96ed-259e6442d2ab\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"Retirement12 \",\n" +
                        "        \"currentCost\": 1000000000,\n" +
                        "        \"years\": 10,\n" +
                        "        \"yearlySipIncrease\": 10,\n" +
                        "        \"targetAmount\": 10000,\n" +
                        //         "        \"existing\": 50000,\n" +
                        //         "        \"future\": 108000,\n" +
                        "        \"target\": 100000,\n" +
                        "        \"assumed\": {\n" +
                        "            \"inflation\": 6,\n" +
                        "            \"returns\": 10\n" +
                        "        },\n" +
                        "        \"allocations\": {\n" +
                        "            \"equity\": 40,\n" +
                        "            \"debt\": 50,\n" +
                        "            \"others\": 10\n" +
                        "        }\n" +
                        "    },\n" +

                        "    {\n" +
                        "        \"requirementId\": \"2e33b880-3a60-4196-96ed-259e6442d2ab\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"Marriage\",\n" +
                        "        \"currentCost\": 100000,\n" +
                        "        \"years\": 10,\n" +
                        "        \"yearlySipIncrease\": 10,\n" +
                        "        \"targetAmount\": 5000,\n" +
                        "        \"existing\": 50000,\n" +
                        "        \"future\": 100000,\n" +
                        "        \"target\": 100000,\n" +
                        "        \"assumed\": {\n" +
                        "            \"inflation\": 6,\n" +
                        "            \"returns\": 8\n" +
                        "        },\n" +
                        "        \"allocations\": {\n" +
                        "            \"equity\": 99,\n" +
                        "            \"debt\": 1,\n" +
                        "            \"others\": 0\n" +
                        "        }\n" +
                        "    }\n" +
                        "]");
        res.post("/tools/portfolios/builder/goal-planner")
                .then().log().all().spec(respec);
    }
    @Test
    public void getGoldPlanner(){
        RequestSpecification res=given().spec(req)
              .queryParam("requirementId","f8edfbc0-de03-4959-b477-d4665fd2ec53");
        res.get("/tools/portfolios/builder/goal-planner")
                .then().log().all().spec(respec);
    }
    @Test
    public void getSchemes(){
        RequestSpecification res=given()
                .queryParam("type","safety")                         // high_risk, long_term, short_term, safety
                .spec(req);
        res.get("/tools/portfolios/builder/schemes")
                .then().log().all().spec(respec);

    }
    @Test
    public void BuilderPDFDownload(){
        RequestSpecification res=given()
                .queryParam("requirementId","c4ad3812-b0ff-431b-a713-428b7a24a96a")
                .queryParam("version","longer")
                .spec(req);
        res.get("/tools/portfolios/builder/download")
                .then().log().all().contentType("application/pdf");
    }
    @Test(enabled = true)
    public void testBuilderPDFDownload() throws IOException {
        // Assuming you have a valid 'req' RequestSpecification
        // ...

        RequestSpecification res = given()
                .queryParam("requirementId", "c4ad3812-b0ff-431b-a713-428b7a24a96a")
                .queryParam("version", "longer")
                .spec(req);
           res.get("/tools/portfolios/builder/download")
                   .then().log().all().contentType("application/pdf");

       /* // Verify response status code if needed
        .then().log().all().statusCode(200);*/

     String directoryPath = "download/portfolioBuilder/";
    File directory = new File(directoryPath);        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

    savePDFToFile(res.head().asByteArray(), directoryPath + "Builder.pdf");     // Save the PDF content to a file
    }
    private void savePDFToFile(byte[] pdfContent, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfContent);
        }
    }
}






