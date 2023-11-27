package core.advisor_Dashboard;
import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static io.restassured.RestAssured.given;
import static java.lang.Double.parseDouble;

public class PortfolioBuilder extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private boolean isCompleted = false;

  // String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTIzODkiLCJzY29wZXMiOiJyZWFkLHdyaXRlIiwibmFtZSI6IlRyaXZlbmkiLCJlbWFpbCI6InRyaS5zaGFyb24wMUBnbWFpbC5jb20iLCJtb2JpbGUiOiI5ODQxNTM0MDk5IiwibWFuYWdlbWVudC11c2VyLWlkIjoxODkwNzQzLCJtYW5hZ2VtZW50LXVzZXItcm9sZXMiOiJhZG1pbiIsImlzcyI6ImZ1bmRzaW5kaWEuY29tIiwianRpIjoiZWQ5MGQ4ZmEtNGFlNy00OWRlLTlmNTUtNzgxMDZlZDBlYzc1IiwiaWF0IjoxNzAwODE2NzU3LCJleHAiOjE3MDA4MjA0MTd9._ohysuc-hwjN6h1W7Mji_28EC1Z4xYvLlSiuoNNHGd306IEGb2dscr2acjlWN-0phuUNugZ9KIwhe6qaEIMERQ";



    public PortfolioBuilder() {
        req = new RequestSpecBuilder()
                .setBaseUri(getADBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", "11")
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

    @Test
    public void postRequirement(){
     Map<String,Object> reqPayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","saravanan E");
        basicData.put("age",35);
        basicData.put("email","sathi@gmail.com");
        basicData.put("mobile","9790790876");
        basicData.put("location","chennai");
        basicData.put("occupation","manager");
        basicData.put("family","testing");
        basicData.put("parents","Father");
    //    basicData.put("spouse","testspouse");

        Map<String,Object> reqData=new HashMap<>();
        reqData.put("description","Education");
        reqData.put("lumpsum",50000);
        reqData.put("sip",2000);
        reqData.put("timeframe","5");

        Map<String,Object> hisData=new HashMap<>();
        hisData.put("investmentExperience",0);
        hisData.put("experienceSoFar","string");
        List<String> list = Arrays.asList("string");
        hisData.put("assetClasses",list);

        Map<String,Object> portfolioData=new HashMap<>();
        portfolioData.put("amount",0);
        portfolioData.put("equity",0);
        portfolioData.put("debt",0);
        portfolioData.put("emiPercentage",0);

        Map<String,Object> riskData=new HashMap<>();
        riskData.put("profile","string");
        riskData.put("downTrendPortfolio","string");
        riskData.put("duringCovid","string");
        riskData.put("comments","string");

      reqPayload.put("basic",basicData);
      reqPayload.put("requirement",reqData);
      reqPayload.put("history",hisData);
      reqPayload.put("portfolio",portfolioData);
      reqPayload.put("risk",riskData);

        RequestSpecification res=given().spec(req)
                        .body(reqPayload);
        res.post("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }
    @Test
    public void getRequirement(){
        RequestSpecification res=given().spec(req)
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .queryParam("email","sathi@gmail.com")
                .queryParam("mobile","9790790876");

        res.get("/tools/portfolios/builder/requirements").then().log().all().spec(respec);
    }
    @Test
    public void putRequirement(){
        Map<String,Object> updatePayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","saravanan E");
        basicData.put("age",35);
        basicData.put("email","sathi@gmail.com");
        basicData.put("mobile","9790790876");
        basicData.put("location","chennai");
        basicData.put("occupation","Manager");
        basicData.put("family","testing");
        basicData.put("parents","Father");
   //     basicData.put("spouse","testspouse");

        Map<String,Object> reqData=new HashMap<>();
        reqData.put("description","Education");
        reqData.put("lumpsum",20000);
        reqData.put("sip",2000);
        reqData.put("timeframe","5");

        Map<String,Object> hisData=new HashMap<>();
        hisData.put("investmentExperience",0);
        hisData.put("experienceSoFar","string");
        List<String> list = Arrays.asList("string");
        hisData.put("assetClasses",list);

        Map<String,Object> portfolioData=new HashMap<>();
        portfolioData.put("amount",0);
        portfolioData.put("equity",0);
        portfolioData.put("debt","");
        portfolioData.put("emiPercentage",0);

        Map<String,Object> riskData=new HashMap<>();
        riskData.put("profile","string");
        riskData.put("downTrendPortfolio","string");
        riskData.put("duringCovid","string");
        riskData.put("comments","string");

    updatePayload.put("requirementId","fa391ac4-d56a-4359-aef5-3dc0b597ac66");
    updatePayload.put("basic",basicData);
    updatePayload.put("requirement",reqData);
    updatePayload.put("history",hisData);
    updatePayload.put("portfolio",portfolioData);
    updatePayload.put("risk",riskData);

        RequestSpecification res=given().spec(req)
                .body(updatePayload);
        res.put("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteRequirement(){
        RequestSpecification res=given()
                .queryParam("requirementId ","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.delete("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }

    @Test
    public void postMoney_Box(){
        Map<String,Object>moneyBoxPayload=new HashMap<>();
        moneyBoxPayload.put("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
        moneyBoxPayload.put("type","less_than_30");           //[ less_than_30, greater_or_equal_to_30 ]
        moneyBoxPayload.put("option1",true);
        moneyBoxPayload.put("option2",false);
        moneyBoxPayload.put("option3",true);
        moneyBoxPayload.put("option4","Equity");
        moneyBoxPayload.put("option5","Debt");

        RequestSpecification res=given().spec(req)
                .body(moneyBoxPayload);
        res.post("/tools/portfolios/builder/money-box").then().log().all().spec(respec);
    }
    @Test
    public void getMoney_Box(){
        RequestSpecification res=given().spec(req)
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
        res.get("/tools/portfolios/builder/money-box").then().log().all().spec(respec);

    }
/*    @Test
    public void putMoney_Box(){
        Map<String,Object>moneyBoxPayload=new HashMap<>();
        moneyBoxPayload.put("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
        moneyBoxPayload.put("type","less_than_30");           //[ less_than_30, greater_or_equal_to_30 ]
        moneyBoxPayload.put("option1",true);
        moneyBoxPayload.put("option2",true);
        moneyBoxPayload.put("option3",true);
        moneyBoxPayload.put("option4","Equity");
        moneyBoxPayload.put("option5","Debt");

        RequestSpecification res=given().spec(req)
                .body(moneyBoxPayload);
        res.put("/tools/portfolios/builder/money-box").then().log().all().spec(respec);
    }*/

    @Test
    public void postWealth_Equation(){
        Map<String,Object> payload=new HashMap<>();
        payload.put("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("current",50000);
        payload.put("sip",1000);
        payload.put("yearlyLumpsum",10000);
        payload.put("yearlyIncreaseLumpsum",10);
        payload.put("yearlyIncreaseSip",0);
        payload.put("type","total_investment");     //[ total_investment, one_time_investment, monthly_savings, yearly_one_time_investment ]

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/wealth-equation").then().log().all().spec(respec);
    }

    @Test
    public void getWealth_Equation(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864").spec(req);
        res.get("/tools/portfolios/builder/wealth-equation").then().log().all().spec(respec);
    }

    @Test
    public void getSafetyBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/safety-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void postSafetyBox(){
        RequestSpecification res=given().spec(req)
                .body("{\n" +
                        "  \"requirementId\": \"3caa0891-a1d4-4f23-8c8f-53a35335c864\",\n" +
                        "  \"mf\": {\n" +
                        "    \"emergency\": [\n" +
                        "      {\n" +
                        "        \"schemeCode\": \"9876\",\n" +
                        "        \"schemeName\": \"Kotak Arbitage Fund\",\n" +
                        "        \"ratings\": 5,\n" +
                        "        \"category\": \"MF\",\n" +
                        "        \"subCategory\": \"Qualtity\",\n" +
                        "        \"sip\": 1000,\n" +
                        "        \"lumpsum\": 2000\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"insurance\": {\n" +
                        "    \"health\": [\n" +
                        "      {\n" +
                        "        \"name\": \"name\",\n" +
                        "        \"companyName\": \"HDFC Ergo Term Plan\",\n" +
                        "        \"cover\": 100,\n" +
                        "        \"yearly\": 5\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"rationals\": {\n" +
                        "    \"emergency\": [\n" +
                        "      {\n" +
                        "        \"schemeCode\": \"9876\",\n" +
                        "        \"schemeName\": \"Kotak Arbitage Fund\",\n" +
                        "        \"category\": \"MF\",\n" +
                        "        \"subCategory\": \"string\",\n" +
                        "        \"rationale\": \"string\",\n" +
                        "        \"ratings\": 2\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"insurance\": [\n" +
                        "      {\n" +
                        "        \"schemeCode\": \"9876\",\n" +
                        "        \"schemeName\": \"HDFC Ergo Term Plan\",\n" +
                        "        \"category\": \"insurance\",\n" +
                        "        \"subCategory\": \"Value\",\n" +
                        "        \"rationale\": \"rationale\",\n" +
                        "        \"ratings\": 1\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"comments\": \"string\"\n" +
                        "}");
        res.post("/tools/portfolios/builder/suggested-portfolio/safety-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void getShortTermBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/short-term-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void getHighRisk(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/high-risk")
                .then().log().all().spec(respec);
    }
    @Test
    public void getLongTerm(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/long-term")
                .then().log().all().spec(respec);
    }

    @Test
    public void getDiscussionAttachement(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void postDiscussionAttachement(){
        String url="https://data-team-dumps.s3.ap-south-1.amazonaws.com/advisory_dashboard/portfolio-builder/track_record/202310+-+FundsIndia+Research+-+Performance+Track+Record.pdf";
        Map<String,Object> payload=new HashMap<>();
        payload.put("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("documentId","5");
        payload.put("name","Track Record");
        payload.put("url",url);
        payload.put("category","track_record");

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void getDiscussion(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion")
                .then().log().all().spec(respec);
    }
    @Test
    public void DeleteDiscussionAttachement(){
        RequestSpecification res=given().spec(req)
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .queryParam("documentId","5");
        res.delete("/tools/portfolios/builder/discussion/attachments")
                .then()
                .log().all()
                .spec(respec);
    }
    @Test
    public void BuilderPDFDownload(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/download")
                .then().log().all().spec(respec);
    }
}




