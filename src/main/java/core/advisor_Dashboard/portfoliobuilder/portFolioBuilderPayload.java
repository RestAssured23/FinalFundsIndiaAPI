package core.advisor_Dashboard.portfoliobuilder;

import java.util.*;

public class portFolioBuilderPayload {
    public static Map<String, Object> postReq() {
        Map<String,Object> reqPayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","Sathish D");
        basicData.put("age",30);
        basicData.put("email","sathish123@gmail.com");
        basicData.put("mobile","+918072007599");
        basicData.put("location","chennai");
        basicData.put("occupation","Testing");
        basicData.put("family","Married");              //(Married / Unmarried) Note: Family if married parents, spouse , No of kids Mandatory
        basicData.put("spouse","saran");
        basicData.put("noOfKids","2Kids");               // 1Kid,2Kids,3+Kids
        basicData.put("parents","Non Dependent Parents");


        Map<String,Object> reqData=new HashMap<>();
        reqData.put("description","Marriage");
        reqData.put("lumpsum",10000);
        reqData.put("sip",12000);
        reqData.put("timeframe","5Years");

        Map<String,Object> hisData=new HashMap<>();
        hisData.put("investmentExperience",2);
        hisData.put("experienceSoFar","String");
        List<String> list = Arrays.asList("String");
        hisData.put("assetClasses",list);

        Map<String,Object> portfolioData=new HashMap<>();
        portfolioData.put("amount",20000);
        portfolioData.put("equity",50);
        portfolioData.put("debt",50);
        portfolioData.put("emiPercentage",10);

        Map<String,Object> riskData=new HashMap<>();
        riskData.put("profile","High Risk");
        riskData.put("downTrendPortfolio","20");
        riskData.put("duringCovid","String");
        riskData.put("comments","String");

        reqPayload.put("basic",basicData);
        reqPayload.put("requirement",reqData);
        reqPayload.put("history",hisData);
        reqPayload.put("portfolio",portfolioData);
        reqPayload.put("risk",riskData);
        return reqPayload;
    }
    public static Map<String, Object> updateReq() {
        Map<String,Object> updatePayload=new HashMap<>();
        Map<String,Object> basicData=new HashMap<>();
        basicData.put("name","updated post investor");
        basicData.put("age",35);
        basicData.put("email","post123@gmail.com");
        basicData.put("mobile","7000000001");
        basicData.put("location","chennai");
        basicData.put("occupation","Manager");
        basicData.put("family","Married");
        basicData.put("spouse","spouse");
        basicData.put("noOfKids","2");               // 1Kid,2Kids,3+Kids
        basicData.put("parents","Father");

        Map<String,Object> reqData=new HashMap<>();
        reqData.put("description","Education");
        reqData.put("lumpsum",20000);
        reqData.put("sip",2000);
        reqData.put("timeframe","5");

        Map<String,Object> hisData=new HashMap<>();
        hisData.put("investmentExperience",0);
        hisData.put("experienceSoFar","");
        List<String> list = Arrays.asList("");
        hisData.put("assetClasses",list);

        Map<String,Object> portfolioData=new HashMap<>();
        portfolioData.put("amount",0);
        portfolioData.put("equity",0);
        portfolioData.put("debt",0);
        portfolioData.put("emiPercentage",0);

        Map<String,Object> riskData=new HashMap<>();
        riskData.put("profile","string");
        riskData.put("downTrendPortfolio","");
        riskData.put("duringCovid","");
        riskData.put("comments","As a financial advisor, I want to create a comprehensive PDF portfolio report for a first-time investor, So that I can present them with detailed information regarding their investment plan.");
        updatePayload.put("requirementId","0d8be727-2141-4767-8a7f-8c0db88f0008");
        updatePayload.put("basic",basicData);
        updatePayload.put("requirement",reqData);
        updatePayload.put("history",hisData);
        updatePayload.put("portfolio",portfolioData);
        updatePayload.put("risk",riskData);
        return updatePayload;
    }
    public static Map<String, Object> postMoneyBox() {
        Map<String,Object>moneyBoxPayload=new HashMap<>();
        moneyBoxPayload.put("requirementId","33ac5dba-ec51-4c66-ab3f-6ad58e2d7b59");
        moneyBoxPayload.put("type","less_than_30");           //[ less_than_30, greater_or_equal_to_30 ]
        moneyBoxPayload.put("option1",true);
        moneyBoxPayload.put("option2",true);
        moneyBoxPayload.put("option3",true);
        moneyBoxPayload.put("option4","Equity");
        moneyBoxPayload.put("option5","Debt");

        return moneyBoxPayload;
    }
    public static Map<String, Object> postWealthEquation() {
        Map<String,Object> wealthequ=new HashMap<>();
        wealthequ.put("requirementId","c038c573-fb6f-4b10-bed1-5149758d8e9c");
        wealthequ.put("current",1000000);
        wealthequ.put("sip",50000);
        wealthequ.put("yearlyLumpsum",100000);
        wealthequ.put("yearlyIncreaseLumpsum",10);
        wealthequ.put("yearlyIncreaseSip",10);
        List<Integer> data=new ArrayList<>();
        data.addAll(Arrays.asList(2,3,5,7,10,12,15,20,25,30));
        wealthequ.put("years",data);
        wealthequ.put("type","monthly_savings");     //[ total_investment, one_time_investment, monthly_savings, yearly_one_time_investment ]
        return wealthequ;
    }
    public static Map<String, Object> postSafetyBox() {
        Map<String, Object> safetyBox = new HashMap<>();
        safetyBox.put("requirementId", "2e33b880-3a60-4196-96ed-259e6442d2ab");
        safetyBox.put("comments", "Generating Portfolio Builder PDF for First-Time Investor");

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
        emergency.put("lumpsum", 1000);
        emergencyList.add(emergency);
        mf.put("emergency", emergencyList);
        safetyBox.put("mf", mf);

        // Insurance
        Map<String, Object> insurance = new HashMap<>();
    //Health
        List<Map<String, Object>> healthList = new ArrayList<>();
        Map<String, Object> health = new HashMap<>();
            health.put("name", "Reassure 2.0");
            health.put("companyName", "Niva Bupa");
            health.put("cover", 1);
            health.put("yearly", 1);
        healthList.add(health);

   //term
        List<Map<String, Object>> termList = new ArrayList<>();
        Map<String, Object> term = new HashMap<>();
            term.put("name", "I Protect Smart");
            term.put("companyName", "ICICI");
            term.put("cover", 1);
            term.put("yearly", 1);
        termList.add(term);

        insurance.put("health", healthList);
        insurance.put("term", termList);
        safetyBox.put("insurance", insurance);

        // Rationals
        Map<String, Object> rationals = new HashMap<>();
        List<Map<String, Object>> emergencyRationalsList = new ArrayList<>();
        Map<String, Object> emergencyRational = new HashMap<>();
        emergencyRational.put("schemeCode", "8029");
        emergencyRational.put("schemeName", "Axis Liquid Fund-Reg(G)");
        emergencyRational.put("category", "Liquid");
        emergencyRational.put("subCategory", "Debt - Liquid Fund");
        emergencyRational.put("rationale", "string");
        emergencyRational.put("ratings", 5);
        emergencyRationalsList.add(emergencyRational);

        List<Map<String, Object>> insuranceRationalsList = new ArrayList<>();
        Map<String, Object> insuranceRational = new HashMap<>();
        insuranceRational.put("schemeCode", "8029");
        insuranceRational.put("schemeName", "Niva Bupa");
        insuranceRational.put("category", "Equity");
        insuranceRational.put("subCategory", "test");
        insuranceRational.put("rationale", "rationale");
        insuranceRational.put("ratings", 5);
        insuranceRationalsList.add(insuranceRational);

        rationals.put("emergency", emergencyRationalsList);
        rationals.put("insurance", insuranceRationalsList);
          safetyBox.put("rationals", rationals);
       return safetyBox;
    }

