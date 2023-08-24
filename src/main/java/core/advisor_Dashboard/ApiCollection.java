package core.advisor_Dashboard;
import core.advisor_Dashboard.model.PortfolioExposureResponseBo;
import core.advisor_Dashboard.model.clientSnapshot;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.text.DecimalFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.Double.parseDouble;

public class ApiCollection extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    public ApiCollection() {
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
                .body(Adv_payload.AllReviews());
        res.when().post("/tools/portfolio-review/completed")
                .then().log().all().spec(respec);
    }
    @Test
    public void Communication_Content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",26859);
        res.when().get("/core/portfolio-review/communications/content")
                .then().log().all().spec(respec);
    }
    @Test
    public void Communication_mail() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                            "reviewId":"3040",
                            "from": "megha.n@fundsindia.com",
                            "to": ["shamelikumarcr7@gmail.com"],
                            "mobiles": ["09790790876"],
                            "type":["whatsapp","email"]
                        }""");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }
    @Test
    public void mail_content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId", "3002");
        res.when().get("/core/portfolio-review/mail-content")
                .then().log().all().spec(respec);
    }

    @Test
    public void Review_Clients() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "page": 1,
                          "size": 10,
                          "due": "month",
                          "status": [
                            "in_progress"
                          ],
                          "fromDate": "string",
                          "toDate": "string",
                          "heads": [
                            "string"
                          ],
                          "managers": [
                            "string"
                          ],
                          "advisors": [
                            "string"
                          ]
                        }""");
        res.when().post("/core/portfolio-review/clients")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_communications() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "reviewId": "string",
                          "from": "string",
                          "to": [
                            "string"
                          ],
                          "mobiles": [
                            "string"
                          ],
                          "type": [
                            "whatsapp"
                          ]
                        }""");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_callback() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "data": [
                            {
                              "tableName": "string",
                              "condition": "string",
                              "set": "string",
                              "upsert": true
                            }
                          ]
                        }""");
        res.when().post("/core/portfolio-review/callback")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_completed() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "page": 0,
                          "size": 0,
                          "types": [
                            "all"
                          ],
                          "status": [
                            "all"
                          ],
                          "fromDate": "string",
                          "toDate": "string",
                          "heads": [
                            "string"
                          ],
                          "managers": [
                            "string"
                          ],
                          "advisors": [
                            "string"
                          ]
                        }""");
        res.when().post("/core/portfolio-review/completed")
                .then().log().all().spec(respec);
    }

//Quality
    @Test
    public void Quality_Review() {
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.quality_review());
        res.when().post("/tools/portfolio-review/quality")
                .then().log().all().spec(respec);
    }
    @Test
    public void Quality_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",24701);
        res.when().get("/tools/portfolio-review/quality/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History_Post() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "date": "2023-06-29T23:59:59.000Z",
                          "status": "Status Test",
                          "comments": "Comments Test",
                          "reviewId": "24699"
                        }""");
        res.when().post("/tools/portfolio-review/follow-up")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",25244);
        res.when().get("/tools/portfolio-review/follow-up/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",24729);
        res.when().get("/tools/portfolio-review/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level0() {
        RequestSpecification res = given().spec(req)
                    //   .body("{\"userRole\":\"Manager\",\"advisors\":[\"131919\"],\"managers\":[\"1871006\"],\"heads\":[\"187458\"],\"financialYear\":\"2023-2024\",\"type\":\"scheme_name\",\"sortBy\":\"scheme_name\",\"aggregateBy\":\"investment_amount\",\"order\":\"asc\",\"size\":500,\"page\":2}");
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
    @Test
    public void MonthlyTrends_Transaction() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("userId","474062");
        payload.put("month","");
        List<String> type = Arrays.asList("");
        payload.put("types",type);
    RequestSpecification res = given().spec(req)
               .body(payload);
    res.when().post("/tools/advisory-dashboard/monthly-trends/transactions")
              .then().log().all().spec(respec);
    }
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
                .queryParam("user_id","474062");
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
        res.when().post("/tools/advisory-dashboard/monthly-trends")
                .then().log().all().spec(respec);
    }
}

