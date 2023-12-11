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
        basicData.put("name","post test");
        basicData.put("age",40);
        basicData.put("email","post@gmail.com");
        basicData.put("mobile","+61700000001");
        basicData.put("location","chennai");
        basicData.put("occupation","occupation");
        basicData.put("family","family Name");
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
              .queryParam("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed");
          //     .queryParam("email","post1@gmail.com");
         //      .queryParam("mobile","6000000000");

        res.get("/tools/portfolios/builder/requirements").then().log().all().spec(respec);
    }
    @Test
    public void putRequirement(){
        Map<String,Object> updatePayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","updated post investor");
        basicData.put("age",35);
        basicData.put("email","post1@gmail.com");
        basicData.put("mobile","6000000001");
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
    updatePayload.put("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed");
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
        RequestSpecification res=given().spec(req);
            //    .queryParam("requirementId","982333a1-0d05-4a02-8bc6-a015bc8942a0");

        res.delete("/tools/portfolios/builder/requirements")
                .then().log().all().spec(respec);
    }

    @Test
    public void postMoney_Box(){
        Map<String,Object>moneyBoxPayload=new HashMap<>();
        moneyBoxPayload.put("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed");
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
                .queryParam("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed");
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
        payload.put("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed");
        payload.put("current",50000);
        payload.put("sip",1000);
        payload.put("yearlyLumpsum",100);
        payload.put("yearlyIncreaseLumpsum",90);
        payload.put("yearlyIncreaseSip",20);
        List<Integer> data=new ArrayList<>();
        data.add(5);
        payload.put("years",data);
        payload.put("type","total_investment");     //[ total_investment, one_time_investment, monthly_savings, yearly_one_time_investment ]

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/wealth-equation").then().log().all().spec(respec);
    }

    @Test
    public void getWealth_Equation(){
        RequestSpecification res=given()
                .queryParam("requirementId","d38070c5-d440-49d6-bd69-1c15fe2f56ed").spec(req);
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
            emergency.put("schemeCode", "8029");
            emergency.put("schemeName", "Axis Liquid Fund-Reg(G)");
            emergency.put("ratings", 5);
            emergency.put("category", "Liquid");
            emergency.put("subCategory", "Debt - Liquid Fund");
            emergency.put("sip", 1000);
            emergency.put("lumpsum", 10000);
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
            emergencyRational.put("ratings", 5);
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
     //   payload.put("rationals", rationals);

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
        safetyPayload.put("comments", "Testing");
//1-3 Years
        List<Map<String, Object>> One_ThreeYears = new ArrayList<>();
            Map<String, Object> onescheme_data = new HashMap<>();
            onescheme_data.put("schemeCode", "468");
            onescheme_data.put("schemeName", "Aditya Birla SL Savings Fund-Reg(G)");
            onescheme_data.put("ratings", 5);
            onescheme_data.put("category", "Debt");
            onescheme_data.put("subCategory", "Debt - Ultra Short Duration Fund");
            onescheme_data.put("sip", 5000);
            onescheme_data.put("lumpsum", 10000);
         One_ThreeYears.add(onescheme_data);
//3-5 Years
        List<Map<String, Object>> Three_FiveYears = new ArrayList<>();
            Map<String, Object> threescheme_data2 = new HashMap<>();
            threescheme_data2.put("schemeCode", "31230");
            threescheme_data2.put("schemeName", "ICICI Pru Equity Savings Fund(G)");
            threescheme_data2.put("ratings", 5);
            threescheme_data2.put("category", "Hybrid Others");
            threescheme_data2.put("subCategory", "Hybrid - Equity Savings");
            threescheme_data2.put("sip", 0);
            threescheme_data2.put("lumpsum", 0);
        Three_FiveYears.add(threescheme_data2);

//Rational Data
        List<Map<String, Object>> Rationla_OneThreeYears = new ArrayList<>();
        Map<String, Object> OnerationalData = new HashMap<>();
            OnerationalData.put("schemeCode", "4567");
            OnerationalData.put("schemeName", "ICICI Prudential Equity Savings Fund");
            OnerationalData.put("ratings", 1);
            OnerationalData.put("category", "MF");
            OnerationalData.put("subCategory", "Blend");
            OnerationalData.put("sip", 5000);
            OnerationalData.put("lumpsum", 10000);
        Rationla_OneThreeYears.add(OnerationalData);

        List<Map<String, Object>> Rational_ThreeFiveYears = new ArrayList<>();
            Map<String, Object> fiverationalData = new HashMap<>();
            fiverationalData.put("schemeCode", "1234");
            fiverationalData.put("schemeName", "ICICI Prudential BAF");
            fiverationalData.put("ratings", 4);
            fiverationalData.put("category", "MF");
            fiverationalData.put("subCategory", "Quality");
            fiverationalData.put("sip", 0);
            fiverationalData.put("lumpsum", 0);
        Rational_ThreeFiveYears.add(fiverationalData);

        safetyPayload.put("oneToThreeYears", One_ThreeYears);
        safetyPayload.put("threeToFiveYears", Three_FiveYears);

        Map<String, Object> rationalPayload = new HashMap<>();
            rationalPayload.put("oneToThreeYears",Rationla_OneThreeYears);
            rationalPayload.put("threeToFiveYears",Rational_ThreeFiveYears);
      //  safetyPayload.put("rationals",rationalPayload);

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
        Map<String, Object> longTermPayload = new HashMap<>();
            longTermPayload.put("presetId", "string");
            longTermPayload.put("presetName", "string");
            longTermPayload.put("lumpsum", 0);
            longTermPayload.put("sip", 0);
            longTermPayload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
            longTermPayload.put("rebalance", "string");
            longTermPayload.put("comment", "string");

 //"assetAllocation"
        Map<String, Object> assetAllocationMap = new HashMap<>();
        assetAllocationMap.put("equity", 80);
        assetAllocationMap.put("debt", 20);

   //"portfolioConstruction" inside "assetAllocation"
        Map<String, Object> portfolioConstructionMap = new HashMap<>();
        portfolioConstructionMap.put("equity", "Testing");
        portfolioConstructionMap.put("debt", "Equity Savings");

        assetAllocationMap.put("portfolioConstruction", portfolioConstructionMap);
        longTermPayload.put("assetAllocation", assetAllocationMap);

 // "allocations"
        Map<String, Object> allocationsMap = new HashMap<>();

  // "EQUITY" inside "allocations"
        Map<String, Object> equityMap = new HashMap<>();
        // "sip" inside "equity"
        Map<String, Object> sipMap = new HashMap<>();
            sipMap.put("amount", 10000);
            sipMap.put("percentage", 70);
            sipMap.put("execution", "execution Testing");
        //"lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
            lumpsumMap.put("amount", 20000);
            lumpsumMap.put("percentage",10 );
            lumpsumMap.put("execution", "Lumpsum");

        equityMap.put("sip", sipMap);
        equityMap.put("lumpsum", lumpsumMap);

    // "schemes" inside "equity"
        List<Map<String, Object>> schemesList = new ArrayList<>();
        Map<String, Object> schemesMap = new HashMap<>();
        schemesMap.put("schemeCode", "938");
        schemesMap.put("schemeName", "Franklin India Focused Equity Fund(G)");
        schemesMap.put("ratings", 5);
        schemesMap.put("category", "Equity");
        schemesMap.put("subCategory", "Equity - Focused Fund");
    // "sip" inside "schemes"
        Map<String, Object> sipSchemeMap = new HashMap<>();
        sipSchemeMap.put("amount", 2000);
        sipSchemeMap.put("percentage", 10);
        sipSchemeMap.put("execution", "One scheme");
    //"lumpsum" inside "schemes"
        Map<String, Object> lumpsumSchemeMap = new HashMap<>();
        lumpsumSchemeMap.put("amount", 10000);
        lumpsumSchemeMap.put("percentage", 20);
        lumpsumSchemeMap.put("execution", "OTI Equity");

        schemesMap.put("sip", sipSchemeMap);
        schemesMap.put("lumpsum", lumpsumSchemeMap);

        schemesList.add(schemesMap);
        equityMap.put("schemes", schemesList);
 //"DEBT" inside "allocations"
        Map<String, Object> debtMap = new HashMap<>();
        // "sip" inside "debt"
        Map<String, Object> debtsipMap = new HashMap<>();
        debtsipMap.put("amount", 10000);
        debtsipMap.put("percentage", 70);
        debtsipMap.put("execution", "execution Testing");
        //"lumpsum" inside "debt"
        Map<String, Object> otilumpsumMap = new HashMap<>();
        otilumpsumMap.put("amount", 20000);
        otilumpsumMap.put("percentage",10 );
        otilumpsumMap.put("execution", "Lumpsum");

        debtMap.put("sip", debtsipMap);
        debtMap.put("lumpsum", otilumpsumMap);

        // "schemes" inside "debt"
        List<Map<String, Object>> debtschemesList = new ArrayList<>();
        Map<String, Object> debtschemesMap = new HashMap<>();
        debtschemesMap.put("schemeCode", "453");
        debtschemesMap.put("schemeName", "Aditya Birla SL Corp Bond Fund(G)");
        debtschemesMap.put("ratings", 5);
        debtschemesMap.put("category", "Debt");
        debtschemesMap.put("subCategory", "Debt - Corporate Bond Fund");
        // "sip" inside "schemes"
        Map<String, Object> debtsipSchemeMap = new HashMap<>();
        debtsipSchemeMap.put("amount", 2000);
        debtsipSchemeMap.put("percentage", 10);
        debtsipSchemeMap.put("execution", "One scheme");
        //"lumpsum" inside "schemes"
        Map<String, Object> debtlumpsumSchemeMap = new HashMap<>();
        debtlumpsumSchemeMap.put("amount", 10000);
        debtlumpsumSchemeMap.put("percentage", 20);
        debtlumpsumSchemeMap.put("execution", "OTI Debt");

        debtschemesMap.put("sip", debtsipSchemeMap);
        debtschemesMap.put("lumpsum", debtlumpsumSchemeMap);

        debtschemesList.add(debtschemesMap);
        debtMap.put("schemes", debtschemesList);

        allocationsMap.put("equity", equityMap);
        allocationsMap.put("debt", debtMap);
    longTermPayload.put("allocations", allocationsMap);

    //"invest"
        Map<String, Object> investMap = new HashMap<>();
        investMap.put("debt", "20000");
        investMap.put("equity", "10000");
    longTermPayload.put("invest", investMap);

    // Create a nested map for "rationals"
        Map<String, Object> rationalsMap = new HashMap<>();

    //"equity" inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
            Map<String, Object> equityRationalsMap = new HashMap<>();
            equityRationalsMap.put("schemeCode", "string");
            equityRationalsMap.put("schemeName", "string");
            equityRationalsMap.put("category", "equity");
            equityRationalsMap.put("subCategory", "string");
            equityRationalsMap.put("rationale", "string");
            equityRationalsMap.put("ratings",3);
        equityRationalsList.add(equityRationalsMap);

    //"debt" inside "rationals"
        List<Map<String, Object>> debtRationalsList = new ArrayList<>();
        Map<String, Object> debtRationalsMap = new HashMap<>();
        debtRationalsMap.put("schemeCode", "string");
        debtRationalsMap.put("schemeName", "string");
        debtRationalsMap.put("category", "debt");
        debtRationalsMap.put("subCategory", "string");
        debtRationalsMap.put("rationale", "string");
        debtRationalsMap.put("ratings",3);
        debtRationalsList.add(debtRationalsMap);

        rationalsMap.put("equity", equityRationalsList);
        rationalsMap.put("debt", debtRationalsList);

  //  longTermPayload.put("rationals", rationalsMap);

        RequestSpecification res=given() .spec(req)
                .body(longTermPayload);
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
        payload.put("lumpsum", 10000);
        payload.put("sip", 2000);
        payload.put("rebalance", "string");
        payload.put("comments", "string");
        payload.put("requirementId", "3caa0891-a1d4-4f23-8c8f-53a35335c864");
        payload.put("comment", "string");

    // Create a nested map for "allocations"
        Map<String, Object> allocationsMap = new HashMap<>();

    // Create a nested map for "equity" inside "allocations"
        Map<String, Object> equityMap = new HashMap<>();

    //"sip" inside "equity"
        Map<String, Object> sipMap = new HashMap<>();
        sipMap.put("amount", 0);
        sipMap.put("percentage", 0);
        sipMap.put("execution", "string");

    //"lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
        lumpsumMap.put("amount", 0);
        lumpsumMap.put("percentage", 0);
        lumpsumMap.put("execution", "string");

        equityMap.put("sip", sipMap);
        equityMap.put("lumpsum", lumpsumMap);

    //Create a list for "schemes" inside "equity"
        List<Map<String, Object>> schemesList = new ArrayList<>();
        Map<String, Object> schemesMap = new HashMap<>();
        schemesMap.put("schemeCode", "948");
        schemesMap.put("schemeName", "Franklin India Prima Fund(G)");
        schemesMap.put("ratings", 5);
        schemesMap.put("category", "Equity");
        schemesMap.put("subCategory", "Equity - Mid Cap Fund");

        // Create a nested map for "sip" inside "schemes"
        Map<String, Object> sipSchemeMap = new HashMap<>();
        sipSchemeMap.put("amount", 1000);
        sipSchemeMap.put("percentage", 60);
        sipSchemeMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "schemes"
        Map<String, Object> lumpsumSchemeMap = new HashMap<>();
        lumpsumSchemeMap.put("amount", 3000);
        lumpsumSchemeMap.put("percentage", 50);
        lumpsumSchemeMap.put("execution", "string");

        schemesMap.put("sip", sipSchemeMap);
        schemesMap.put("lumpsum", lumpsumSchemeMap);

        schemesList.add(schemesMap);
        equityMap.put("schemes", schemesList);

    // Create a nested map for "debt" inside "allocations"
        Map<String, Object> debtMap = new HashMap<>();
        //"sip" inside "debt"
        Map<String, Object> debtsipMap = new HashMap<>();
        debtsipMap.put("amount", 0);
        debtsipMap.put("percentage", 0);
        debtsipMap.put("execution", "string");

        //"lumpsum" inside "debt"
        Map<String, Object> debtlumpsumMap = new HashMap<>();
        debtlumpsumMap.put("amount", 0);
        debtlumpsumMap.put("percentage", 0);
        debtlumpsumMap.put("execution", "string");

        debtMap.put("sip", debtsipMap);
        debtMap.put("lumpsum", debtlumpsumMap);

        //Create a list for "schemes" inside "equity"
        List<Map<String, Object>> debtschemesList = new ArrayList<>();
        Map<String, Object> debtschemesMap = new HashMap<>();
        debtschemesMap.put("schemeCode", "948");
        debtschemesMap.put("schemeName", "Franklin India Prima Fund(G)");
        debtschemesMap.put("ratings", 5);
        debtschemesMap.put("category", "Equity");
        debtschemesMap.put("subCategory", "Equity - Mid Cap Fund");

        // Create a nested map for "sip" inside "schemes"
        Map<String, Object> debtsipSchemeMap = new HashMap<>();
        debtsipSchemeMap.put("amount", 1000);
        debtsipSchemeMap.put("percentage", 60);
        debtsipSchemeMap.put("execution", "string");

        // Create a nested map for "lumpsum" inside "schemes"
        Map<String, Object> debtlumpsumSchemeMap = new HashMap<>();
        debtlumpsumSchemeMap.put("amount", 3000);
        debtlumpsumSchemeMap.put("percentage", 50);
        debtlumpsumSchemeMap.put("execution", "string");

        debtschemesMap.put("sip", debtsipSchemeMap);
        debtschemesMap.put("lumpsum", debtlumpsumSchemeMap);

        debtschemesList.add(debtschemesMap);
        debtMap.put("schemes", debtschemesList);

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

        List<Map<String, Object>> debtRationalsList = new ArrayList<>();
            Map<String, Object> debtRationalsMap = new HashMap<>();
            debtRationalsMap.put("schemeCode", "string");
            debtRationalsMap.put("schemeName", "string");
            debtRationalsMap.put("category", "string");
            debtRationalsMap.put("subCategory", "string");
            debtRationalsMap.put("rationale", "string");
            debtRationalsMap.put("ratings", 0);

        debtRationalsList.add(debtRationalsMap);
        equityRationalsList.add(equityRationalsMap);
        rationalsMap.put("debt", debtRationalsList);
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
    public void getDiscussion() {
        RequestSpecification res = given()
                       .queryParam("requirementId","fa391ac4-d56a-4359-aef5-3dc0b597ac66")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion")
                .then().log().all().spec(respec);
    }

    @Test
    public void postDiscussionAttachement(){

        Map<String,Object> payload=new HashMap<>();
        payload.put("requirementId","8ad2c916-8761-42a3-9d5d-007910bdbdee");
        payload.put("documentId","2");
        payload.put("name","SIP Conversion");
        payload.put("url","d1yefw7zveme14.cloudfront.net/Advisory_Dashboard/Portfolio_Builder/SIP_Conversations.pdf");
        payload.put("category","sip_conversation");

        RequestSpecification res=given().spec(req)
                .body(payload);
        res.post("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }
    @Test
    public void getDiscussionAttachement(){
        RequestSpecification res=given()
                      .queryParam("requirementId","8ad2c916-8761-42a3-9d5d-007910bdbdee")
                .spec(req);
        res.get("/tools/portfolios/builder/discussion/attachments")
                .then().log().all().spec(respec);
    }


    @Test
    public void DeleteDiscussionAttachement(){
        RequestSpecification res=given().spec(req)
              .queryParam("requirementId","8ad2c916-8761-42a3-9d5d-007910bdbdee")
               .queryParam("documentId","3");
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
    @Test
    public void postLongTermPreset(){
        Map<String, Object> jsonPayload = new HashMap<>();
       //     jsonPayload.put("presetId", "");
            jsonPayload.put("presetName", "Sathish DS");
       //    jsonPayload.put("lumpsum", 10000);
       //    jsonPayload.put("sip", 1000);

        // Create "assetAllocation" map
        Map<String, Object> assetAllocationMap = new HashMap<>();
        assetAllocationMap.put("equity", 80);
        assetAllocationMap.put("debt", 20);

        // Create "portfolioConstruction" map
        Map<String, Object> portfolioConstructionMap = new HashMap<>();
        portfolioConstructionMap.put("equity", "Five finger ");
        portfolioConstructionMap.put("debt", "Saving Fund");

        assetAllocationMap.put("portfolioConstruction", portfolioConstructionMap);
            jsonPayload.put("assetAllocation", assetAllocationMap);

        // Create "allocations" map
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create "equity" map
        Map<String, Object> equityMap = new HashMap<>();

        // Create "sip" map inside "equity"
        Map<String, Object> sipMapEquity = new HashMap<>();
            sipMapEquity.put("amount", 1000);
            sipMapEquity.put("percentage", 1000);
            sipMapEquity.put("execution", "string");

        // Create "lumpsum" map inside "equity"
        Map<String, Object> lumpsumMapEquity = new HashMap<>();
            lumpsumMapEquity.put("amount", 10000);
            lumpsumMapEquity.put("percentage", 10);
            lumpsumMapEquity.put("execution", "string");

        // Create "schemes" list inside "equity"
        List<Map<String, Object>> schemesListEquity = new ArrayList<>();
        Map<String, Object> schemeMapEquity = new HashMap<>();
            schemeMapEquity.put("schemeCode", "string");
            schemeMapEquity.put("schemeName", "string");
            schemeMapEquity.put("ratings", 0);
            schemeMapEquity.put("category", "Equity");
            schemeMapEquity.put("subCategory", "string");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapScheme = new HashMap<>();
            sipMapScheme.put("amount", 0);
            sipMapScheme.put("percentage", 0);
            sipMapScheme.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapScheme = new HashMap<>();
            lumpsumMapScheme.put("amount", 0);
            lumpsumMapScheme.put("percentage", 0);
            lumpsumMapScheme.put("execution", "string");

        schemeMapEquity.put("sip", sipMapScheme);
        schemeMapEquity.put("lumpsum", lumpsumMapScheme);

        schemesListEquity.add(schemeMapEquity);

        equityMap.put("sip", sipMapEquity);
        equityMap.put("lumpsum", lumpsumMapEquity);
        equityMap.put("schemes", schemesListEquity);

       allocationsMap.put("equity", equityMap);

        // Create "debt" map
        Map<String, Object> debtMap = new HashMap<>();

        // Create "sip" map inside "debt"
        Map<String, Object> sipMapDebt = new HashMap<>();
        sipMapDebt.put("amount", 1000);
        sipMapDebt.put("percentage", 10);
        sipMapDebt.put("execution", "string");

        // Create "lumpsum" map inside "debt"
        Map<String, Object> lumpsumMapDebt = new HashMap<>();
        lumpsumMapDebt.put("amount", 10000);
        lumpsumMapDebt.put("percentage", 10);
        lumpsumMapDebt.put("execution", "string");

        // Create "schemes" list inside "debt"
        List<Map<String, Object>> schemesListDebt = new ArrayList<>();
        Map<String, Object> schemeMapDebt = new HashMap<>();
        schemeMapDebt.put("schemeCode", "string");
        schemeMapDebt.put("schemeName", "string");
        schemeMapDebt.put("ratings", 0);
        schemeMapDebt.put("category", "Debt");
        schemeMapDebt.put("subCategory", "string");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapSchemeDebt = new HashMap<>();
        sipMapSchemeDebt.put("amount", 0);
        sipMapSchemeDebt.put("percentage", 0);
        sipMapSchemeDebt.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapSchemeDebt = new HashMap<>();
        lumpsumMapSchemeDebt.put("amount", 0);
        lumpsumMapSchemeDebt.put("percentage", 0);
        lumpsumMapSchemeDebt.put("execution", "string");

        schemeMapDebt.put("sip", sipMapSchemeDebt);
        schemeMapDebt.put("lumpsum", lumpsumMapSchemeDebt);

        schemesListDebt.add(schemeMapDebt);

        debtMap.put("sip", sipMapDebt);
        debtMap.put("lumpsum", lumpsumMapDebt);
        debtMap.put("schemes", schemesListDebt);

        allocationsMap.put("debt", debtMap);

        jsonPayload.put("allocations", allocationsMap);

        RequestSpecification res=given().spec(req)
                .body(jsonPayload);
        res.post("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void getLongTermPreset(){
        RequestSpecification res=given().spec(req)
                        .queryParam("presetId","b31fcf13-a6eb-420f-9ca8-671f961d0a40");
        res.get("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteLongTermPreset(){
        RequestSpecification res=given().spec(req)
           .queryParam("presetId","b31fcf13-a6eb-420f-9ca8-671f961d0a40");
        res.delete("/tools/portfolios/builder/suggested-portfolio/long-term/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void postHighRiskPreset(){
        Map<String, Object> jsonPayload = new HashMap<>();
        jsonPayload.put("presetId", "");
        jsonPayload.put("presetName", "High Risk Preset");
        jsonPayload.put("lumpsum", 10000);
        jsonPayload.put("sip", 1000);

        // Create "allocations" map
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create "equity" map
        Map<String, Object> equityMap = new HashMap<>();

        // Create "sip" map inside "equity"
        Map<String, Object> sipMapEquity = new HashMap<>();
        sipMapEquity.put("amount", 1000);
        sipMapEquity.put("percentage", 90);
        sipMapEquity.put("execution", "string");

        // Create "lumpsum" map inside "equity"
        Map<String, Object> lumpsumMapEquity = new HashMap<>();
        lumpsumMapEquity.put("amount", 10000);
        lumpsumMapEquity.put("percentage", 90);
        lumpsumMapEquity.put("execution", "string");

        // Create "schemes" list inside "equity"
        List<Map<String, Object>> schemesListEquity = new ArrayList<>();
        Map<String, Object> schemeMapEquity = new HashMap<>();
        schemeMapEquity.put("schemeCode", "1023");
        schemeMapEquity.put("schemeName", "Aditiya Birla");
        schemeMapEquity.put("ratings", 3);
        schemeMapEquity.put("category", "Equity");
        schemeMapEquity.put("subCategory", "string");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapScheme = new HashMap<>();
        sipMapScheme.put("amount", 1000);
        sipMapScheme.put("percentage", 99);
        sipMapScheme.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapScheme = new HashMap<>();
        lumpsumMapScheme.put("amount", 0);
        lumpsumMapScheme.put("percentage", 99);
        lumpsumMapScheme.put("execution", "string");

        schemeMapEquity.put("sip", sipMapScheme);
        schemeMapEquity.put("lumpsum", lumpsumMapScheme);

        schemesListEquity.add(schemeMapEquity);

        equityMap.put("sip", sipMapEquity);
        equityMap.put("lumpsum", lumpsumMapEquity);
        equityMap.put("schemes", schemesListEquity);

        // Create "debt" map
        // Follow similar steps as for "equity"...

        allocationsMap.put("equity", equityMap);

        // Create "debt" map
        Map<String, Object> debtMap = new HashMap<>();

        // Create "sip" map inside "debt"
        Map<String, Object> sipMapDebt = new HashMap<>();
        sipMapDebt.put("amount", 0);
        sipMapDebt.put("percentage", 0);
        sipMapDebt.put("execution", "string");

        // Create "lumpsum" map inside "debt"
        Map<String, Object> lumpsumMapDebt = new HashMap<>();
        lumpsumMapDebt.put("amount", 10000);
        lumpsumMapDebt.put("percentage", 83);
        lumpsumMapDebt.put("execution", "string");

        // Create "schemes" list inside "debt"
        List<Map<String, Object>> schemesListDebt = new ArrayList<>();
        Map<String, Object> schemeMapDebt = new HashMap<>();
        schemeMapDebt.put("schemeCode", "1234");
        schemeMapDebt.put("schemeName", "SBI Debt Fund");
        schemeMapDebt.put("ratings", 0);
        schemeMapDebt.put("category", "Debt");
        schemeMapDebt.put("subCategory", "string");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapSchemeDebt = new HashMap<>();
        sipMapSchemeDebt.put("amount", 0);
        sipMapSchemeDebt.put("percentage", 0);
        sipMapSchemeDebt.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapSchemeDebt = new HashMap<>();
        lumpsumMapSchemeDebt.put("amount", 0);
        lumpsumMapSchemeDebt.put("percentage", 0);
        lumpsumMapSchemeDebt.put("execution", "string");

        schemeMapDebt.put("sip", sipMapSchemeDebt);
        schemeMapDebt.put("lumpsum", lumpsumMapSchemeDebt);

        schemesListDebt.add(schemeMapDebt);

        debtMap.put("sip", sipMapDebt);
        debtMap.put("lumpsum", lumpsumMapDebt);
        debtMap.put("schemes", schemesListDebt);

        allocationsMap.put("debt", debtMap);

        jsonPayload.put("allocations", allocationsMap);

        // Create "rationals" map
        Map<String, Object> rationalsMap = new HashMap<>();

        // Create "equity" list inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
        Map<String, Object> equityRationalMap = new HashMap<>();
        equityRationalMap.put("schemeCode", "string");
        equityRationalMap.put("schemeName", "string");
        equityRationalMap.put("category", "Equity");
        equityRationalMap.put("subCategory", "string");
        equityRationalMap.put("rationale", "string");
        equityRationalMap.put("ratings", 5);

        equityRationalsList.add(equityRationalMap);
        rationalsMap.put("equity", equityRationalsList);

        // Create "debt" list inside "rationals"
        List<Map<String, Object>> debtRationalsList = new ArrayList<>();
        Map<String, Object> debtRationalMap = new HashMap<>();
        debtRationalMap.put("schemeCode", "string");
        debtRationalMap.put("schemeName", "string");
        debtRationalMap.put("category", "Debt");
        debtRationalMap.put("subCategory", "string");
        debtRationalMap.put("rationale", "string");
        debtRationalMap.put("ratings", 0);

        debtRationalsList.add(debtRationalMap);
        rationalsMap.put("debt", debtRationalsList);

        jsonPayload.put("rationals", rationalsMap);

        // Add remaining fields like "rebalance", "comments", etc.
        jsonPayload.put("rebalance", "string");
        jsonPayload.put("comments", "string");
        RequestSpecification res=given().spec(req)
                .body(jsonPayload);
        res.post("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void getHighRiskPreset(){
        RequestSpecification res=given().spec(req)
                .queryParam("presetId","0390e065-cf69-4614-aa37-3e0161c95acb");
        res.get("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }
    @Test
    public void deleteHighRiskPreset(){
        RequestSpecification res=given().spec(req)
               .queryParam("presetId","59869c7e-24b9-46e3-90bc-b8d5f5ec7c3b");
        res.delete("/tools/portfolios/builder/suggested-portfolio/high-risk/presets")
                .then().log().all().spec(respec);
    }

    @Test
    public void postGoldPlanner(){
        RequestSpecification res=given().spec(req)
                .body("[\n" +
                        "    {\n" +
                        "        \"requirementId\": \"5efd4598-6abf-4d63-99bf-8d2428813ee1\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"First goal\",\n" +
                 //       "        \"currentCost\": 1000000,\n" +
                       "        \"years\": 10,\n" +
                 //       "        \"yearlySipIncrease\": 99,\n" +
                        "        \"targetAmount\": 1791000,\n" +
               //         "        \"existing\": 50000,\n" +
               //         "        \"future\": 108000,\n" +
                        "        \"target\": 1683000,\n" +
                        "        \"assumed\": {\n" +
                        "            \"inflation\": 6,\n" +
                        "            \"returns\": 10\n" +
                        "        },\n" +
                        "        \"allocations\": {\n" +
                        "            \"equity\": 100,\n" +
                        "            \"debt\": 0,\n" +
                        "            \"others\": 0\n" +
                        "        }\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"requirementId\": \"5efd4598-6abf-4d63-99bf-8d2428813ee1\",\n" +
                        "        \"goalId\": \"1\",\n" +
                        "        \"goalName\": \"First goal\",\n" +
                        "        \"currentCost\": 1000000,\n" +
                        "        \"years\": 10,\n" +
                        "        \"yearlySipIncrease\": 10,\n" +
                        "        \"targetAmount\": 1791000,\n" +
                        "        \"existing\": 50000,\n" +
                        "        \"future\": 108000,\n" +
                        "        \"target\": 1683000,\n" +
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
              .queryParam("requirementId","5efd4598-6abf-4d63-99bf-8d2428813ee1");
        res.get("/tools/portfolios/builder/goal-planner")
                .then().log().all().spec(respec);
    }
}





