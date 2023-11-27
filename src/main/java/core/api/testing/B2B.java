package core.api.testing;

import core.api.Payload;
import core.model.HoldingProfile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static core.api.CommonVariable.*;
import static io.restassured.RestAssured.given;

public class B2B extends TestAccessPropertyFile {

    public B2B() throws IOException {

    String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MDg2OCIsInNjb3BlcyI6InJlYWQsd3JpdGUiLCJuYW1lIjoiVmlqYXlhIEJoYXNrYXIgLksiLCJlbWFpbCI6ImFybjgyOTk2QHBhcnRuZXIuZnVuZHNpbmRpYS5jb20iLCJtb2JpbGUiOiI4OTM5MzQyMDUyIiwibWFuYWdlbWVudC11c2VyLWlkIjoxODkwNzQzLCJtYW5hZ2VtZW50LXVzZXItcm9sZXMiOiJhZG1pbiIsImlzcyI6ImZ1bmRzaW5kaWEuY29tIiwianRpIjoiY2JhNDNhNmYtNzFiMC00N2YwLTlkMmYtMGRkZTMyMjg2ZWVmIiwiaWF0IjoxNjk5MzQzMzc2LCJleHAiOjE2OTkzNDcwMzZ9.tlb2rTxPNo-gLf2Pv9LjEYeDuNSm9YfvTS3DzHT8wROnyjhD9KDA7olm3UQGuAgYfmxw5ZUoOOP5Ukp51-0EhQ";



    req = new RequestSpecBuilder()
                .setBaseUri("https://api.fundsindia.com")
                .addHeader("x-api-version", "1.0")
             //   .addHeader("channel-id","10")
                .addHeader("x-fi-access-token", token)
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();
        respec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    SoftAssert softAssert=new SoftAssert();


    @Test
    public void adv_dashboard_test() {
        RequestSpecification res = given().spec(req);
        B2Bbo.Root response =res.when().get("/manage/ifa/advisor/dashboard")
                .then().log().all().spec(respec).extract().response().as(B2Bbo.Root.class);
    }


    @Test
    public void Sys_Plans() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/manage/ifa/advisor/systematic-plans")
                .then().log().all().spec(respec).extract().response().asString();

    }

    @Test(priority = 1)
    public void userProfile() {
        RequestSpecification res = given().spec(req)
                .body("{\n" +
                        "  \"name\": \"\",\n" +
                        "  \"email\": \"\",\n" +
                        "  \"mobile\": \"\",\n" +
                        "  \"orderBy\": \"\",\n" +
                        "  \"orderType\": \"ASC\",\n" +
                        "  \"page\": 1,\n" +
                        "  \"size\": 50\n" +
                        "}");
        response=res.when().post("/manage/ifa/client/users")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 2)
    public void User_Sys_Plan() {
        RequestSpecification res = given().spec(req)
                .queryParam("userId",152389);
        response=res.when().get("/manage/ifa/client/systematic-plans")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 2)
    public void Fetch_PartnerPortfolio() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/manage/ifa/advisor/portfolios")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 2)
    public void Adv_Details() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/manage/ifa/advisor/details")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void adv_dashboard() {
        //1573649
        String Aum1[]={"3506033.931","25348059.06"};   // Debt and Equity
        String inflow1[]={"18535000","9375000"};
//1457752                    // others , commodity , debt ,equity , hybrid
        String Aum2[]={"18761818.77","320514.479","8208313.161","161647718.2","56477855.8"};
        String inflow2[]={"11686575.38","465900","86576423.17","130979210.4","90523631.89"};
//1442822
        String Aum3[]={"68588017.31","4338768.813","198996750","1398047895","63034398.92"};
        String inflow3[]={"24537400","1363000","1657969522","509468198.9","85242976"};
//1442821
        String Aum4[]={"2444.451365","71110.94474","2791353.997","55972092.74","1564840.832"};
        String inflow4[]={"78000","95600","145756483","80501769.68","1129532.529"};
//1442820
        String Aum5[]={"40370271.19","7253359.784","123626028.5","1038457500","51893374.56"};
        String inflow5[]={"12775000","3830000","996848340.7","168690423.2","43278000"};
//1442815
        String Aum6[]={"19508712.44","2105625.097","92745322.8","743127607.9","51498946.85"};
        String inflow6[]={"4872502.15","280000","638145832.9","194021596.2","39957539.53"};
//416217
        String test_aum[]={"0","11047.74254","259461.3914","4635.38285"};
        String test_inflow[]={"4000","1002705.38","501500","23525.15"};

        // others , commodity , debt ,equity , hybrid


        RequestSpecification res = given().spec(req);
        B2Bbo.Root response =res.when().get("/manage/ifa/advisor/dashboard")
                .then().log().all().spec(respec).extract().response().as(B2Bbo.Root.class);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        System.out.println("totalClients : "+response.getData().getSummary().getTotalClients());
        System.out.println("activatedClients : "+response.getData().getSummary().getActivatedClients());
        System.out.println("nonActivatedClients : "+response.getData().getSummary().getNonActivatedClients());

        System.out.println("========================================================");
        System.out.println(response.getData().getDetails().get(0).getDataFor());
        System.out.println("totalAmount :"+decimalFormat.format(response.getData().getDetails().get(0).getTotalAmount()));

        for (int i=0;i<response.getData().getDetails().get(0).getAllocation().size();i++){
            String expectedValue = String.format("%.2f",
                    Double.parseDouble(test_aum[i]));

            String actualValue =decimalFormat.format(response.getData().getDetails().get(0).getAllocation().get(i).getAllocationAmount());
            String category =response.getData().getDetails().get(0).getAllocation().get(i).getCategory();


            softAssert.assertEquals(expectedValue, actualValue, "AUM : " +category +" : ");

        }
        System.out.println("========================================================");
        System.out.println(response.getData().getDetails().get(1).getDataFor());
        for (int j=0;j<response.getData().getDetails().get(1).getAllocation().size();j++){
            String expectedValue = String.format("%.2f",
                    Double.parseDouble(test_inflow[j]));

            String actualValue =decimalFormat.format(response.getData().getDetails().get(1).getAllocation().get(j).getAllocationAmount());
            String category =response.getData().getDetails().get(1).getAllocation().get(j).getCategory();
            // System.out.println(actualValue);

            softAssert.assertEquals(expectedValue, actualValue, "Inflow : " +category +" : ");

        }
        softAssert.assertAll();
    }
}

