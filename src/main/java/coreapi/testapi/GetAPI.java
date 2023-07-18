package coreapi.testapi;

import coreapi.basepath.AccessPropertyFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import coreapi.model.HoldingProfile;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetAPI extends AccessPropertyFile {
    RequestSpecification req =new RequestSpecBuilder()
                .setBaseUri(getBasePath())
            .addHeader("x-api-version","2.0")
            .addHeader("channel-id","10")
            .addHeader("x-fi-access-token", getAccessToken())
            .setContentType(ContentType.JSON).build().log().all();
    ResponseSpecification respec =new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();

    String Holdingid,InvestorId,response;

    public GetAPI() throws IOException {
    }
    @Test
    public void Feature() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void User_Profile() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/user-profile")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void holdingProfile() {
        boolean matchFound = false; // Flag variable
        RequestSpecification res = given().spec(req);
        HoldingProfile.Root holdResponse = res.when().get("/core/investor/holding-profiles")
                .then().log().all().spec(respec).extract().response().as(HoldingProfile.Root.class);

        for (HoldingProfile.Datum data : holdResponse.getData()) {
            String idList = data.getHoldingProfileId();
            if (idList.equalsIgnoreCase(holdingid_pro)) {
                Holdingid = idList;
                System.out.println("Holding ID is matched with the property file: " + Holdingid);
                if (data.getHoldingProfileId().equalsIgnoreCase(Holdingid)) {
                    int foundIndex = holdResponse.getData().indexOf(data);
                    InvestorId = holdResponse.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            Holdingid = holdResponse.getData().get(0).getHoldingProfileId();
            InvestorId = holdResponse.getData().get(0).getInvestors().get(0).getInvestorId();
            System.out.println("Holding ID is not matched with the property file: " + Holdingid);
        }
    }
    @Test(priority = 1)
    public void Dashboard() {
            RequestSpecification res = given().spec(req)
                    .queryParam("holdingProfileId", Holdingid);
            response = res.when().get("/core/investor/dashboard")
                    .then().log().all().spec(respec).extract().response().asString();
            Reporter.log(response);
    }
    @Test(priority = 1)
    public void Dashboard_Portfolio() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/dashboard/portfolio")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Systematic_plan() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/systematic-plan/sips")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Invested_Schemes() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Recent_Transactions() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Investor_Mandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId);
      response=  res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void STP() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid)
                .queryParam("page", "1")
                .queryParam("size", "50");
        response=res.when().get("/core/investor/current-stps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Power_STPs() {
        RequestSpecification res = given().log().all().spec(req);
       response= res.when().get("/core/investor/power-stps")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test(priority = 1)
    public void Triggers() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid);
       response= res.when().get("/core/investor/current-triggers")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void SWP() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", Holdingid)
                .queryParam("page", "1")
                .queryParam("size", "50");

       response= res.when().get("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Folio_Banklist() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/folio-bank-list")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Contact_Info() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/investor/contacts")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Transactions_Authorization() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
       response= res.when().get("/core/investor/transactions/authorization")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Pending_Payments() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
       response= res.when().get("/core/investor/pending-payments")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Investor_Goals() {
        RequestSpecification res = given().spec(req);
       response= res.when().get("/core/goals")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Investor_Goal() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
        response=res.when().get("/core/investor/goals")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void product_search_mf_form() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
       response= res.when().get("/core/product-search/mf/form")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Announcements() {
        RequestSpecification res = given().spec(req);
      response=  res.when().get("/core/user/sign-up/announcements")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void lookup() {
        RequestSpecification res = given().spec(req)
                .queryParam("types", "Location");  //State,Location,country,fd_nominee_salutation
        response=res.when().get("/core/lookups")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void ProductSearch_MF_Form() {
        RequestSpecification res = given().spec(req)
                .body(Payload.product_Search());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Super_Savings() {
        RequestSpecification res = given().spec(req)
                .body(Payload.Super_Savings());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void NFO_Search() {
        RequestSpecification res = given().spec(req)
                .body(Payload.NFO());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Scheme_info() //Scheme_Info
    {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "0");
        payload.put("showZeroHoldings", true);
        Map<String, Object> sort = new HashMap<>();
        sort.put("by", "investment_amount");
        /*investment_amount, current_amount, scheme_name, portfolio_name,
         * today_change, annualized_return, return_1yr, return_3yr,
         * return_5yr, since_inception_return
         */
        sort.put("type", "desc");                //desc , asc
        payload.put("sort", sort);
        payload.put("type", "portfolio");        //	portfolio, scheme_info, asset, tax

        RequestSpecification res = given().spec(req)
                .body(payload);
       response= res.when().post("/core/investor/dashboard/portfolio")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    //Asset Allocation
    @Test
    public void Dashboard_portfolio_Allocation_Asset_All() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "0");
        payload.put("portfolioId", "0");
        payload.put("detailType", "asset");
        payload.put("duration", "three_month");

        RequestSpecification res = given().spec(req)
                .body(payload);
      response=  res.when().post("/core/investor/dashboard/portfolio/allocations")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void MF_Scheme() {
        RequestSpecification res = given().spec(req)
                .queryParam("schemeCodes",876)
                .queryParam("page",1)
                .queryParam("size",10);
        response=res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Dashboard_portfolio_Allocation_category() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "0");
        payload.put("portfolioId", "0");
        payload.put("detailType", "category");
        payload.put("duration", "three_month");

        RequestSpecification res = given().spec(req)
                .body(payload);
     response=   res.when().post("/core/investor/dashboard/portfolio/allocations")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Dashboard_portfolio_Allocation_fi_style() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "0");
        payload.put("portfolioId", "0");
        payload.put("detailType", "fi_style");
        payload.put("duration", "three_month");

        RequestSpecification res = given().spec(req)
                .body(payload);
     response=   res.when().post("/core/investor/dashboard/portfolio/allocations")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Dashboard_portfolio_Allocation_credit() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("holdingProfileId", "0");
        payload.put("portfolioId", "0");
        payload.put("detailType", "credit_quality");
        payload.put("duration", "three_month");

        RequestSpecification res = given().spec(req)
                .body(payload);
        response =res.when().post("/core/investor/dashboard/portfolio/allocations")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void Select_Funds() {
        RequestSpecification res = given().spec(req)
                .body(Payload.Select_Funds());
      response=  res.when().post("/core/product-search/mf/select-funds")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void New_Nominee_Declaration() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", Holdingid);
      response=  res.when().get("/core/investor/nominees/declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Get_Existing_Nominee_MF() {
        //Investor ID for Equity and Holding id for MF
        RequestSpecification res=given().spec(req)
                .queryParam("holdingProfileId",Holdingid)
                .queryParam("product","MF");
        response=res.when().get("/core/investor/nominees/existing-declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void Get_Equity_GetNominee() {           //Get API
        //Investor ID for Equity and Holding id for MF
        RequestSpecification res=given().spec(req)
                .queryParam("investorId",InvestorId)      // 934332(saravanan)  , 177973(local)
                .queryParam("product","EQUITY");
     response=   res.when().get("/core/investor/nominees/existing-declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void View_Investor() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId",InvestorId);
       response= res.when().get("/core/investor/accounts")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

    //FD API
    @Test
    public void FD_Company() {
        RequestSpecification res = given().spec(req)
                .body("{companyId:[2]}");
       response= res.when().post("/core/product-search/fixed-deposit/companies")
               .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void fdTransaction() {
        HashMap<String, Object> trans = new HashMap<>();
        trans.put("holdingProfileId", "0");
        ArrayList<String> type = new ArrayList<>();
        //type.add("holdings");            // [ pendings, holdings ]
        type.add("pendings");
        trans.put("type", type);

        RequestSpecification res = given().spec(req)
                .body(trans);
        response=res.when().post("/core/investor/fixed-deposit/transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_Authorization() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "0");
       response= res.when().get("/core/investor/transactions/authorization")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_Pending_Payments() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "0");
        response=res.when().get("/core/investor/pending-payments")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_Recent_Transactions() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
       response= res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_Sys_plan() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
        response=res.when().get("/core/investor/systematic-plan/sips")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test(priority = 1)
    public void AllSupport_STP() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0)
                .queryParam("page", "1")
                .queryParam("size", "50");
        response=res.when().get("/core/investor/current-stps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_SWP() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0)
                .queryParam("page", "1")
                .queryParam("size", "50");

      response=  res.when().get("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void AllSupport_Triggers() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
        response=res.when().get("/core/investor/current-triggers")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
   /* @Test(priority = 1)
    public void callback() {
        RequestSpecification res=given().spec(req);
           //     .queryParam("investorId","1401246")  ;
        response= res.when().get("/core/investor/advisors/callback")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }*/

  /*  @Test(priority = 1)
    public void main_dashboard_restAPI() throws IOException
    {
        RestAssured.baseURI = "https://webrevampneo.fundsindia.com";
        response = given().log().all()
                .header("x-api-version", "2.0")
                .header("channel-id", "10")
                .header("x-fi-access-token", accesstoken())
                .header("Content-Type", "application/json")

                .body("").when().post("/rest/fi/dashboard/v1/user")
                .then().log().all().assertThat().statusCode(200)
                .header("Content-Type", "application/json")
                .extract().response().asString();
        Reporter.log(response);
    }*/
}

