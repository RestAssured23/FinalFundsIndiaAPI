package core.advisor_Dashboard;
import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import static io.restassured.RestAssured.given;

public class APIRegression extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    private final List<String> investorIDList = new ArrayList<>();
    String investorId,reviewId,firstGeneratedReviewId,firstCompletedReviewId;  List<String> portfolioList;
    private final Map<String, List<String>> investorPortfolioMap = new HashMap<>();
    SoftAssert softAssert = new SoftAssert();
    public APIRegression() {
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
        RequestSpecification res = given().spec(req)
                .body("{\"reviewId\":"+reviewId+",\"amount\":5000}");
        res.when().post("/core/portfolio-review/cash")
                .then().log().all().spec(respec);
    }
    @Test(priority = 10)
    public void cashWithdraw() {
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
    public void CommunicationsContent() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/core/portfolio-review/communications/content")
                .then().log().all().spec(respec);
    }
    @Test(priority = 20)
    public void SendEmail() {
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

    @Test(priority = 21)
    public void Filter_Form() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/advisory-dashboard/filters/form")
                .then().log().all().spec(respec);
    }
    @Test(priority = 22)
    public void Dues() {
        RequestSpecification res = given().spec(req);
        res.when().get("/tools/portfolio-review/dues")
                .then().log().all().spec(respec);
    }
    @Test(priority = 23)
    public void AllClients() {
        List<Map<String, Object>> payloads = Arrays.asList(
                Regressionpayload.AllClients(),
                Regressionpayload.AllClients_month(),
                Regressionpayload.AllClients_Week(),
                Regressionpayload.AllClients_overdue(),
                Regressionpayload.AllClients_panSearch(),
                Regressionpayload.AllClients_mobilesearch(),
                Regressionpayload.AllClients_DateFilter()
        );
        for (Map<String, Object> payload : payloads) {
            RequestSpecification res = given().spec(req)
                    .body(payload);
            AllClientsResponse.Root response= res.when().post("/tools/portfolio-review/clients")
                    .then().log().all().spec(respec).extract().response().as(AllClientsResponse.Root.class);
            }
    }


  @Test(priority = 24)
  public void All_Reviews() {
      RequestSpecification res = given().spec(req)
              .body(Adv_payload.AllReviews());
      AllReviewResponse.Root response = res.when().post("/tools/portfolio-review/completed")
              .then().log().all().spec(respec).extract().response().as(AllReviewResponse.Root.class);

      // Initialize variables to store first Generated and Completed IDs
      firstGeneratedReviewId = null;
      firstCompletedReviewId = null;

      for (AllReviewResponse.Review review : response.getData().getReviews()) {
          String reviewId = String.valueOf(review.getReviewId());
          String reviewStatus = review.getStatus();

          if ("Generated".equalsIgnoreCase(reviewStatus)) {
              if (firstGeneratedReviewId == null) {
                  firstGeneratedReviewId = reviewId;
                  System.out.println("First Generated ID: " + firstGeneratedReviewId);
              }
          } else if ("Completed".equalsIgnoreCase(reviewStatus)) {
              if (firstCompletedReviewId == null) {
                  firstCompletedReviewId = reviewId;
                  System.out.println("First Completed ID: " + firstCompletedReviewId);
              }
          }
          // Exit the loop if both first Generated and Completed IDs are found
          if (firstGeneratedReviewId != null && firstCompletedReviewId != null) {
              break;
          }
      }
  }
    @Test(priority = 25)
    public void GeneratedPDFDownload() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",firstGeneratedReviewId)
                .queryParam("type","New");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().assertThat().contentType("application/pdf");
    }
    @Test(priority = 26)
    public void CompletedPDFDownload() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",firstCompletedReviewId)
                .queryParam("type","New");
        res.when().get("/core/portfolio-review/download")
                .then().log().all().assertThat().contentType("application/pdf");
    }

   @Test(priority = 27)
    public void Review_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/tools/portfolio-review/history")
                .then().log().all().spec(respec);
    }

    @Test(priority = 28)
    public void Follow_Up_History_Post() {
        LocalDateTime updatedDate = LocalDateTime.now().plusDays(2);
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
    @Test(priority = 29)
    public void Follow_Up_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/tools/portfolio-review/follow-up/history")
                .then().log().all().spec(respec);
    }
    @Test(priority = 30)
    public void Quality_History() {
        RequestSpecification res = given().spec(req)
                .queryParam("reviewId",reviewId);
        res.when().get("/tools/portfolio-review/quality/history")
                .then().log().all().spec(respec);
    }
    @Test(priority = 31)
    public void Quality_Review() {
        LocalDateTime updatedDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedUpdatedDate = updatedDate.format(formatter);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("reviewId", String.valueOf(reviewId));
        payload.put("critical", "yes");         // [yes , no]
        payload.put("date", formattedUpdatedDate);
        payload.put("comments", "testing");

        List<Map<String, Object>> parameter = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("id","1");
        data.put("description","Testing");
        data.put("value","yes");
        List<String> tag = Arrays.asList("Tags testing");
        data.put("tags",tag);
        parameter.add(data);
        payload.put("parameters",parameter);

        RequestSpecification res = given().spec(req)
                .body(payload);
        res.when().post("/tools/portfolio-review/quality")
                .then().log().all().spec(respec);
    }
}

