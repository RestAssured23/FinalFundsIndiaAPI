package core.advisor_Dashboard;
import core.advisor_Dashboard.model.clientSnapshot;
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
            .body(Adv_payload.level0());
        res.when().post("/tools/portfolio-exposure/l0")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level1() {
        RequestSpecification res = given().spec(req)
                .body(Adv_payload.level1());
        res.when().post("/tools/portfolio-exposure/l1")
                .then().log().all().spec(respec);
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
    public void MonthlyTrends_Snapshot() {
        SoftAssert softAssert = new SoftAssert();

        double totalNetflow = 0;
        double totalInflow = 0;
        double totalOutflow = 0;
        double totalBaseAum = 0;
        double totalMtm = 0;
        double totalCurrentAum = 0;
        double totalNetgrowthper=0;
        double totalAumgrowthper=0;
        double totalMtmper=0;

        RequestSpecification res = given().spec(req)
             .body(Adv_payload.SnapshotPayload());
        clientSnapshot.Root response =res
                .when()
                .post("/tools/advisory-dashboard/investors/snapshot")
                .then()
                .log()
                .all()
                .spec(respec)
                .extract()
                .response()
                .as(clientSnapshot.Root.class);
      int count=response.getData().getRows().size();
            for (clientSnapshot.Row rowData : response.getData().getRows()) {
                double netflow = parseDouble(rowData.getNetflow());
                double inflow = parseDouble(rowData.getInflow());
                double outflow = parseDouble(rowData.getOutflow());
                double baseAum = parseDouble(rowData.getBaseAum());
                double mtm = parseDouble(rowData.getMtm());
                double currentAum = parseDouble(rowData.getCurrentAum());
                double netgrowthPercentage = parseDouble(rowData.getNetflowGrowthPercentage());
                double aumgrowthPercentage = parseDouble(rowData.getAumGrowthPercentage());
                double mtmPercentage = parseDouble(rowData.getMtmPercentage());

            totalInflow += inflow;
            totalOutflow += outflow;
            totalBaseAum += baseAum;
            totalNetflow += netflow;
            totalMtm += mtm;
            totalCurrentAum += currentAum;
            totalNetgrowthper +=netgrowthPercentage/count;
            totalAumgrowthper +=aumgrowthPercentage/count;
            totalMtmper +=mtmPercentage/count;
        }
        System.out.println("OutFlow: " + totalOutflow);
        System.out.println("NETFLOW: " + totalNetflow);
        System.out.println("BASEAUM: " + totalBaseAum);
        System.out.println("netflowGrowthPercentage: " + totalNetgrowthper);
        System.out.println("MTM: " + totalMtm);
        System.out.println("TOTAL CUR_AUM: " + totalCurrentAum);
        System.out.println("mtmPercentage: " + totalMtmper);
        System.out.println("aumGrowthPercentage: " + totalAumgrowthper);
        System.out.println("INFLOW: " + totalInflow);

        for (clientSnapshot.Summary summaryData : response.getData().getSummary()) {
            DecimalFormat decimalFormat = new DecimalFormat("0");

            softAssert.assertEquals(decimalFormat.format(totalOutflow),summaryData.getOutflow(),"OutFlow : ");

                softAssert.assertEquals(totalNetflow,summaryData.getNetflow(),"TotalNetflow :");
                softAssert.assertEquals(totalBaseAum,summaryData.getBaseAum(), "TotalBaseAum :");

                softAssert.assertEquals(totalNetgrowthper,summaryData.getNetflowGrowthPercentage(),"NetflowGrowthPercentage :");
                softAssert.assertEquals(totalMtm,summaryData.getMtm(),"MTM :");
                softAssert.assertEquals(totalCurrentAum,summaryData.getCurrentAum(),"CurrentAum :");
                softAssert.assertEquals(totalMtmper,summaryData.getMtmPercentage(),"MtmPercentage : ");
                softAssert.assertEquals(totalAumgrowthper,summaryData.getAumGrowthPercentage(),"AumGrowthPercentage :");
                softAssert.assertEquals(totalInflow,summaryData.getInflow(),"Inflow :");
        }
        softAssert.assertAll();      // Perform all assertions and collect all failures
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
                data.put("event","Bounce");
                                    /*event possibility ==> Processed,Dropped,Deferred,Bounce,Blocked,Delivered,Open,Click,
                                                            Span report,Unsubscribe,Group unscubscibe,Group resubscribe*/
               data.put("category","facts");
              data.put("sg_event_id","rbtnWrG1DVDGGGFHFyun0A==");
             data.put("sg_message_id","14c5d75ce93.dfd.64b469.filter0001.16648.5515E0B88.000000000000000000000");
            mailPayload.add(data);

        RequestSpecification res = given().spec(req)
                        .body(mailPayload);
        res.when().post("/tools/advisory-dashboard/web-hook/mail")
                .then().log().all().spec(respec);
    }
}

