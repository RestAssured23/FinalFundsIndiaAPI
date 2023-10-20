package core.advisor_Dashboard;
import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
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
                .body("{\n" +
                        "                            \"reviewId\":\"3040\",\n" +
                        "                            \"from\": \"megha.n@fundsindia.com\",\n" +
                        "                            \"to\": [\"shamelikumarcr7@gmail.com\"],\n" +
                        "                            \"mobiles\": [\"09790790876\"],\n" +
                        "                            \"type\":[\"whatsapp\",\"email\"]\n" +
                        "                        }");
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
                .body("{\n" +
                        "                          \"page\": 1,\n" +
                        "                          \"size\": 10,\n" +
                        "                          \"due\": \"month\",\n" +
                        "                          \"status\": [\n" +
                        "                            \"in_progress\"\n" +
                        "                          ],\n" +
                        "                          \"fromDate\": \"string\",\n" +
                        "                          \"toDate\": \"string\",\n" +
                        "                          \"heads\": [\n" +
                        "                            \"string\"\n" +
                        "                          ],\n" +
                        "                          \"managers\": [\n" +
                        "                            \"string\"\n" +
                        "                          ],\n" +
                        "                          \"advisors\": [\n" +
                        "                            \"string\"\n" +
                        "                          ]\n" +
                        "                        }");
        res.when().post("/core/portfolio-review/clients")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_communications() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "                          \"reviewId\": \"string\",\n" +
                        "                          \"from\": \"string\",\n" +
                        "                          \"to\": [\n" +
                        "                            \"string\"\n" +
                        "                          ],\n" +
                        "                          \"mobiles\": [\n" +
                        "                            \"string\"\n" +
                        "                          ],\n" +
                        "                          \"type\": [\n" +
                        "                            \"whatsapp\"\n" +
                        "                          ]\n" +
                        "                        }");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_callback() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "                          \"data\": [\n" +
                        "                            {\n" +
                        "                              \"tableName\": \"string\",\n" +
                        "                              \"condition\": \"string\",\n" +
                        "                              \"set\": \"string\",\n" +
                        "                              \"upsert\": true\n" +
                        "                            }\n" +
                        "                          ]\n" +
                        "                        }");
        res.when().post("/core/portfolio-review/callback")
                .then().log().all().spec(respec);
    }
  /*  @Test
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
    }*/

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
                .body("{\n" +
                        "                          \"date\": \"2023-06-29T23:59:59.000Z\",\n" +
                        "                          \"status\": \"Status Test\",\n" +
                        "                          \"comments\": \"Comments Test\",\n" +
                        "                          \"reviewId\": \"24699\"\n" +
                        "                        }");
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
     ExposureLevel1.Root response= res.when().post("/tools/portfolio-exposure/l1")
                .then().log().all().spec(respec).extract().response().as(ExposureLevel1.Root.class);

        for (ExposureLevel1.Row rowData : response.getData().getRows()) {
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
                     .queryParam("user_id","431340");
     ClientDetailsResponse.Root response=res.when().get("/tools/advisory-dashboard/monthly-trends/investor")
             .then().spec(respec).extract().response().as(ClientDetailsResponse.Root.class);
    System.out.println(response.getData().getSummary().get(0).getYtd());
 }
    /*    @Test
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
    public void ClientSnapshot() {
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
    }
    @Test
    public void MonthlyTrends() {
        RequestSpecification res = given().spec(req)
                 .body(Adv_payload.Monthly_Trends());
        MonthlyTrendsResponse.Root response= res.when().post("/tools/advisory-dashboard/monthly-trends")
                .then().log().all().spec(respec).extract().response().as(MonthlyTrendsResponse.Root.class);
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

     // Initialize variables with descriptive names
     double totalInflow = 0;
     double totalOutflow = 0;
     double totalBaseAum = 0;
     double totalNetflow = 0;
     double totalNetgrowthper = 0;
     double totalAumgrowthper = 0;
     double totalMtm = 0;
     double totalCurrentAum = 0;
     double totalMtmper = 0;

     // Iterate through rows and accumulate totals
     for (clientSnapshot.Row rowData : rows) {
         totalInflow += parseDouble(rowData.getInflow());
         totalOutflow += parseDouble(rowData.getOutflow());
         totalBaseAum += parseDouble(rowData.getBaseAum());
       //  totalNetflow += parseDouble(rowData.getNetflow());

         String netflowStr = rowData.getNetflow();
         if (netflowStr != null) {
             totalNetflow += parseDouble(netflowStr);
         }

         totalNetgrowthper += parseDouble(rowData.getNetflowGrowthPercentage()) / rowCount;
         totalAumgrowthper += parseDouble(rowData.getAumGrowthPercentage()) / rowCount;

         String mtmPercentageStr = rowData.getMtmPercentage();
         if (!mtmPercentageStr.isEmpty()) {
             totalMtmper += parseDouble(mtmPercentageStr) / rowCount;
         }

         String mtmStr = rowData.getMtm();
         if (!mtmStr.isEmpty()) {
             totalMtm += parseDouble(mtmStr);
         }

         String aumStr = rowData.getCurrentAum();
         if (!aumStr.isEmpty()) {
             totalCurrentAum += parseDouble(aumStr);
         }

     }

   /*  // Print totals with labels
     printTotal("OutFlow", totalOutflow, decimalFormat);
     printTotal("NETFLOW", totalNetflow, decimalFormat);
     printTotal("BASEAUM", totalBaseAum, decimalFormat);
     printTotal("netflowGrowthPercentage", totalNetgrowthper);
     printTotal("MTM", totalMtm, decimalFormat);
     printTotal("TOTAL CUR_AUM", totalCurrentAum, decimalFormat);
     printTotal("mtmPercentage", totalMtmper);
     printTotal("aumGrowthPercentage", totalAumgrowthper);
     printTotal("INFLOW", totalInflow, decimalFormat);*/

     List<clientSnapshot.Summary> summaries = response.getData().getSummary();
     SoftAssert softAssert = new SoftAssert();

     // Assertions for summaries
     for (clientSnapshot.Summary summaryData : summaries) {
         softAssert.assertEquals(decimalFormat.format(totalOutflow), Double.parseDouble(summaryData.getOutflow()), "OutFlow : ");
         softAssert.assertEquals(decimalFormat.format(totalNetflow), summaryData.getNetflow(), "TotalNetflow :");
         softAssert.assertEquals(decimalFormat.format(totalBaseAum), summaryData.getBaseAum(), "TotalBaseAum :");
         softAssert.assertEquals(decimalFormat.format(totalMtm), summaryData.getMtm(), "MTM :");
         softAssert.assertEquals(decimalFormat.format(totalCurrentAum), summaryData.getCurrentAum(), "CurrentAum :");
         softAssert.assertEquals(decimalFormat.format(totalInflow), summaryData.getInflow(), "Inflow :");
         softAssert.assertEquals(totalNetgrowthper, Double.parseDouble(summaryData.getNetflowGrowthPercentage()), 0.000001, "NetflowGrowthPercentage :");
         softAssert.assertEquals(totalMtmper, Double.parseDouble(summaryData.getMtmPercentage()), 0.000001, "MtmPercentage : ");
         softAssert.assertEquals(totalAumgrowthper, Double.parseDouble(summaryData.getAumGrowthPercentage()), 0.000001, "AumGrowthPercentage :");
     }
     softAssert.assertAll();
 }
    @Test
    public void MonthlyTrends_Get() {
        RequestSpecification res = given().spec(req)
                .queryParam("user_id","1020690")
                .queryParam("financialYear","2023-2024");
        res.when().get("/tools/advisory-dashboard/monthly-trends/investor")
                .then().log().all().spec(respec);
    }
    @Test
    public void web_hook_mail() {
            List<Map<String, Object>> mailPayload = new ArrayList<>();
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
    @Test
    public void AUM_Overview() {
        RequestSpecification res = given().spec(req)
                        .body(Adv_payload.OverviewPayload());
        res.when().post("/tools/advisory-dashboard/growth/aum/overview")
                .then().log().all().spec(respec);
    }
    @Test
    public void Growth_Overview() {
        RequestSpecification res = given().spec(req)
                        .queryParam("includeTeam",false);
        res.when().get("/tools/advisory-dashboard/growth/overview")
                .then().log().all().spec(respec);
    }
    @Test
    public void Growth_AUM() {
        double aumGrowthPercent,sipGrowthPercent;
        long baseAum,currentAum;
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.AUM());
        AUMSummaryResponse.Root resposne=res.when().post("/tools/advisory-dashboard/growth/aum")
                .then().log().all().spec(respec).extract().response().as(AUMSummaryResponse.Root.class);

        baseAum=resposne.getData().getCurrent().get(0).getBaseAum();
        currentAum=resposne.getData().getCurrent().get(0).getCurrentAum();
        aumGrowthPercent = ((double)(currentAum - baseAum) / baseAum) * 100;
        sipGrowthPercent = ((double)(currentAum - baseAum) / baseAum) * 100;

        System.out.println("BASEAUM :"+baseAum);
        System.out.println("Current AUM :"+currentAum);
        System.out.println(aumGrowthPercent);
        System.out.println(sipGrowthPercent);

        softAssert.assertEquals(resposne.getData().getAumGrowth(),aumGrowthPercent);
        softAssert.assertEquals(resposne.getData().getSipGrowth(),sipGrowthPercent);
        softAssert.assertAll();
    }


}

