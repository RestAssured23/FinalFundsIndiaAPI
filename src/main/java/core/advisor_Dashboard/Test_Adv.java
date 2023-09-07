package core.advisor_Dashboard;
import core.advisor_Dashboard.model.AllReviewResponse;
import core.advisor_Dashboard.model.PortfolioExposureResponseBo;
import core.advisor_Dashboard.model.clientSnapshot;
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

import static io.restassured.RestAssured.given;
import static java.lang.Double.parseDouble;

public class Test_Adv extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    public Test_Adv() {
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
    public void Head_level0() {
        List<String> managerIds = Arrays.asList("1871006", "182424", "359296", "86808", "343573");

        for (String managerId : managerIds) {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("page", 1);
            payload.put("size", 500);
            payload.put("userRole", "string");
            List<String> heads = Arrays.asList("187458", "2152531");
            payload.put("heads", heads);
            List<String> advisors = Arrays.asList();
            payload.put("advisors", advisors);

            List<String> managers = Arrays.asList(managerId);
            payload.put("managers", managers);

            payload.put("financialYear", "2023-2024");
            payload.put("type", "scheme_name");
            //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
            payload.put("aggregateBy", "investment_amount");
            //investor_count / investment_amount
            payload.put("sortBy", "scheme_name");
            //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
            payload.put("order", "asc");

            RequestSpecification res = given().spec(req)
                    .body(payload);
            res.when().post("/tools/portfolio-exposure/l0")
                    .then().log().all().spec(respec);
        }
    }


    @Test
    public void surentharManager_level0() {     //surenthar

        List<String> managerIds = Arrays.asList("182424");
        List<String> advisorIds = Arrays.asList("1853018", "290727", "182943", "2312804", "179758", "182424", "355401", "299133");


        for (String managerId : managerIds) {
            for (String advisorId : advisorIds) {
                Map<String, Object> payload = new LinkedHashMap<>();
                payload.put("page", 1);
                payload.put("size", 500);
                payload.put("userRole", "string");
                List<String> heads = Arrays.asList("187458","2152531");
                payload.put("heads", heads);

                List<String> managers = Arrays.asList(managerId);
                payload.put("managers", managers);
                List<String> advisors = Arrays.asList(advisorId);
                payload.put("advisors", advisors);

                payload.put("financialYear", "2023-2024");
                payload.put("type", "scheme_name");
                //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("aggregateBy", "investment_amount");
                //investor_count / investment_amount
                payload.put("sortBy", "scheme_name");
                //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("order", "asc");

                RequestSpecification res = given().spec(req)
                        .body(payload);
                res.when().post("/tools/portfolio-exposure/l0")
                        .then().log().all().spec(respec);
            }
        }
    }