    public static Map<String, Object> postShortterm() {
        Map<String, Object> shortTerm = new HashMap<>();
        shortTerm.put("requirementId", "2e33b880-3a60-4196-96ed-259e6442d2ab");
        shortTerm.put("comments", "As a financial advisor, I want to create a comprehensive PDF portfolio report for a first-time investor, So that I can present them with detailed information regarding their investment plan.");
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
        OnerationalData.put("schemeCode", "468");
        OnerationalData.put("schemeName", "Aditya Birla SL Savings Fund-Reg(G)");
        OnerationalData.put("ratings", 5);
        OnerationalData.put("category", "Debt");
        OnerationalData.put("subCategory", "Debt - Ultra Short Duration Fund");
        OnerationalData.put("sip", 5000);
        OnerationalData.put("lumpsum", 10000);
        OnerationalData.put("rationale","rationale");
        Rationla_OneThreeYears.add(OnerationalData);

        List<Map<String, Object>> Rational_ThreeFiveYears = new ArrayList<>();
        Map<String, Object> fiverationalData = new HashMap<>();
        fiverationalData.put("schemeCode", "1470");
        fiverationalData.put("schemeName", "ICICI Pru Balanced Advantage Fund(G)");
        fiverationalData.put("ratings",5);
        fiverationalData.put("category", "Hybrid Others");
        fiverationalData.put("subCategory", "Hybrid - Equity Savings");
        fiverationalData.put("sip", 1000);
        fiverationalData.put("lumpsum", 1000);
        fiverationalData.put("rationale","rationale");
        Rational_ThreeFiveYears.add(fiverationalData);

        shortTerm.put("oneToThreeYears", One_ThreeYears);
        shortTerm.put("threeToFiveYears", Three_FiveYears);

        Map<String, Object> rationalPayload = new HashMap<>();
        rationalPayload.put("oneToThreeYears",Rationla_OneThreeYears);
        rationalPayload.put("threeToFiveYears",Rational_ThreeFiveYears);
        shortTerm.put("rationals",rationalPayload);

        return shortTerm;
    }
    public static Map<String, Object> postLongterm() {
        Map<String, Object> longTermPayload = new HashMap<>();
        longTermPayload.put("presetId", "string");
        longTermPayload.put("presetName", "Testing");
        longTermPayload.put("lumpsum", 1000);
        longTermPayload.put("sip", 1000);
        longTermPayload.put("requirementId", "2e33b880-3a60-4196-96ed-259e6442d2ab");
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
        sipMap.put("execution", "");
        //"lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
        lumpsumMap.put("amount", 20000);
        lumpsumMap.put("percentage",10 );
        lumpsumMap.put("execution", "");

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
        sipSchemeMap.put("execution", "");
        //"lumpsum" inside "schemes"
        Map<String, Object> lumpsumSchemeMap = new HashMap<>();
        lumpsumSchemeMap.put("amount", 10000);
        lumpsumSchemeMap.put("percentage", 20);
        lumpsumSchemeMap.put("execution", "");

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
        debtsipMap.put("execution", "");
        //"lumpsum" inside "debt"
        Map<String, Object> otilumpsumMap = new HashMap<>();
        otilumpsumMap.put("amount", 20000);
        otilumpsumMap.put("percentage",10 );
        otilumpsumMap.put("execution", "");

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
        debtsipSchemeMap.put("execution", "");
        //"lumpsum" inside "schemes"
        Map<String, Object> debtlumpsumSchemeMap = new HashMap<>();
        debtlumpsumSchemeMap.put("amount", 10000);
        debtlumpsumSchemeMap.put("percentage", 20);
        debtlumpsumSchemeMap.put("execution", "");

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
        equityRationalsMap.put("schemeCode", "938");
        equityRationalsMap.put("schemeName", "Franklin India Focused Equity Fund(G)");
        equityRationalsMap.put("category", "equity");
        equityRationalsMap.put("subCategory", "Equity - Focused Fund");
        equityRationalsMap.put("rationale", "string");
        equityRationalsMap.put("ratings",5);
        equityRationalsList.add(equityRationalsMap);

        //"debt" inside "rationals"
        List<Map<String, Object>> debtRationalsList = new ArrayList<>();
        Map<String, Object> debtRationalsMap = new HashMap<>();
        debtRationalsMap.put("schemeCode", "453");
        debtRationalsMap.put("schemeName", "Aditya Birla SL Corp Bond Fund(G)");
        debtRationalsMap.put("category", "debt");
        debtRationalsMap.put("subCategory", "Debt - Corporate Bond Fund");
        debtRationalsMap.put("rationale", "string");
        debtRationalsMap.put("ratings",5);
        debtRationalsList.add(debtRationalsMap);

        rationalsMap.put("equity", equityRationalsList);
        rationalsMap.put("debt", debtRationalsList);

         longTermPayload.put("rationals", rationalsMap);
        return longTermPayload;
    }

