package core.api;

import core.basepath.AccessPropertyFile;
import core.model.HoldingProfile;
import core.model.InvestedScheme;
import core.model.MFscheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class STPFlow extends AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    String holdingId,investorId, folio, otpRefid, dbOtp, dbRefid,  firstReferenceNo, sourceSchemeName;
    String goalId, goalName, bankId;
    double minAmount, units, minUnit, currentAmount, totalUnits;
    String fromSchemename, fromSchemecode, fromOption, toSchemename, toSchemcode, toOption, amcName, amcCode;
    public STPFlow() throws IOException{
        req = new RequestSpecBuilder()
                .setBaseUri(getBasePath())
                .addHeader("x-api-version", "2.0")
                .addHeader("channel-id", "10")
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
                    investorId = holdResponse.getData().get(foundIndex).getInvestors().get(0).getInvestorId();
                }
                matchFound = true;
                break;
            }
        }
        if (!matchFound) {
            holdingId = holdResponse.getData().get(0).getHoldingProfileId();
            investorId = holdResponse.getData().get(0).getInvestors().get(0).getInvestorId();
            System.out.println("Holding ID is not matched with the property file: " + holdingId);
        }
    }
    @Test(priority = 11)
    public void getInvestedSchemeDetails() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("holdingProfileId", holdingId);
        InvestedScheme.Root response = res.when().get("/core/investor/invested-schemes")
                .then().log().all().spec(respec).extract().response().as(InvestedScheme.Root.class);

        for(InvestedScheme.Datum data:response.getData()) {
            if (data.getFolio().equalsIgnoreCase(folio_pro)) {
                fromSchemename = data.getSchemeName();
                fromSchemecode = data.getSchemeCode();
                folio = data.getFolio();
                units = data.getUnits();
                fromOption = data.getOption();
                goalId = data.getGoalId();
                bankId = data.getBankId();
                minAmount = data.getSwitchOut().getMinimumAmount();
                minUnit = data.getSwitchOut().getMinimumUnits();
                currentAmount = data.getCurrentAmount();
                goalName = data.getGoalName();
                totalUnits = data.getUnits();
                System.out.println(fromSchemename);
                System.out.println(folio);
            }
        }
    }
    @Test(priority = 12)
    public void productSearchMFForm() {
        RequestSpecification res = given().log().all().spec(req)
                .queryParam("page", 1)
                .queryParam("size", 100)
                .queryParam("schemeCodes", fromSchemecode);
        MFscheme.Root response = res.when().get("/core/product-search/mf/schemes")
                .then().log().all().spec(respec).extract().response().as(MFscheme.Root.class);
        for (MFscheme.Content content : response.getData().getContent()) {
            amcName = content.getAmc();
            amcCode = content.getAmcCode();
            sourceSchemeName = content.getName();
            System.out.printf(amcCode + "\t" + amcName + "\t" + sourceSchemeName);
        }
    }
}
