package core.advisor_Dashboard;
import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.print.attribute.standard.JobKOctets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;

public class AdvDashboardApiRegression extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    private List<String> investorIDList = new ArrayList<>();
    String investorId,reviewId;  List<String> portfolioList;
    private Map<String, List<String>> investorPortfolioMap = new HashMap<>();
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
      SearchInvestorResponse.Root response=res.when()
              .post("/core/portfolio-review/search-investor")
                .then()
              .log()
              .all()
              .spec(respec)
              .extract().response().as(SearchInvestorResponse.Root.class);
      for(SearchInvestorResponse.UserIdList user:response.getData().getUserIdList()){
          investorIDList.add(String.valueOf(user.getId()));
          System.out.println(investorIDList);
      }

    }
    @Test(priority = 1)
    public void PortfolioList() {
          Map<String, Object> payload = new HashMap<>();
            payload.put("investors",investorIDList);
    /*        List<String> investorIds = Arrays.asList("63910", "1560984");
            payload.put("investors", investorIds);*/

            RequestSpecification res = given().spec(req)
                    .body(payload);
            PortfolioListResponse.Root response = res.when()
                    .post("/core/portfolio-review/portfolio-list")
                    .then()
                    .log()
                    .all()
                    .spec(respec).extract().response().as(PortfolioListResponse.Root.class);

            // Iterate through the response and group Portfolio IDs by Investor ID
            for (PortfolioListResponse.Data data : response.getData()) {
                String investorId = data.getInvestorId();
                String portfolioId = String.valueOf(data.getId());

                // Check if the Investor ID is already in the map
                if (investorPortfolioMap.containsKey(investorId)) {
                    investorPortfolioMap.get(investorId).add(portfolioId); // Add the Portfolio ID to the existing list
                } else {
                    List<String> portfolioList = new ArrayList<>();
                    portfolioList.add(portfolioId);
                    investorPortfolioMap.put(investorId, portfolioList); // Create a new list for this Investor ID
                }
            }
            // Print the Investor ID and the associated list of Portfolio IDs
            for (Map.Entry<String, List<String>> entry : investorPortfolioMap.entrySet()) {
                investorId = entry.getKey();
                portfolioList = entry.getValue();
                System.out.println("Investor ID: " + investorId);
                System.out.println("Portfolio IDs: " + portfolioList);
            }
        }
    @Test(priority = 2)
    public void CurrentPortfolio() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("reviewId", 0);
        payload.put("fiInvestmentStyle", false);

        List<Map<String, Object>> investorData = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : investorPortfolioMap.entrySet()) {
            String investorId = entry.getKey();
            List<String> portfolioList = entry.getValue();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("investorId", investorId);
            data.put("portfolios", portfolioList);

            investorData.add(data);
        }
        payload.put("investors", investorData);

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/core/portfolio-review/current-portfolio")
                .then().log().all().spec(respec);
    }
    @Test(priority = 3)
    public void Rebalance() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("reviewId", 0);
        payload.put("fiInvestmentStyle", false);
        payload.put("checkTax",false);

        List<Map<String, Object>> investorData = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : investorPortfolioMap.entrySet()) {
            String investorId = entry.getKey();
            List<String> portfolioList = entry.getValue();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("investorId", investorId);
            data.put("portfolios", portfolioList);

            investorData.add(data);
        }
        payload.put("investors", investorData);

        RequestSpecification res = given().spec(req)
                .body(payload);
       RebalanceResponse.Root response= res.when().post("/core/portfolio-review/rebalance")
                .then().log().all().spec(respec).extract().response().as(RebalanceResponse.Root.class);
        reviewId= String.valueOf(response.getData().getReviewId());
        System.out.println(reviewId);
    }

    @Test(priority = 4)
    public void AggregatedSummary() {
        RequestSpecification res = given().spec(req)
                        .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/aggregated-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 5)
    public void SystematicPlans() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/systematic-plans")
                .then().log().all().spec(respec);
    }
    @Test(priority = 6)
    public void FundCommentary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
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
        Map<String, Object>payload=new HashMap<>();
        payload.put("amc","");
        payload.put("category","");
        payload.put("subCategory","");
        payload.put("query","");
        payload.put("filterBy","0");
        payload.put("sortBy","0");
        payload.put("investors",investorIDList);
        payload.put("fisCategory","");
        payload.put("fisSubCategory","");

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/core/portfolio-review/scheme-search")
                .then().log().all().spec(respec);
    }
    @Test(priority = 9)
    public void cash_Add() {
     /*   Map<String ,Object>payload=new HashMap<>();
        payload.put("reviewId",reviewId.toString());
        payload.put("amount",5000);*/

        RequestSpecification res = given().spec(req)
                .body("{\"reviewId\":"+reviewId+",\"amount\":5000}");
        res.when().post("/core/portfolio-review/cash")
                .then().log().all().spec(respec);
    }
    @Test(priority = 10)
    public void cashWithdraw() {
     /*   Map<String ,Object>payload=new HashMap<>();
        payload.put("reviewId",reviewId.toString());
        payload.put("amount",5000);*/
        RequestSpecification res = given().spec(req)
                .body("{\"reviewId\":"+reviewId+",\"amount\":-5000}");
        res.when().post("/core/portfolio-review/cash")
                .then().log().all().spec(respec);
    }
    @Test(priority = 11)
    public void AggregateSummary_AddComments() {
        Map<String,Object>payload=new HashMap<>();
        payload.put("comment","API Testing");
        payload.put("reviewId",reviewId);
        payload.put("type","Asset Allocation");

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/core/portfolio-review/add-comment")
                .then().log().all().spec(respec);
    }
    @Test(priority = 12)
    public void CategoryAllocation() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/category-allocation-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 13)
    public void AllocationSummary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId)
                .queryParam("fiInvestmentStyle",false);
        res.when().get("/core/portfolio-review/allocation-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 14)
    public void ExecutionSummary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/execution-summary")
                .then().log().all().spec(respec);
    }
    @Test(priority = 15)
    public void Summary() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
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
        Map<String,Object> payload=new HashMap<>();
        payload.put("reviewId",reviewId);
        payload.put("type","observation");
        payload.put("comment","");
        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/core/portfolio-review/observations")
                .then().log().all().spec(respec);
    }
    @Test(priority = 18)
    public void OldPDF() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId)
                .queryParam("type","old");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().assertThat().contentType("application/pdf");
    }
    @Test(priority = 18)
    public void NewPDF() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId)
                .queryParam("type","New");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().assertThat().contentType("application/pdf");
    }
    @Test(priority = 19)
    public void content() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/communications/content")
                .then().log().all().spec(respec);
    }
    @Test(priority = 20)
    public void communications() {
        Map<String, Object>payload=new HashMap<>();
        payload.put("reviewId",reviewId);
        payload.put("from","qateam@fundsindia.com");
        payload.put("attachmentName",mailid+"-"+reviewId+".pdf");

        List<String> typedata=Arrays.asList("email");
         payload.put("type",typedata);
        List<String>tomail=Arrays.asList(mailid);
         payload.put("to",tomail);
        List<String> ccmail=Arrays.asList();
        payload.put("cc",ccmail);

        RequestSpecification res = given().spec(req)
                        .body(payload);
        res.when().post("/core/portfolio-review/communications")
                .then().log().all().spec(respec);
    }

  /*
    }
    @Test(priority = 20)
    public void communications() {
        Map<String, Object>payload=new HashMap<>();
        payload.put("reviewId","27948");
        payload.put("from","qateam@fundsindia.com");
        payload.put("attachmentName","27948");

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
                .queryParam("reviewId",reviewId);
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
                .queryParam("reviewId", reviewId);
        res.when().get("/core/portfolio-review/mail-content")
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
                .queryParam("reviewId",reviewId);
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
            payload.put("reviewId",reviewId);

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/tools/portfolio-review/follow-up")
                .then().log().all().spec(respec);
    }
    @Test
    public void Follow_Up_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/tools/portfolio-review/follow-up/history")
                .then().log().all().spec(respec);
    }
    @Test
    public void Review_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/tools/portfolio-review/history")
                .then().log().all().spec(respec);
    }
*/
    }