    @Test
    public void sowmiyaManager_level0() {     //sowmiya
        List<String> managerIds = Arrays.asList("359296");
        List<String> advisorIds = Arrays.asList("187458","1991926","98300","1063336","359296","293316","485280","2312691");

        for (String managerId : managerIds) {
            for (String advisorId : advisorIds) {
                Map<String, Object> payload = new LinkedHashMap<>();
                payload.put("page", 1);
                payload.put("size", 500);
                payload.put("userRole", "string");
                List<String> heads = Arrays.asList("187458", "2152531");
                payload.put("heads", heads);

                List<String> managers = Arrays.asList(managerId);
                payload.put("managers", managers);
                List<String> advisors = Arrays.asList(advisorId);
                payload.put("advisors", advisors);

                payload.put("financialYear", "2023-2024");
                payload.put("type", "scheme_name");
                //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("aggregateBy", "investment_amount");
                //investor_count / investment_amount
                payload.put("sortBy", "scheme_name");
                //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("order", "asc");

                RequestSpecification res = given().spec(req)
                        .body(payload);
                res.when().post("/tools/portfolio-exposure/l0")
                        .then().log().all().spec(respec);
            }
        }
    }
    @Test
    public void surajjainManager_level0() {     //surajjain
        List<String> managerIds = Arrays.asList("1871006");
        List<String> advisorIds = Arrays.asList( "300210", "335339", "1041112", "2402884", "131919","2443123","2589046","2443125","1871006");

        for (String managerId : managerIds) {
            for (String advisorId : advisorIds) {
                Map<String, Object> payload = new LinkedHashMap<>();
                payload.put("page", 1);
                payload.put("size", 500);
                payload.put("userRole", "string");
                List<String> heads = Arrays.asList("187458", "2152531");
                payload.put("heads", heads);

                List<String> managers = Arrays.asList(managerId);
                payload.put("managers", managers);
                List<String> advisors = Arrays.asList(advisorId);
                payload.put("advisors", advisors);

                payload.put("financialYear", "2023-2024");
                payload.put("type", "scheme_name");
                //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("aggregateBy", "investment_amount");
                //investor_count / investment_amount
                payload.put("sortBy", "scheme_name");
                //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("order", "asc");

                RequestSpecification res = given().spec(req)
                        .body(payload);
                res.when().post("/tools/portfolio-exposure/l0")
                        .then().log().all().spec(respec);
            }
        }
    }
    @Test
    public void sonikumariManager_level0() {     //sonikumari
        List<String> managerIds = Arrays.asList("86808");
        List<String> advisorIds = Arrays.asList("1848508","2126808", "2437767","1816472","2437747","2578086","86808","2098351");

        for (String managerId : managerIds) {
            for (String advisorId : advisorIds) {
                Map<String, Object> payload = new LinkedHashMap<>();
                payload.put("page", 1);
                payload.put("size", 500);
                payload.put("userRole", "string");
                List<String> heads = Arrays.asList("187458", "2152531");
                payload.put("heads", heads);

                List<String> managers = Arrays.asList(managerId);
                payload.put("managers", managers);
                List<String> advisors = Arrays.asList(advisorId);
                payload.put("advisors", advisors);

                payload.put("financialYear", "2023-2024");
                payload.put("type", "scheme_name");
                //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("aggregateBy", "investment_amount");
                //investor_count / investment_amount
                payload.put("sortBy", "amc");
                //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("order", "asc");

                RequestSpecification res = given().spec(req)
                        .body(payload);
                res.when().post("/tools/portfolio-exposure/l0")
                        .then().log().all().spec(respec);
            }
        }
    }
    @Test
    public void meenakshiManager_level0() {     //meenakshi
        List<String> managerIds = Arrays.asList("343573");
        List<String> advisorIds = Arrays.asList( "2084062","2437769", "343573","2126807", "1466042");

        for (String managerId : managerIds) {
            for (String advisorId : advisorIds) {
                Map<String, Object> payload = new LinkedHashMap<>();
                payload.put("page", 1);
                payload.put("size", 500);
                payload.put("userRole", "string");
                List<String> heads = Arrays.asList("187458", "2152531");
                payload.put("heads", heads);

                List<String> managers = Arrays.asList(managerId);
                payload.put("managers", managers);
                List<String> advisors = Arrays.asList(advisorId);
                payload.put("advisors", advisors);

                payload.put("financialYear", "2023-2024");
                payload.put("type", "scheme_name");
                //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("aggregateBy", "investment_amount");
                //investor_count / investment_amount
                payload.put("sortBy", "scheme_name");
                //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
                payload.put("order", "asc");

                RequestSpecification res = given().spec(req)
                        .body(payload);
                res.when().post("/tools/portfolio-exposure/l0")
                        .then().log().all().spec(respec);
            }
        }
    }

    @Test
    public void All_Reviews() {
        RequestSpecification res = given().spec(req)
                .body("{\"page\":1,\"size\":20,\"segments\":[\"platinum\",\"gold\",\"silver\",\"digital\"],\"types\":\"all\",\"status\":[\"all\",\"draft\",\"generated\",\"completed\"],\"heads\":[\"187458\",\"2152531\"],\"managers\":[],\"advisors\":[],\"sortBy\":\"advisor_name\",\"sortType\":\"asc\",\"search\":{\"query\":\"rupamlaldas@gmail.com\",\"type\":\"email\"}}");
                //   .body(Adv_payload.AllReviews());
        AllReviewResponse.Root response = res.when().post("/tools/portfolio-review/completed")
                .then().log().all().spec(respec).extract().response().as(AllReviewResponse.Root.class);

        for (AllReviewResponse.Review review : response.getData().getReviews()) {
            String advisorName=review.getAdvisorName();
            String status =review.getStatus();
            for (AllReviewResponse.Investor investor : review.getInvestors()) {
                String investorName = investor.getName();

              //  System.out.println(investorName);
              //  System.out.println(advisorName);
              //  System.out.println(status);
                if (investorName.isBlank()||advisorName.isBlank()||status.isBlank()) {
                    String message = "Investor / Advisor /Status is blank. Email: " + investor.getEmail() + ", PAN: " + investor.getPan();
                     Assert.fail(message); // This assertion will fail the test
                }
            }
        }
    }
}

