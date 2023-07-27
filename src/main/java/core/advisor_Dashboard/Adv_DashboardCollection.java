package core.advisor_Dashboard;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class Adv_DashboardCollection extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private String Holdingid,InvestorId,response;
    public Adv_DashboardCollection() {
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
    public void Form() {
        RequestSpecification res = given().spec(req).log().all();
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
                .body("""
                        {
                          "page": 1,
                          "size": 20,
                          "segments": [
                            "platinum",
                            "gold",
                            "silver",
                            "digital"
                          ],
                          "status": [
                            "not_started",
                            "in_progress",
                            "completed",
                            "overdue",
                            "not_reviewed"
                          ],
                          "heads": [
                            "2152531"
                          ],
                          "managers": [],
                          "advisors": [],
                          "sortBy": "user_name",
                          "sortType": "asc"
                        }""");
        res.when().post("/tools/portfolio-review/clients")
                .then().log().all().spec(respec);
    }
    @Test
    public void Communication() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",20879);
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
    public void Advisory_dashboard() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/advisory-dashboard/filters/form")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_Clients() {
        RequestSpecification res = given().spec(req)
                .body("""
                        {
                          "page": 0,
                          "size": 0,
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
        RequestSpecification res = given().spec(req).log().all()
                .body("{\n" +
                        "  \"reviewId\": \"23015\",\n" +
                        "  \"critical\": \"yes\",\n" +
                        "  \"date\": \"2023-06-26T12:18:34.268Z\",\n" +
                        "  \"parameters\": [\n" +
                        "    {\n" +
                        "      \"id\": \"1\",\n" +
                        "      \"description\": \"Testing\",\n" +
                        //      "      \"comment\": \"string\",\n" +
                        "      \"value\": \"yes\",\n" +
                        "      \"tags\": [\n" +
                        "        \"Tags testing \"\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"comments\": \"abcd\"\n" +
                        "}");
        res.when().post("/tools/portfolio-review/quality")
                .then().log().all().spec(respec);
    }
    @Test
    public void Quality_History() {
        RequestSpecification res = given().spec(req).log().all()
                .queryParam("reviewId",24701);
        res.when().get("/tools/portfolio-review/quality/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History_Post() {
        RequestSpecification res = given().spec(req).log().all()
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
        RequestSpecification res = given().spec(req).log().all()
                .queryParam("reviewId",25244);
        res.when().get("/tools/portfolio-review/follow-up/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_History() {
        RequestSpecification res = given().spec(req).log().all()
                .queryParam("reviewId",24729);
        res.when().get("/tools/portfolio-review/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level0() {
        RequestSpecification res = given().spec(req).log().all()
                .body(Adv_payload.level0());
        res.when().post("/tools/portfolio-exposure/l0")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level1() {
        RequestSpecification res = given().spec(req).log().all()
                .body(Adv_payload.level1());
        res.when().post("/tools/portfolio-exposure/l1")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level2() {
        RequestSpecification res = given().spec(req).log().all()
                .body(Adv_payload.level2());
        res.when().post("/tools/portfolio-exposure/l2")
                .then().log().all().spec(respec);
    }

 //Monthly Trends
 @Test
 public void MonthlyTrends_Investor() {
     RequestSpecification res = given().spec(req).log().all()
                     .queryParam("user_id","");
     res.when().get("/advisory-dashboard/monthly-trends/investor")
             .then().log().all().spec(respec);
 }
    @Test
    public void MonthlyTrends_Transaction() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("userId","");
        payload.put("month","");
        List<String> type = Arrays.asList("");
        payload.put("types",type);

    RequestSpecification res = given().spec(req).log().all()
               .body(payload);
    res.when().post("/advisory-dashboard/monthly-trends/transactions")
              .then().log().all().spec(respec);
    }
    @Test
    public void MonthlyTrends_Snapshot() {
        RequestSpecification res = given().spec(req).log().all()
                .body(Adv_payload.SnapshotPayload());
        res.when().post("/advisory-dashboard/investors/snapshot")
                .then().log().all().spec(respec);
    }
}

