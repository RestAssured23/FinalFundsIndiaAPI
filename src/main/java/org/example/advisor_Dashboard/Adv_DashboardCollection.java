package org.example.advisor_Dashboard;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Adv_DashboardCollection {
    RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri(login_cred.neo)
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .addHeader("x-fi-access-token",login_cred.qateam())
            .setContentType(ContentType.JSON).build();
    ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();

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
                .body("{\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 20,\n" +
                        "  \"segments\": [\n" +
                        "    \"platinum\",\n" +
                        "    \"gold\",\n" +
                        "    \"silver\",\n" +
                        "    \"digital\"\n" +
                        "  ],\n" +
                        "  \"status\": [\n" +
                        "    \"not_started\",\n" +
                        "    \"in_progress\",\n" +
                        "    \"completed\",\n" +
                        "    \"overdue\",\n" +
                        "    \"not_reviewed\"\n" +
                        "  ],\n" +
                        "  \"heads\": [\n" +
                        "    \"2152531\"\n" +
                        "  ],\n" +
                        "  \"managers\": [],\n" +
                        "  \"advisors\": [],\n" +
                        "  \"sortBy\": \"user_name\",\n" +
                        "  \"sortType\": \"asc\"\n" +
                        "}");
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
                .body("{\n" +
                        "    \"reviewId\":\"3040\",\n" +
                        "    \"from\": \"megha.n@fundsindia.com\",\n" +
                        "    \"to\": [\"shamelikumarcr7@gmail.com\"],\n" +
                        "    \"mobiles\": [\"09790790876\"],\n" +
                        "    \"type\":[\"whatsapp\",\"email\"]\n" +
                        "}");
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
/*
    @Test
    public void Review() {
        RequestSpecification res = given().spec(req);
        res.when().get("/core/portfolio-review/dues")
                .then().log().all().spec(respec);
    }
*/

    @Test
    public void Review_Clients() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"page\": 0,\n" +
                        "  \"size\": 0,\n" +
                        "  \"due\": \"month\",\n" +
                        "  \"status\": [\n" +
                        "    \"in_progress\"\n" +
                        "  ],\n" +
                        "  \"fromDate\": \"string\",\n" +
                        "  \"toDate\": \"string\",\n" +
                        "  \"heads\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"managers\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"advisors\": [\n" +
                        "    \"string\"\n" +
                        "  ]\n" +
                        "}");
        res.when().post("/core/portfolio-review/clients")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_communications() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"reviewId\": \"string\",\n" +
                        "  \"from\": \"string\",\n" +
                        "  \"to\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"mobiles\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"type\": [\n" +
                        "    \"whatsapp\"\n" +
                        "  ]\n" +
                        "}");
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_callback() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"tableName\": \"string\",\n" +
                        "      \"condition\": \"string\",\n" +
                        "      \"set\": \"string\",\n" +
                        "      \"upsert\": true\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");
        res.when().post("/core/portfolio-review/callback")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_completed() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"page\": 0,\n" +
                        "  \"size\": 0,\n" +
                        "  \"types\": [\n" +
                        "    \"all\"\n" +
                        "  ],\n" +
                        "  \"status\": [\n" +
                        "    \"all\"\n" +
                        "  ],\n" +
                        "  \"fromDate\": \"string\",\n" +
                        "  \"toDate\": \"string\",\n" +
                        "  \"heads\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"managers\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"advisors\": [\n" +
                        "    \"string\"\n" +
                        "  ]\n" +
                        "}");
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
                .body("{\n" +
                        "  \"date\": \"2023-06-29T23:59:59.000Z\",\n" +
                        "  \"status\": \"Status Test\",\n" +
                        "  \"comments\": \"Comments Test\",\n" +
                        "  \"reviewId\": \"24699\"\n" +
                        "}");
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
    public void Exposure_level2() {
        RequestSpecification res = given().spec(req).log().all()
                .body("{\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 501,\n" +
                        "  \"userRole\": \"string\",\n" +

                    /*    "    \"advisors\": [],\n" +
                        "    \"managers\": [],\n" +
*/
                        "  \"heads\": [\n" +
                        "    \"2152531\"\n" +
                        "  ],\n" +

                        "  \"duration\": \"3y\",\n" +
                        "  \"type\": \"amc\",\n" +

                 //       "    \"level1\": [],\n" +

                        "  \"level2\": [\n" +
                        "    {\n" +

                        "      \"name\": \"scheme_name\",\n" +
                        "      \"value\": \"400013\"\n" +

                      /*  "      \"name\": \"scheme_name\",\n" +
                        "      \"value\": \"Aditya Birla SL Frontline Equity Fund(G)\"\n" +*/
                        "    }\n" +
                        "  ],\n" +
                        "  \"aggregateBy\": \"investment_amount\",\n" +
                        "  \"sortBy\": \"name\",\n" +
                        "  \"order\": \"desc\"\n" +
                        "}");
        res.when().post("/tools/portfolio-exposure/l2")
                .then().log().all().spec(respec);
    }
    @Test
    public void Exposure_level1() {
        RequestSpecification res = given().spec(req).log().all()
                .body("{\n" +
                        "    \"page\": 1,\n" +
                        "    \"size\": 500,\n" +
                        "    \"userRole\": \"string\",\n" +
                        "    \"advisors\": [],\n" +
                        "    \"managers\": [],\n" +

                        "    \"heads\": [\n" +
                        "        \"2152531\"\n" +
                        "    ],\n" +
                        "    \"duration\": \"3y\",\n" +
                        "    \"type\": \"scheme_name\",\n" +
                        "    \"level1\": [\n" +
                        "        {\n" +
                        "            \"name\": \"credit_quality\",\n" +
                        "            \"value\": \"AA & Below\"\n" +
                        "        }\n" +

                        "  ],\n" +
                        //    "    \"level2\": [],\n" +
                        "    \"aggregateBy\": \"investment_amount\",\n" +
                        "    \"sortBy\": \"customer_percentage\",\n" +
                        "    \"order\": \"asc\",\n" +

                        "    \"search\": {\n" +
                        "        \"type\": \"scheme_name\",\n" +
                        "        \"query\": \"Aditya Birla SL Savings Fund-Reg(G)\"\n" +
                        "    }\n" +
                        "}");
        res.when().post("/tools/portfolio-exposure/l1")
                .then().log().all().spec(respec);
    }

}


//Testing
//Testing_1