package core.api;

import core.basepath.AccessPropertyFile;
import core.model.MFscheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import core.model.HoldingProfile;
import lombok.Value;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static core.api.CommonVariable.*;
public class GetAPI extends AccessPropertyFile {

    public GetAPI() throws IOException {
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", getChannelID())
                .addHeader("x-fi-access-token", getAccessToken())
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
    public void feature() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/features")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void userProfile() {
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
                holdingId = idList;
                System.out.println("Holding ID is matched with the property file: " + holdingId);
                if (data.getHoldingProfileId().equalsIgnoreCase(holdingId)) {
                    int foundIndex = holdResponse.getData().indexOf(data);
                    InvestorId = holdResponse.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            holdingId = holdResponse.getData().get(0).getHoldingProfileId();
            InvestorId = holdResponse.getData().get(0).getInvestors().get(0).getInvestorId();
            System.out.println("Holding ID is not matched with the property file: " + holdingId);
        }
    }
    @Test(priority = 1)
    public void dashboard() {
            RequestSpecification res = given().spec(req)
                    .queryParam("holdingProfileId", holdingId);
            response = res.when().get("/core/investor/dashboard")
                    .then().log().all().spec(respec).extract().response().asString();
            Reporter.log(response);
    }
    @Test(priority = 1)
    public void dashboardPortfolio() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/dashboard/portfolio")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void systematicPlan() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/systematic-plan/sips")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void investedSchemes() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void recentTransactions() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void investorMandates() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId);
      response=  res.when().get("/core/investor/mandates")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void stp() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId)
                .queryParam("page", "1")
                .queryParam("size", "50");
        response=res.when().get("/core/investor/current-stps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void powerSTP() {
        RequestSpecification res = given().log().all().spec(req);
       response= res.when().get("/core/investor/power-stps")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test(priority = 1)
    public void triggers() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", holdingId);
       response= res.when().get("/core/investor/current-triggers")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void swp() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", holdingId)
                .queryParam("page", "1")
                .queryParam("size", "50");

       response= res.when().get("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void folioBanklist() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/folio-bank-list")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void contactInfo() {
        RequestSpecification res = given().spec(req);
        response=res.when().get("/core/investor/contacts")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void authorization() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
       response= res.when().get("/core/investor/transactions/authorization")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void pendingPayments() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
       response= res.when().get("/core/investor/pending-payments")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void goals() {
        RequestSpecification res = given().spec(req);
       response= res.when().get("/core/goals")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void investorGoal() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/investor/goals")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void productSearchMfForm() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
       response= res.when().get("/core/product-search/mf/form")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void announcements() {
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
    public void productSearchMfFormPost() {
        RequestSpecification res = given().spec(req)
                .body(Payload.product_Search());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void superSavings() {
        RequestSpecification res = given().spec(req)
                .body(Payload.Super_Savings());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void nfoSearch() {
        RequestSpecification res = given().spec(req)
                .body(Payload.NFO());
       response= res.when().post("/core/product-search/mf")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void schemeInfo() //Scheme_Info
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
    public void dashboardPortfolioAllocationAssetAll() {
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
    public void mfScheme() {
        RequestSpecification res = given().spec(req)
                .queryParam("schemeCodes","44958")
                .queryParam("page",1)
                .queryParam("size",10);
        response=res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test
    public void dashboardPortfolioAllocationCategory() {
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
    public void dashboardPortfolioAllocationFiStyle() {
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
    public void dashboardPortfolioAllocationCredit() {
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
    public void selectFunds() {
        RequestSpecification res = given().spec(req)
                .body(Payload.Select_Funds());
      response=  res.when().post("/core/product-search/mf/select-funds")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void newNomineeDeclaration() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", holdingId);
      response=  res.when().get("/core/investor/nominees/declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void getExistingNomineeMf() {
        //Investor ID for Equity and Holding id for MF
        RequestSpecification res=given().spec(req)
                .queryParam("holdingProfileId",holdingId)
                .queryParam("product","MF");
        response=res.when().get("/core/investor/nominees/existing-declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void getEquityGetNominee() {           //Get API
        //Investor ID for Equity and Holding id for MF
        RequestSpecification res=given().spec(req)
                .queryParam("investorId",InvestorId)      // 934332(saravanan)  , 177973(local)
                .queryParam("product","EQUITY");
     response=   res.when().get("/core/investor/nominees/existing-declaration")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void viewInvestor() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId",InvestorId);
       response= res.when().get("/core/investor/accounts")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }

    //FD API
    @Test
    public void fdCompany() {
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
    public void allSupportAuthorization() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "0");
       response= res.when().get("/core/investor/transactions/authorization")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void allSupportPendingPayments() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", "0");
        response=res.when().get("/core/investor/pending-payments")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void allSupportRecentTransactions() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
       response= res.when().get("/core/investor/recent-transactions")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void allSupportSysPlan() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
        response=res.when().get("/core/investor/systematic-plan/sips")
                .then().log().all().spec(respec).extract().response().asString();
    }
    @Test(priority = 1)
    public void allSupportSTP() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0)
                .queryParam("page", "1")
                .queryParam("size", "50");
        response=res.when().get("/core/investor/current-stps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void allSupportSWP() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0)
                .queryParam("page", "1")
                .queryParam("size", "50");

      response=  res.when().get("/core/investor/current-swps")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void allSupportTriggers() {
        RequestSpecification res = given().spec(req)
                .queryParam("holdingProfileId", 0);
        response=res.when().get("/core/investor/current-triggers")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void UserDashboard() {
        RequestSpecification res = given().spec(req)
               .queryParam("holdingProfileId", holdingId);
        response=res.when().get("/core/user/dashboard")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void InvestorBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId);
        res.when().get("/core/investor/banks")
                .then().log().all().spec(respec);
    }
    @Test(priority = 1)
    public void ListBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId);
        //       .queryParam("userBankId", 1);
        response = res.when().get("/core/investor/banks")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
    @Test(priority = 1)
    public void QRBank() {
        RequestSpecification res = given().spec(req)
                .queryParam("investorId", InvestorId)
                .queryParam("source", "ADD_BANK");
        response = res.when().get("/core/investor/banks/qr")
                .then().log().all().spec(respec).extract().response().asString();
        Reporter.log(response);
    }
  /*  @Test         // Rest API
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