    public static Map<String, Object> postHighRisk() {
        Map<String, Object> highRiskpayload = new HashMap<>();

        // Set values for the outermost fields
        highRiskpayload.put("presetId", "string");
        highRiskpayload.put("presetName", "Testing");
        highRiskpayload.put("lumpsum", 10000);
        highRiskpayload.put("sip", 2000);
        highRiskpayload.put("rebalance", "string");
        highRiskpayload.put("comments", "Suggested Portfolio: An outlined investment portfolio tailored to the investor's preferences and goals.");
        highRiskpayload.put("requirementId", "2e33b880-3a60-4196-96ed-259e6442d2ab");
        highRiskpayload.put("comment", "Suggested Portfolio: An outlined investment portfolio tailored to the investor's preferences and goals.");

        // Create a nested map for "allocations"
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create a nested map for "equity" inside "allocations"
        Map<String, Object> equityMap = new HashMap<>();

        //"sip" inside "equity"
        Map<String, Object> sipMap = new HashMap<>();
        sipMap.put("amount", 1000);
        sipMap.put("percentage", 50);
        sipMap.put("execution", "equitySIP");

        //"lumpsum" inside "equity"
        Map<String, Object> lumpsumMap = new HashMap<>();
        lumpsumMap.put("amount", 1000);
        lumpsumMap.put("percentage", 50);
        lumpsumMap.put("execution", "equityOTI");

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

     //   debtMap.put("sip", debtsipMap);
     //   debtMap.put("lumpsum", debtlumpsumMap);

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

   //     debtschemesMap.put("sip", debtsipSchemeMap);
   //     debtschemesMap.put("lumpsum", debtlumpsumSchemeMap);

    //    debtschemesList.add(debtschemesMap);
   //     debtMap.put("schemes", debtschemesList);

        allocationsMap.put("equity", equityMap);
        allocationsMap.put("debt", debtMap);

        highRiskpayload.put("allocations", allocationsMap);

        // Create a nested map for "rationals"
        Map<String, Object> rationalsMap = new HashMap<>();

        // Create a list for "equity" inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
        Map<String, Object> equityRationalsMap = new HashMap<>();
        equityRationalsMap.put("schemeCode", "948");
        equityRationalsMap.put("schemeName", "Franklin India Prima Fund(G)");
        equityRationalsMap.put("category", "Equity");
        equityRationalsMap.put("subCategory", "Equity - Mid Cap Fund");
        equityRationalsMap.put("rationale", "string");
        equityRationalsMap.put("ratings", 5);

        List<Map<String, Object>> debtRationalsList = new ArrayList<>();
        Map<String, Object> debtRationalsMap = new HashMap<>();
        debtRationalsMap.put("schemeCode", "string");
        debtRationalsMap.put("schemeName", "string");
        debtRationalsMap.put("category", "string");
        debtRationalsMap.put("subCategory", "string");
        debtRationalsMap.put("rationale", "string");
        debtRationalsMap.put("ratings", 0);

    //    debtRationalsList.add(debtRationalsMap);
        equityRationalsList.add(equityRationalsMap);
     //   rationalsMap.put("debt", debtRationalsList);
        rationalsMap.put("equity", equityRationalsList);

        highRiskpayload.put("rationals", rationalsMap);

        // Create a nested map for "invest"
        Map<String, Object> investMap = new HashMap<>();
        investMap.put("debt", "50");
        investMap.put("equity", "50");
        highRiskpayload.put("invest", investMap);
        return highRiskpayload;
    }

