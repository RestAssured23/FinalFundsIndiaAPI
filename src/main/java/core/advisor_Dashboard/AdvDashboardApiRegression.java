package core.advisor_Dashboard;
import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.Double.parseDouble;

public class AdvDashboardApiRegression extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    public AdvDashboardApiRegression() {
        req = new RequestSpecBuilder()
                .setBaseUri(getADBasePath())
                .addHeader("x-api-version", "2.0")
                .addHeader("channel-id", "10")
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
    public void SearchInvestor() {
        RequestSpecification res = given().spec(req)
                .body(Regressionpayload.SearchInvestor());
      res.when().post("/core/portfolio-review/search-investor")
                .then().log().all().spec(respec);

    }
    @Test(priority = 1)
    public void PortfolioList() {
        Map<String,Object> payload=new HashMap<>();
        List<String> investorid = Arrays.asList("1560984");
        payload.put("investors",investorid);

        RequestSpecification res = given().spec(req)
                        .body(payload);
        PortfolioListResponse.Root response=res.when().post("/core/portfolio-review/portfolio-list")
                .then().log().all().spec(respec).extract().response().as(PortfolioListResponse.Root.class);
        for(PortfolioListResponse.Data data: response.getData())
             System.out.println(data.getId());
    }
    @Test(priority = 2)
    public void CurrentPortfolio() {

        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"reviewId\": 0,\n" +
                        "  \"fiInvestmentStyle\": false,\n" +
                        "  \"investors\": [\n" +
                        "    {\n" +
                        "      \"investorId\": \"63910\",\n" +
                        "      \"portfolios\": [\n" +
                        "        173895,\n" +
                        "        524029,\n" +
                        "        787214,\n" +
                        "        2012021,\n" +
                        "        2012022,\n" +
                        "        3272148,\n" +
                        "        3413971,\n" +
                        "        3438938,\n" +
                        "        3441237,\n" +
                        "        3441263\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"investorId\": \"1560984\",\n" +
                        "      \"portfolios\": [\n" +
                        "        173895,\n" +
                        "        3365521,\n" +
                        "        3365522,\n" +
                        "        3441257,\n" +
                        "        3457648,\n" +
                        "        3457650,\n" +
                        "        3473581\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        res.when().post("/core/portfolio-review/current-portfolio")
                .then().log().all().spec(respec);
    }
    @Test(priority = 3)
    public void Rebalance() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"reviewId\": 0,\n" +
                        "  \"fiInvestmentStyle\": false,\n" +
                        "  \"checkTax\": false,\n" +
                        "  \"investors\": [\n" +
                        "    {\n" +
                        "      \"investorId\": \"63910\",\n" +
                        "      \"portfolios\": [\n" +
                        "        173895,\n" +
                        "        524029,\n" +
                        "        787214,\n" +
                        "        2012021,\n" +
                        "        2012022,\n" +
                        "        3272148,\n" +
                        "        3413971,\n" +
                        "        3438938,\n" +
                        "        3441237,\n" +
                        "        3441263\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"investorId\": \"1560984\",\n" +
                        "      \"portfolios\": [\n" +
                        "        173895,\n" +
                        "        3365521,\n" +
                        "        3365522,\n" +
                        "        3441257,\n" +
                        "        3457648,\n" +
                        "        3457650,\n" +
                        "        3473581\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        res.when().post("/core/portfolio-review/rebalance")
                .then().log().all().spec(respec);
    }
    @Test(priority = 4)
    public void AggregatedSummary() {
        RequestSpecification res = given().spec(req)
                        .queryParam("reviewId",27876);
        res.when().get("/core/portfolio-review/aggregated-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 5)
    public void SystematicPlans() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27876);
        res.when().get("/core/portfolio-review/systematic-plans")
                .then().log().all().spec(respec);
    }
    @Test(priority = 6)
    public void FundCommentary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27876);
        res.when().get("/core/portfolio-review/fund-commentary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 7)
    public void Scheme_SearchForm() {
        RequestSpecification res = given().spec(req);
        res.when().get("/core/portfolio-review/scheme-search/form")
                .then().log().all().spec(respec);
    }
    @Test(priority = 8)
    public void SchemeSearch() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"amc\": \"\",\n" +
                        "  \"category\": \"\",\n" +
                        "  \"subCategory\": \"\",\n" +
                        "  \"query\": \"\",\n" +
                        "  \"filterBy\": \"0\",\n" +
                        "  \"sortBy\": \"0\",\n" +
                        "  \"investors\": [\n" +
                        "    \"63910\",\n" +
                        "    \"1560984\"\n" +
                        "  ],\n" +
                        "  \"fisCategory\": \"\",\n" +
                        "  \"fisSubCategory\": \"\"\n" +
                        "}");
        res.when().post("/core/portfolio-review/scheme-search")
                .then().log().all().spec(respec);
    }
    @Test(priority = 9)
    public void cash_Add() {
        RequestSpecification res = given().spec(req)
                .body("{\"reviewId\":27926,\"amount\":5000}");
        res.when().post("/core/portfolio-review/cash")
                .then().log().all().spec(respec);
    }
    @Test(priority = 10)
    public void cashWithdraw() {
        RequestSpecification res = given().spec(req)
                .body("{\"reviewId\":27926,\"amount\":-5000}");
        res.when().post("/core/portfolio-review/cash")
                .then().log().all().spec(respec);
    }
    @Test(priority = 11)
    public void AggregateSummary_AddComments() {
        RequestSpecification res = given().spec(req)
                .body("{\"comment\":\"API Testing\",\"reviewId\":\"27926\",\"type\":\"Asset Allocation\"}");
        res.when().post("/core/portfolio-review/add-comment")
                .then().log().all().spec(respec);
    }
    @Test(priority = 12)
    public void CategoryAllocation() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948);
        res.when().get("/core/portfolio-review/category-allocation-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 13)
    public void AllocationSummary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948)
                .queryParam("fiInvestmentStyle",false);
        res.when().get("/core/portfolio-review/allocation-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 14)
    public void ExecutionSummary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948);
        res.when().get("/core/portfolio-review/execution-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 15)
    public void Summary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948);
        res.when().get("/core/portfolio-review/summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 16)
    public void FiComments() {
        RequestSpecification res = given().spec(req)
                .queryParam("schemeCode",9767);
        res.when().get("/core/portfolio-review/fi-comments")
                .then().log().all().spec(respec);
    }
    @Test(priority = 17)
    public void Observation() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"reviewId\": \"8710\",\n" +
                        "  \"type\": \"observation\",\n" +
                        "  \"comment\": \"\"\n" +
                        "}");
        res.when().post("/core/portfolio-review/observations")
                .then().log().all().spec(respec);
    }
    @Test(priority = 18)
    public void OldPDF() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948)
                .queryParam("type","old");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().spec(respec);
    }
    @Test(priority = 18)
    public void NewPDF() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948)
                .queryParam("type","New");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().spec(respec);
    }
    @Test(priority = 19)
    public void content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",27948);
        res.when().get("/core/portfolio-review/communications/content")
                .then().log().all().spec(respec);
    }
    @Test(priority = 20)
    public void communications() {
        RequestSpecification res = given().spec(req)
                        .body("{\n" +
                                "  \"reviewId\": \"27948\",\n" +
                                "  \"type\": [\n" +
                                "    \"email\"\n" +
                                "  ],\n" +
                                "  \"from\": \"qateam@fundsindia.com\",\n" +
                                "  \"to\": [\n" +
                                "    \"tri.sharon01@gmail.com\",\n" +
                                "    \"stpflow6@gmail.com\"\n" +
                                "  ],\n" +
                                "  \"cc\": [],\n" +
                                "  \"attachmentName\": \"tri.sharon01@gmail.com-27948.pdf\"\n" +
                                "}");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }


    @Test
    public void Filter_Form() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/advisory-dashboard/filters/form")
                .then().log().all().spec(respec);
    }
    @Test
    public void Dues() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/portfolio-review/dues")
                .then().log().all().spec(respec);
    }
    @Test
    public void All_Clients() {
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.AllClients());
        res.when().post("/tools/portfolio-review/clients")
                .then().log().all().spec(respec);
    }
    @Test
    public void All_Reviews() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 20,\n" +
                        "  \"segments\": [\n" +
                        "    \"platinum\",\n" +
                        "    \"gold\",\n" +
                        "    \"silver\",\n" +
                        "    \"digital\"\n" +
                        "  ],\n" +
                        "  \"types\": \"all\",\n" +
                        "  \"status\": [\n" +
                        "    \"all\",\n" +
                        "    \"draft\",\n" +
                        "    \"generated\",\n" +
                        "    \"completed\"\n" +
                        "  ],\n" +
                        "  \"heads\": [\n" +
                        "    \"187458\",\n" +
                        "    \"2152531\"\n" +
                        "  ],\n" +
                        "  \"managers\": [],\n" +
                        "  \"advisors\": [],\n" +
                        "  \"sortBy\": \"advisor_name\",\n" +
                        "  \"sortType\": \"asc\",\n" +
                        "  \"search\": {\n" +
                        "    \"query\": \"tri.sharon01@gmail.com\",\n" +
                        "    \"type\": \"email\"\n" +
                        "  }\n" +
                        "}");
                //.body(Adv_payload.AllReviews());
       AllReviewResponse.Root response= res.when().post("/tools/portfolio-review/completed")
                .then().log().all().spec(respec).extract().response().as(AllReviewResponse.Root.class);
        System.out.println(response.getData().getReviews().get(0).getReviewId());
    }
    @Test(priority = 1)
    public void Communication_Content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",10414);
        res.when().get("/core/portfolio-review/communications/content")
                .then().log().all().spec(respec);
    }
    @Test
    public void Communication_mail() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                            "reviewId":"3040",
                            "from": "qateam@fundsindia.com",
                            "to": ["tri.sharon01@gmail.com"],                           
                            "type":["email"]
                        }""");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }
    @Test
    public void mail_content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId", "22928");
        res.when().get("/core/portfolio-review/mail-content")
                .then().log().all().spec(respec);
    }

//Quality
/*    @Test
    public void Quality_Review() {
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.quality_review());
        res.when().post("/tools/portfolio-review/quality")
                .then().log().all().spec(respec);
    }*/
    @Test
    public void Quality_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",22928);
        res.when().get("/tools/portfolio-review/quality/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History_Post() {
        LocalDateTime updatedDate = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedUpdatedDate = updatedDate.format(formatter);

        Map<String,Object> payload=new HashMap<>();
            payload.put("date",formattedUpdatedDate);
            payload.put("status","Automation Testing");
            payload.put("comments","Automation Comments Test");
            payload.put("reviewId","22928");

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/tools/portfolio-review/follow-up")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",22928);
        res.when().get("/tools/portfolio-review/follow-up/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",22928);
        res.when().get("/tools/portfolio-review/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level0() {
        RequestSpecification res = given().spec(req)
           //     .body("{\"userRole\":\"Manager\",\"advisors\":[],\"managers\":[\"1871006\"],\"heads\":[\"2152531\"],\"financialYear\":\"2023-2024\",\"type\":\"amc\",\"sortBy\":\"amc\",\"aggregateBy\":\"investment_amount\",\"order\":\"asc\",\"size\":500,\"page\":1}");
           .body(Adv_payload.level0());
        res.when().post("/tools/portfolio-exposure/l0")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level1() {
        double aum=0.0,cust = 0.0;
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.level1());
     PortfolioExposureResponseBo.Root response= res.when().post("/tools/portfolio-exposure/l1")
                .then().log().all().spec(respec).extract().response().as(PortfolioExposureResponseBo.Root.class);

        for (PortfolioExposureResponseBo.Row rowData : response.getData().getRows()) {
            /*System.out.println(rowData.getData().get(0).getValue());
            System.out.println(rowData.getData().get(1).getValue());
            System.out.println(rowData.getData().get(3).getValue());*/
            double CusPercentage = Double.parseDouble(String.valueOf(rowData.getData().get(1).getValue()));
            double AumPercentage = Double.parseDouble(String.valueOf(rowData.getData().get(3).getValue()));
            aum += AumPercentage;
            cust += CusPercentage;

        }
              System.out.println("Total Customer Percentage: " + cust);
              System.out.println("Total AUM Percentage: " + aum);
        softAssert.assertEquals(cust,100.0);
        softAssert.assertEquals(aum,100.0);
        softAssert.assertAll();
    }
    @Test
    public void Exposure_level2() {
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.level2());
        res.when().post("/tools/portfolio-exposure/l2")
                .then().log().all().spec(respec);
    }

 //Monthly Trends
 @Test
 public void MonthlyTrends_Investor() {
     RequestSpecification res = given().spec(req)
                     .queryParam("user_id","474062");
     res.when().get("/advisory-dashboard/monthly-trends/investor")
             .then().log().all().spec(respec);
 }
 /*   @Test
    public void MonthlyTrends_Transaction() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("userId","474062");
        payload.put("month","");
        List<String> type = Arrays.asList();
        payload.put("types",type);
    RequestSpecification res = given().spec(req)
               .body(payload);
    res.when().post("/tools/advisory-dashboard/monthly-trends/transactions")
              .then().log().all().spec(respec);
    }*/
    @Test
    public void testClientSnapshot() {
        RequestSpecification requestSpec = given().spec(req)
                .body(Adv_payload.SnapshotPayload());
        clientSnapshot.Root response = requestSpec
                .when()
                .post("/tools/advisory-dashboard/investors/snapshot")
                .then()
                .log()
                .all()
                .spec(respec)
                .extract()
                .response()
                .as(clientSnapshot.Root.class);

        List<clientSnapshot.Row> rows = response.getData().getRows();
        int rowCount = rows.size();

        DecimalFormat decimalFormat = new DecimalFormat("0");

        double totalNetflow = 0;
        double totalInflow = 0;
        double totalOutflow = 0;
        double totalBaseAum = 0;
        double totalMtm = 0;
        double totalCurrentAum = 0;
        double totalNetgrowthper = 0;
        double totalAumgrowthper = 0;
        double totalMtmper = 0;

        for (clientSnapshot.Row rowData : rows) {
            totalInflow += addToTotal(parseDouble(rowData.getInflow()));
            totalOutflow += addToTotal(parseDouble(rowData.getOutflow()));
            totalBaseAum += addToTotal(parseDouble(rowData.getBaseAum()));
            totalNetflow += addToTotal(parseDouble(rowData.getNetflow()));


            totalNetgrowthper += addToTotal(parseDouble(rowData.getNetflowGrowthPercentage())) / rowCount;
            totalAumgrowthper += addToTotal(parseDouble(rowData.getAumGrowthPercentage())) / rowCount;

            String mtmPercentageStr = rowData.getMtmPercentage();
            if (!mtmPercentageStr.isEmpty()) {
                totalMtmper += addToTotal(parseDouble(rowData.getMtmPercentage())) / rowCount;
            }

            String mtmStr = rowData.getMtm();
            if (!mtmStr.isEmpty()) {
                totalMtm += addToTotal(parseDouble(rowData.getMtm()));
            }

            String aumStr = rowData.getCurrentAum();
            if (!aumStr.isEmpty()) {
                totalCurrentAum += addToTotal(parseDouble(rowData.getCurrentAum()));
            }
        }

        printTotal("OutFlow", totalOutflow, decimalFormat);
        printTotal("NETFLOW", totalNetflow, decimalFormat);
        printTotal("BASEAUM", totalBaseAum, decimalFormat);
        printTotal("netflowGrowthPercentage", totalNetgrowthper);
        printTotal("MTM", totalMtm, decimalFormat);
        printTotal("TOTAL CUR_AUM", totalCurrentAum, decimalFormat);
        printTotal("mtmPercentage", totalMtmper);
        printTotal("aumGrowthPercentage", totalAumgrowthper);
        printTotal("INFLOW", totalInflow, decimalFormat);

        List<clientSnapshot.Summary> summaries = response.getData().getSummary();
        SoftAssert softAssert = new SoftAssert();

        for (clientSnapshot.Summary summaryData : summaries) {
            softAssert.assertEquals(decimalFormat.format(totalOutflow), summaryData.getOutflow(), "OutFlow : ");
            softAssert.assertEquals(decimalFormat.format(totalNetflow), summaryData.getNetflow(), "TotalNetflow :");
            softAssert.assertEquals(decimalFormat.format(totalBaseAum), summaryData.getBaseAum(), "TotalBaseAum :");
            softAssert.assertEquals(decimalFormat.format(totalMtm), summaryData.getMtm(), "MTM :");
            softAssert.assertEquals(decimalFormat.format(totalCurrentAum), summaryData.getCurrentAum(), "CurrentAum :");
            softAssert.assertEquals(decimalFormat.format(totalInflow), summaryData.getInflow(), "Inflow :");
            softAssert.assertEquals(totalNetgrowthper, summaryData.getNetflowGrowthPercentage(), "NetflowGrowthPercentage :");
            softAssert.assertEquals(totalMtmper, summaryData.getMtmPercentage(), "MtmPercentage : ");
            softAssert.assertEquals(totalAumgrowthper, summaryData.getAumGrowthPercentage(), "AumGrowthPercentage :");
        }
        softAssert.assertAll();
    }
    private double addToTotal(double value) {
        return value;
    }
    private void printTotal(String label, double value, DecimalFormat decimalFormat) {
        System.out.println(label + ": " + decimalFormat.format(value));
    }
    private void printTotal(String label, double value) {
        System.out.println(label + ": " + value);
    }

    @Test
    public void MonthlyTrends_Get() {
        RequestSpecification res = given().spec(req)
                .queryParam("user_id","114723");
        res.when().get("/tools/advisory-dashboard/monthly-trends/investor")
                .then().log().all().spec(respec);
    }
    @Test
    public void web_hook_mail() {
            List<Map<String, Object>> mailPayload = new ArrayList<>();      // Level 1
            Map<String, Object> data = new HashMap<>();
                data.put("email","tri.sharon01@gmail.com");
                data.put("timestamp","1513299569");
                data.put("smtp-id","<14c5d75ce93.dfd.64b469@ismtpd-555>");
                data.put("event","Processed");
                                    /*event possibility ==> Processed,Dropped,Deferred,Bounce,Blocked,Delivered,Open,Click,
                                                            Span report,Unsubscribe,Group unscubscibe,Group resubscribe*/
              //data.put("category","facts");
             data.put("sg_event_id","rbtnWrG1DVDGGGFHFyun0A==");
             data.put("sg_message_id","14c5d75ce93.dfd.64b469.filter0001.16648.5515E0B88.000000000000000000000");
            mailPayload.add(data);

        RequestSpecification res = given().spec(req)
                        .body(mailPayload);
        res.when().post("/tools/advisory-dashboard/web-hook/mail")
                .then().log().all().spec(respec);
    }
    @Test
    public void Advisory_MonthlyTrends() {
        RequestSpecification res = given().spec(req)
                        .body(Adv_payload.Monthly_Trends());
       MonthlyTrendsResponse.Root response= res.when().post("/tools/advisory-dashboard/monthly-trends")
                .then().log().all().spec(respec).extract().response().as(MonthlyTrendsResponse.Root.class);
    }

    @Test
    public void Advisory_Filters() {            //post
        RequestSpecification res = given().spec(req)
                        .body(Adv_payload.Filters());
        res.when().post("/tools/advisory-dashboard/filters")
                .then().log().all().spec(respec);
    }
    @Test
    public void Advisory_Filters_Get() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/advisory-dashboard/filters")
                .then().log().all().spec(respec);
    }
    @Test
    public void Advisory_Filters_Delete() {
        RequestSpecification res = given().spec(req)
                .queryParam("filterId","3178009d-86d9-4e19-b628-341f05695749");
        res.when().delete("/tools/advisory-dashboard/filters")
                .then().log().all().spec(respec);
    }
    @Test(priority = 16)
    public void TestComments() {
        RequestSpecification res = given().spec(req)
                .queryParam("schemeCode",116);
      FiCommentsResponse.Root response=  res.when().get("/core/portfolio-review/fi-comments")
                .then().log().all().spec(respec).extract().response().as(FiCommentsResponse.Root.class);
      //  System.out.println(response.getData().getComments());

        String comments="We do not prefer this fund due to low AUM and weak track record of the category in dynamically managing interest rate risk. We prefer short duration funds as they provide similar or better returns over 3-5 years compared to Dynamic Funds but with much lower volatility.";
        Assert.assertEquals(comments,response.getData().getComments());
    }

    @Test
    public void TestCommentsfile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Fi-User\\Desktop\\FICommentry.xlsx");
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet("Data"); // Replace with the actual sheet name

            Row row = sheet.getRow(9);
            Cell code=row.getCell(0);
              Cell cell = row.getCell(6);
              String expectedcomment= String.valueOf(cell);
            System.out.println(code);

        if (cell != null) {
                        RequestSpecification res = given().spec(req)
                        .queryParam("schemeCode",276);
                FiCommentsResponse.Root response=  res.when().get("/core/portfolio-review/fi-comments")
                        .then().log().all().spec(respec).extract().response().as(FiCommentsResponse.Root.class);

           /* String expectedNormalized = expectedcomment.replaceAll("\\s+", " ").trim();
            String actualNormalized = response.getData().getComments().replaceAll("\\s+", " ").trim();
             Assert.assertEquals(expectedNormalized,actualNormalized);*/

                Assert.assertEquals(expectedcomment,response.getData().getComments());

            } else {
                System.out.println("No comment found in the cell.");
            }

            workbook.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

