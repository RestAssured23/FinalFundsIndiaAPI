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
        basicData.put("name","Testing Investor");
        basicData.put("age",35);
        basicData.put("email","sathi@gmail.com");
        basicData.put("mobile","9790790876");
        basicData.put("location","chennai");
        basicData.put("occupation","vhjnfjh");
        basicData.put("family","kdajhkdaj");
   //     basicData.put("parents","Father");
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
            //    .queryParam("requirementId","23c1a9c6-1ff2-477f-8a89-75e1b5947411")
          //      .queryParam("email","sathi@gmail.com")
               .queryParam("mobile","9790790876");

        res.get("/tools/portfolios/builder/requirements").then().log().all().spec(respec);
    }
    @Test
    public void putRequirement(){
        Map<String,Object> updatePayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","updated investor");
        basicData.put("age",35);
        basicData.put("email","sathi@gmail.com");
        basicData.put("mobile","9790790876");
        basicData.put("location","chennai");
        basicData.put("occupation","Manager");
        basicData.put("family","testing");
 //       basicData.put("parents","Father");
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
        portfolioData.put("debt",0);
        portfolioData.put("emiPercentage",0);

        Map<String,Object> riskData=new HashMap<>();
        riskData.put("profile","string");
        riskData.put("downTrendPortfolio","string");
        riskData.put("duringCovid","string");
        riskData.put("comments","string");
    updatePayload.put("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864");
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
        RequestSpecification res=given().spec(req)
                .queryParam("requirementId","982333a1-0d05-4a02-8bc6-a015bc8942a0");

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
                .queryParam("requirementId","982333a1-0d05-4a02-8bc6-a015bc8942a0");
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
    public void postSafetyBox(){
        Map<String, Object> payload = new HashMap<>();
        payload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("comments", "string");
        // MF
        Map<String, Object> mf = new HashMap<>();
        List<Map<String, Object>> emergencyList = new ArrayList<>();
        Map<String, Object> emergency = new HashMap<>();
        emergency.put("schemeCode", "string");
        emergency.put("schemeName", "string");
        emergency.put("ratings", 4);
        emergency.put("category", "string");
        emergency.put("subCategory", "string");
        emergency.put("sip", 0);
        emergency.put("lumpsum", 0);
        emergencyList.add(emergency);
        mf.put("emergency", emergencyList);
        payload.put("mf", mf);

        // Insurance
        Map<String, Object> insurance = new HashMap<>();
        List<Map<String, Object>> healthList = new ArrayList<>();
        Map<String, Object> health = new HashMap<>();
        health.put("name", "string");
        health.put("companyName", "string");
        health.put("cover", 1);
        health.put("yearly", 1);
        healthList.add(health);
        insurance.put("health", healthList);
        payload.put("insurance", insurance);

        // Rationals
        Map<String, Object> rationals = new HashMap<>();
        List<Map<String, Object>> emergencyRationalsList = new ArrayList<>();
        Map<String, Object> emergencyRational = new HashMap<>();
        emergencyRational.put("schemeCode", "string");
        emergencyRational.put("schemeName", "string");
        emergencyRational.put("category", "string");
        emergencyRational.put("subCategory", "string");
        emergencyRational.put("rationale", "string");
        emergencyRational.put("ratings", 4);
        emergencyRationalsList.add(emergencyRational);

        List<Map<String, Object>> insuranceRationalsList = new ArrayList<>();
        Map<String, Object> insuranceRational = new HashMap<>();
        insuranceRational.put("schemeCode", "string");
        insuranceRational.put("schemeName", "string");
        insuranceRational.put("category", "string");
        insuranceRational.put("subCategory", "string");
        insuranceRational.put("rationale", "string");
        insuranceRational.put("ratings", 5);
        insuranceRationalsList.add(insuranceRational);

        rationals.put("emergency", emergencyRationalsList);
        rationals.put("insurance", insuranceRationalsList);
        payload.put("rationals", rationals);

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/suggested-portfolio/safety-box")
                .then()
                .log()
                .all()
                .spec(respec);
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
    public void postShortTerm(){
        Map<String, Object> safetyPayload = new HashMap<>();
        safetyPayload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
        safetyPayload.put("comments", "String");

        List<Map<String, Object>> listdata = new ArrayList<>();
            Map<String, Object> threeYear = new HashMap<>();
            threeYear.put("schemeCode", "4567");
            threeYear.put("schemeName", "ICICI Prudential Equity Savings Fund");
            threeYear.put("ratings", 1);
            threeYear.put("category", "MF");
            threeYear.put("subCategory", "Blend");
            threeYear.put("sip", 5000);
            threeYear.put("lumpsum", 10000);
         listdata.add(threeYear);

        List<Map<String, Object>> listdata2 = new ArrayList<>();
        Map<String, Object> fiveYear = new HashMap<>();
            fiveYear.put("schemeCode", "1234");
            fiveYear.put("schemeName", "ICICI Prudential BAF");
            fiveYear.put("ratings", 4);
            fiveYear.put("category", "MF");
            fiveYear.put("subCategory", "Quality");
            fiveYear.put("sip", 0);
            fiveYear.put("lumpsum", 0);
        listdata2.add(fiveYear);

        Map<String, Object> rationalPayload = new HashMap<>();

        List<Map<String, Object>> listdata3 = new ArrayList<>();
            Map<String, Object> rThreeYear = new HashMap<>();
                rThreeYear.put("schemeCode", "4567");
                rThreeYear.put("schemeName", "ICICI Prudential Equity Savings Fund");
                rThreeYear.put("category", "string");
                rThreeYear.put("subCategory", "Blend");
                rThreeYear.put("rationale", "rationale");
                rThreeYear.put("ratings", 4);
        listdata3.add(rThreeYear);

        List<Map<String, Object>> listdata4 = new ArrayList<>();
            Map<String, Object> rFiveYear = new HashMap<>();
                rFiveYear.put("schemeCode", "1234");
                rFiveYear.put("schemeName", "ICICI Prudential BAF");
                rFiveYear.put("category", "string");
                rFiveYear.put("subCategory", "Blend");
                rFiveYear.put("rationale", "rational");
                rFiveYear.put("ratings", 6);
        listdata4.add(rFiveYear);

        Map<String, Object> listPayload = new HashMap<>();
        listPayload.put("oneToThreeYears", listdata3);
        listPayload.put("threeToFiveYears", listdata4);
        rationalPayload.put("rationals", listPayload);

        safetyPayload.put("oneToThreeYears", listdata);
        safetyPayload.put("threeToFiveYears", listdata2);
        safetyPayload.put("rationals", listPayload);


        RequestSpecification res=given().spec(req)
                        .body(safetyPayload);
        res.post("/tools/portfolios/builder/suggested-portfolio/short-term-box")
                .then().log().all().spec(respec);
    }
    @Test
    public void getShortTermBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/short-term-box")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void postLongTermBox(){
        Map<String, Object> payload = new HashMap<>();
        // Set values for the outermost fields
        payload.put("presetId", "string");
        payload.put("presetName", "string");
        payload.put("lumpsum", 0);
        payload.put("sip", 0);
        payload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("rebalance", "string");
        payload.put("comment", "string");

        // Create a nested map for "assetAllocation"
        Map<String, Object> assetAllocationMap = new HashMap<>();
        assetAllocationMap.put("equity", 0);
        assetAllocationMap.put("debt", 0);

        // Create a nested map for "portfolioConstruction" inside "assetAllocation"
        Map<String, Object> portfolioConstructionMap = new HashMap<>();
        portfolioConstructionMap.put("equity", "string");
        portfolioConstructionMap.put("debt", "string");

        assetAllocationMap.put("portfolioConstruction", portfolioConstructionMap);
        payload.put("assetAllocation", assetAllocationMap);

        // Create a nested map for "allocations"
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create a nested map for "equity" inside "allocations"
        Map<String, Object> equityMap = new HashMap<>();

        // Create a nested map for "sip" inside "equity"
        Map<String, Object> sipMap = new HashMap<>();
        sipMap.put("amount", 0);
        sipMap.put("percentage", 0);
        sipMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
        lumpsumMap.put("amount", 0);
        lumpsumMap.put("percentage", 0);
        lumpsumMap.put("execution", "string");

        equityMap.put("sip", sipMap);
        equityMap.put("lumpsum", lumpsumMap);

        // Create a list for "schemes" inside "equity"
        List<Map<String, Object>> schemesList = new ArrayList<>();
        Map<String, Object> schemesMap = new HashMap<>();
        schemesMap.put("schemeCode", "string");
        schemesMap.put("schemeName", "string");
        schemesMap.put("ratings", 0);
        schemesMap.put("category", "string");
        schemesMap.put("subCategory", "string");

        // Create a nested map for "sip" inside "schemes"
        Map<String, Object> sipSchemeMap = new HashMap<>();
        sipSchemeMap.put("amount", 0);
        sipSchemeMap.put("percentage", 0);
        sipSchemeMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "schemes"
        Map<String, Object> lumpsumSchemeMap = new HashMap<>();
        lumpsumSchemeMap.put("amount", 0);
        lumpsumSchemeMap.put("percentage", 0);
        lumpsumSchemeMap.put("execution", "string");

        schemesMap.put("sip", sipSchemeMap);
        schemesMap.put("lumpsum", lumpsumSchemeMap);

        schemesList.add(schemesMap);
        equityMap.put("schemes", schemesList);

        allocationsMap.put("equity", equityMap);

        payload.put("allocations", allocationsMap);

        // Create a nested map for "invest"
        Map<String, Object> investMap = new HashMap<>();
        investMap.put("debt", "string");
        investMap.put("equity", "string");
        payload.put("invest", investMap);

        // Create a nested map for "rationals"
        Map<String, Object> rationalsMap = new HashMap<>();

        // Create a list for "equity" inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
        Map<String, Object> equityRationalsMap = new HashMap<>();
        equityRationalsMap.put("schemeCode", "string");
        equityRationalsMap.put("schemeName", "string");
        equityRationalsMap.put("category", "string");
        equityRationalsMap.put("subCategory", "string");
        equityRationalsMap.put("rationale", "string");
        equityRationalsMap.put("ratings", 0);

        equityRationalsList.add(equityRationalsMap);
        rationalsMap.put("equity", equityRationalsList);


        payload.put("rationals", rationalsMap);

        RequestSpecification res=given() .spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/suggested-portfolio/long-term")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void getLongTermBox(){
        RequestSpecification res=given()
                .queryParam("requirementId","3caa0891-a1d4-4f23-8c8f-53a35335c864")
                .spec(req);
        res.get("/tools/portfolios/builder/suggested-portfolio/long-term")
                .then()
                .log()
                .all()
                .spec(respec);
    }
    @Test
    public void postHighRisk(){
        Map<String, Object> payload = new HashMap<>();

        // Set values for the outermost fields
        payload.put("presetId", "string");
        payload.put("presetName", "string");
        payload.put("lumpsum", 0);
        payload.put("sip", 0);
        payload.put("rebalance", "string");
        payload.put("comments", "string");
        payload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("comment", "string");

        // Create a nested map for "allocations"
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create a nested map for "equity" inside "allocations"
        Map<String, Object> equityMap = new HashMap<>();

        // Create a nested map for "sip" inside "equity"
        Map<String, Object> sipMap = new HashMap<>();
        sipMap.put("amount", 0);
        sipMap.put("percentage", 0);
        sipMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
        lumpsumMap.put("amount", 0);
        lumpsumMap.put("percentage", 0);
        lumpsumMap.put("execution", "string");

        equityMap.put("sip", sipMap);
        equityMap.put("lumpsum", lumpsumMap);

        // Create a list for "schemes" inside "equity"
        List<Map<String, Object>> schemesList = new ArrayList<>();
        Map<String, Object> schemesMap = new HashMap<>();
        schemesMap.put("schemeCode", "string");
        schemesMap.put("schemeName", "string");
        schemesMap.put("ratings", 0);
        schemesMap.put("category", "string");
        schemesMap.put("subCategory", "string");

        // Create a nested map for "sip" inside "schemes"
        Map<String, Object> sipSchemeMap = new HashMap<>();
        sipSchemeMap.put("amount", 0);
        sipSchemeMap.put("percentage", 0);
        sipSchemeMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "schemes"
        Map<String, Object> lumpsumSchemeMap = new HashMap<>();
        lumpsumSchemeMap.put("amount", 0);
        lumpsumSchemeMap.put("percentage", 0);
        lumpsumSchemeMap.put("execution", "string");

        schemesMap.put("sip", sipSchemeMap);
        schemesMap.put("lumpsum", lumpsumSchemeMap);

        schemesList.add(schemesMap);
        equityMap.put("schemes", schemesList);

        // Create a nested map for "debt" inside "allocations"
        Map<String, Object> debtMap = new HashMap<>();

        allocationsMap.put("equity", equityMap);
        allocationsMap.put("debt", debtMap);

        payload.put("allocations", allocationsMap);

        // Create a nested map for "rationals"
        Map<String, Object> rationalsMap = new HashMap<>();

        // Create a list for "equity" inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
        Map<String, Object> equityRationalsMap = new HashMap<>();
        equityRationalsMap.put("schemeCode", "string");
        equityRationalsMap.put("schemeName", "string");
        equityRationalsMap.put("category", "string");
        equityRationalsMap.put("subCategory", "string");
        equityRationalsMap.put("rationale", "string");
        equityRationalsMap.put("ratings", 0);

        equityRationalsList.add(equityRationalsMap);
        rationalsMap.put("equity", equityRationalsList);

        payload.put("rationals", rationalsMap);

        // Create a nested map for "invest"
        Map<String, Object> investMap = new HashMap<>();
        investMap.put("debt", "string");
        investMap.put("equity", "string");
        payload.put("invest", investMap);
        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/suggested-portfolio/high-risk")
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
    public void getDiscussionAttachement(){
        RequestSpecification res=given()
                .queryParam("requirementId","fa391ac4-d56a-4359-aef5-3dc0b597ac66")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void postDiscussionAttachement(){

        Map<String,Object> payload=new HashMap<>();
        payload.put("requirementId","fa391ac4-d56a-4359-aef5-3dc0b597ac66");
        payload.put("documentId","1");
        payload.put("name","Wealth");
        payload.put("url","d1yefw7zveme14.cloudfront.net/Advisory_Dashboard/Portfolio_Builder/FundsIndia_Wealth_Conversations.pdf");
        payload.put("category","wealth_conversation");

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void getDiscussion(){
        RequestSpecification res=given()
                .queryParam("requirementId","fa391ac4-d56a-4359-aef5-3dc0b597ac66")
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