    public static Map<String, Object> postLongTermPreset() {
        Map<String, Object> longtermPreset = new HashMap<>();
        //     jsonPayload.put("presetId", "");
        longtermPreset.put("presetName", "Sathish DS");
        longtermPreset.put("lumpsum", 10000);
        longtermPreset.put("sip", 1000);

        // Create "assetAllocation" map
        Map<String, Object> assetAllocationMap = new HashMap<>();
        assetAllocationMap.put("equity", 80);
        assetAllocationMap.put("debt", 20);

        // Create "portfolioConstruction" map
        Map<String, Object> portfolioConstructionMap = new HashMap<>();
        portfolioConstructionMap.put("equity", "Five finger");
        portfolioConstructionMap.put("debt", "Saving Fund");

        assetAllocationMap.put("portfolioConstruction", portfolioConstructionMap);
        longtermPreset.put("assetAllocation", assetAllocationMap);

        // Create "allocations" map
        Map<String, Object> allocationsMap = new HashMap<>();

        // Create "equity" map
        Map<String, Object> equityMap = new HashMap<>();

        // Create "sip" map inside "equity"
        Map<String, Object> sipMapEquity = new HashMap<>();
        sipMapEquity.put("amount", 1000);
        sipMapEquity.put("percentage", 50);
        sipMapEquity.put("execution", "string");

        // Create "lumpsum" map inside "equity"
        Map<String, Object> lumpsumMapEquity = new HashMap<>();
        lumpsumMapEquity.put("amount", 10000);
        lumpsumMapEquity.put("percentage", 10);
        lumpsumMapEquity.put("execution", "string");

        // Create "schemes" list inside "equity"
        List<Map<String, Object>> schemesListEquity = new ArrayList<>();
        Map<String, Object> schemeMapEquity = new HashMap<>();
        schemeMapEquity.put("schemeCode", "15557");
        schemeMapEquity.put("schemeName", "Axis Focused 25 Fund-Reg(G)");
        schemeMapEquity.put("ratings", 5);
        schemeMapEquity.put("category", "Equity");
        schemeMapEquity.put("subCategory", "string");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapScheme = new HashMap<>();
        sipMapScheme.put("amount", 1000);
        sipMapScheme.put("percentage", 10);
        sipMapScheme.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapScheme = new HashMap<>();
        lumpsumMapScheme.put("amount", 1000);
        lumpsumMapScheme.put("percentage", 10);
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
        schemeMapDebt.put("schemeCode", "453");
        schemeMapDebt.put("schemeName", "Aditya Birla SL Corp Bond Fund(G)");
        schemeMapDebt.put("ratings", 5);
        schemeMapDebt.put("category", "Debt");
        schemeMapDebt.put("subCategory", "Debt - Corporate Bond Fund");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapSchemeDebt = new HashMap<>();
        sipMapSchemeDebt.put("amount", 1000);
        sipMapSchemeDebt.put("percentage", 10);
        sipMapSchemeDebt.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapSchemeDebt = new HashMap<>();
        lumpsumMapSchemeDebt.put("amount", 1000);
        lumpsumMapSchemeDebt.put("percentage", 10);
        lumpsumMapSchemeDebt.put("execution", "string");

        schemeMapDebt.put("sip", sipMapSchemeDebt);
        schemeMapDebt.put("lumpsum", lumpsumMapSchemeDebt);

        schemesListDebt.add(schemeMapDebt);

        debtMap.put("sip", sipMapDebt);
        debtMap.put("lumpsum", lumpsumMapDebt);
        debtMap.put("schemes", schemesListDebt);

        allocationsMap.put("debt", debtMap);
        longtermPreset.put("allocations", allocationsMap);
return longtermPreset;
    }
    public static Map<String, Object> postHighRiskPreset() {
        Map<String, Object> highriskPayload = new HashMap<>();
        highriskPayload.put("presetId", "");
        highriskPayload.put("presetName", "High Risk Preset Testing");
        highriskPayload.put("lumpsum", 10000);
        highriskPayload.put("sip", 1000);

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
        schemeMapEquity.put("schemeCode", "5736");
        schemeMapEquity.put("schemeName", "ICICI Pru Banking & Fin Serv Fund(G)");
        schemeMapEquity.put("ratings", 5);
        schemeMapEquity.put("category", "Equity");
        schemeMapEquity.put("subCategory", "Equity - Thematic Fund - Other");

        // Create "sip" map inside "schemes"
        Map<String, Object> sipMapScheme = new HashMap<>();
        sipMapScheme.put("amount", 1000);
        sipMapScheme.put("percentage", 50);
        sipMapScheme.put("execution", "string");

        // Create "lumpsum" map inside "schemes"
        Map<String, Object> lumpsumMapScheme = new HashMap<>();
        lumpsumMapScheme.put("amount", 1000);
        lumpsumMapScheme.put("percentage", 50);
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

        highriskPayload.put("allocations", allocationsMap);

        // Create "rationals" map
        Map<String, Object> rationalsMap = new HashMap<>();

        // Create "equity" list inside "rationals"
        List<Map<String, Object>> equityRationalsList = new ArrayList<>();
        Map<String, Object> equityRationalMap = new HashMap<>();
        equityRationalMap.put("schemeCode", "5736");
        equityRationalMap.put("schemeName", "ICICI Pru Banking & Fin Serv Fund(G)");
        equityRationalMap.put("category", "Equity");
        equityRationalMap.put("subCategory", "Equity - Thematic Fund - Other");
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
     //   rationalsMap.put("debt", debtRationalsList);

        highriskPayload.put("rationals", rationalsMap);

        // Add remaining fields like "rebalance", "comments", etc.
        highriskPayload.put("rebalance", "string");
        highriskPayload.put("comments", "string");
        return highriskPayload;
    }

    }
