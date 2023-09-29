package core.advisor_Dashboard;
import core.advisor_Dashboard.model.ClientDetailsResponse;
import core.advisor_Dashboard.model.clientSnapshot;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

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
    public void ClientSnapshot_percentageCheck() {
        int aumYTD,aumLFY,totalinflowYTD,totalinflowLFY,
                sipYTD,sipLFY,lumYTD,lumLFY,t_inYTD,t_inLFY,
                totaloutflowYTD,totaloutflowLFY,redemYTD,redemLFY,swpYTD,
                swpLFY,netflowYDT,netflowLFY,netperYDT,netperLFY,t_outYTD,t_outLFY;


        String inflow,outflow,netpercentage,mtm,mtmpercentage,aumgrowthpercentage,clientname,userid;
        double curaum,startaum;
        RequestSpecification requestSpec = given().spec(req)
                .body(Adv_payload.SnapshotPayload());
        clientSnapshot.Root response = requestSpec
                .when()
                .post("/tools/advisory-dashboard/investors/snapshot")
                .then().log().all().spec(respec)
                .extract()
                .response()
                .as(clientSnapshot.Root.class);

        int index=10;
        userid=response.getData().getRows().get(index).getUserId();
        clientname= response.getData().getRows().get(index).getName();
        startaum= Double.parseDouble(response.getData().getRows().get(index).getBaseAum());
        inflow=response.getData().getRows().get(index).getInflow();
        outflow=response.getData().getRows().get(index).getOutflow();
        netpercentage=response.getData().getRows().get(index).getNetflowGrowthPercentage();
        mtm=response.getData().getRows().get(index).getMtm();
        mtmpercentage=response.getData().getRows().get(index).getMtmPercentage();
        curaum= Double.parseDouble(response.getData().getRows().get(index).getCurrentAum());
        aumgrowthpercentage=response.getData().getRows().get(index).getAumGrowthPercentage();

       /* double AUMGrowthPercentage =((curaum - startaum) / startaum) * 100;
        double roundedAUMGrowthPercentage = Math.floor(AUMGrowthPercentage * 100.0) / 100.0;
        System.out.println(userid);
        System.out.println("AUMgrowthpercentage " +roundedAUMGrowthPercentage);
        softAssert.assertEquals(aumgrowthpercentage,roundedAUMGrowthPercentage);
        //  Assert.assertEquals(roundedAUMGrowthPercentage,aumgrowthpercentage);*/


//Get Client Details
        RequestSpecification res1 = given().spec(req)
                .queryParam("user_id",userid);
        ClientDetailsResponse.Root clientresponse=res1.when().get("/tools/advisory-dashboard/monthly-trends/investor")
                .then().log().all().spec(respec).extract().response().as(ClientDetailsResponse.Root.class);
        aumYTD= (int) clientresponse.getData().getSummary().get(0).getYtd();
        aumLFY= (int) clientresponse.getData().getSummary().get(0).getLfy().getValue();

        totalinflowYTD= (int) clientresponse.getData().getSummary().get(1).getYtd();
        totalinflowLFY= (int) clientresponse.getData().getSummary().get(1).getLfy().getValue();

        sipYTD= (int) clientresponse.getData().getSummary().get(2).getYtd();
        sipLFY= (int) clientresponse.getData().getSummary().get(2).getLfy().getValue();

        lumYTD= (int) clientresponse.getData().getSummary().get(3).getYtd();
        lumLFY= (int) clientresponse.getData().getSummary().get(3).getLfy().getValue();

        swpYTD= (int) clientresponse.getData().getSummary().get(5).getYtd();
        swpLFY= (int) clientresponse.getData().getSummary().get(5).getLfy().getValue();

        totaloutflowYTD= (int) clientresponse.getData().getSummary().get(4).getYtd();
        totaloutflowLFY= (int) clientresponse.getData().getSummary().get(4).getLfy().getValue();

        redemYTD= (int) clientresponse.getData().getSummary().get(6).getYtd();
        redemLFY= (int) clientresponse.getData().getSummary().get(6).getLfy().getValue();

        netflowYDT= (int) clientresponse.getData().getSummary().get(7).getYtd();
        netflowLFY= (int) clientresponse.getData().getSummary().get(7).getLfy().getValue();

        netperYDT = (int) clientresponse.getData().getSummary().get(8).getYtd();
        String YDT = Integer.toString(netperYDT);

        String valueAsString = (String) clientresponse.getData().getSummary().get(8).getLfy().getValue();
        netperLFY = Integer.parseInt(valueAsString);

        int netflowpercentagecalculation,aumGrowthPercentagecalculation,mtmcalculationcalculation,mtppercentagecalculation,
                aumwithoutmtmcalculation,aumwithoutmtmpercentagecalculation,sipgrowthpercentagecalculation,
                lumgrowthpercentagecalculation,inflowcalculation,outflowcalculation;
//calculation
netflowpercentagecalculation = (int) (((double) netflowYDT / aumLFY) * 100);
aumGrowthPercentagecalculation= (int) (((curaum-aumLFY)/aumLFY)*100);
mtmcalculationcalculation= (int) (curaum-aumLFY);
mtppercentagecalculation=(int) (curaum-aumLFY)/(aumLFY*100);
inflowcalculation=sipYTD+lumYTD;
outflowcalculation=redemYTD+swpYTD;
//sipgrowthpercentagecalculation=(sipYTD/sipLFY)*100;
//lumgrowthpercentagecalculation=(lumYTD/lumLFY)*100;




softAssert.assertEquals(netflowpercentagecalculation, Double.parseDouble(netpercentage),0.001,"clientsnapshotNetflow %");
softAssert.assertEquals(aumGrowthPercentagecalculation, Double.parseDouble(aumgrowthpercentage),0.001,"clientsnapshotAUMgrowth%");
//softAssert.assertEquals(mtmcalculationcalculation, Double.parseDouble(mtm),0.001,"clientsnapshotMTM");
softAssert.assertEquals(inflowcalculation, Double.parseDouble(inflow),0.001,"clientsnapshotinflow");
softAssert.assertEquals(outflowcalculation, Double.parseDouble(outflow),0.001,"clientsnapshotoutflow");

        System.out.println("mtppercentagecalculation :"+mtppercentagecalculation);
    //   System.out.println(aumwithoutmtmpercentagecalculation);

        softAssert.assertAll();

    }














    @Test
    public void testMonthlyTrends_Investor() {
        int aumYTD,aumLFY,totalinflowYTD,totalinflowLFY,
                sipYTD,sipLFY,lumYTD,lumLFY,t_inYTD,t_inLFY,
                totaloutflowYTD,totaloutflowLFY,redemYTD,redemLFY,swpYTD,
                swpLFY,netflowYDT,netflowLFY,netperYDT,netperLFY,t_outYTD,t_outLFY;


        RequestSpecification res1 = given().spec(req)
                .queryParam("user_id","431340");
        ClientDetailsResponse.Root clientresponse=res1.when().get("/tools/advisory-dashboard/monthly-trends/investor")
                .then().log().all().spec(respec).extract().response().as(ClientDetailsResponse.Root.class);
        aumYTD= (int) clientresponse.getData().getSummary().get(0).getYtd();
        aumLFY= (int) clientresponse.getData().getSummary().get(0).getLfy().getValue();

        totalinflowYTD= (int) clientresponse.getData().getSummary().get(1).getYtd();
        totalinflowLFY= (int) clientresponse.getData().getSummary().get(1).getLfy().getValue();

        sipYTD= (int) clientresponse.getData().getSummary().get(2).getYtd();
        sipLFY= (int) clientresponse.getData().getSummary().get(2).getLfy().getValue();

        lumYTD= (int) clientresponse.getData().getSummary().get(3).getYtd();
        lumLFY= (int) clientresponse.getData().getSummary().get(3).getLfy().getValue();

        swpYTD= (int) clientresponse.getData().getSummary().get(5).getYtd();
        swpLFY= (int) clientresponse.getData().getSummary().get(5).getLfy().getValue();

        totaloutflowYTD= (int) clientresponse.getData().getSummary().get(4).getYtd();
        totaloutflowLFY= (int) clientresponse.getData().getSummary().get(4).getLfy().getValue();

        redemYTD= (int) clientresponse.getData().getSummary().get(6).getYtd();
        redemLFY= (int) clientresponse.getData().getSummary().get(6).getLfy().getValue();

        netflowYDT= (int) clientresponse.getData().getSummary().get(7).getYtd();
        netflowLFY= (int) clientresponse.getData().getSummary().get(7).getLfy().getValue();

     /*   netperYDT= (int) response.getData().getSummary().get(8).getYtd();
        netperLFY= (int) response.getData().getSummary().get(8).getLfy().getValue();*/

        netperYDT = (int) clientresponse.getData().getSummary().get(8).getYtd();
        String YDT = Integer.toString(netperYDT);

        String valueAsString = (String) clientresponse.getData().getSummary().get(8).getLfy().getValue();
        netperLFY = Integer.parseInt(valueAsString);


     /*   netperLFY= (int) response.getData().getSummary().get(8).getLfy().getValue();
        System.out.println(netperLFY);*/

 /*       t_inYTD= (int) response.getData().getSummary().get(12).getYtd();
        t_inLFY= (int) response.getData().getSummary().get(12).getLfy().getValue();*/

/*        t_outYTD= (int) response.getData().getSummary().get(13).getYtd();
        t_outLFY= (int) response.getData().getSummary().get(13).getLfy().getValue();*/


        System.out.println("aum YTD:"+aumYTD);
        System.out.println("aum LFY:"+aumLFY);
        System.out.println("inflow YTD:"+totalinflowYTD);
        System.out.println("inflow LFY:"+totalinflowLFY);
        System.out.println("sipYTD :"+sipYTD);
        System.out.println("sipLFY :"+sipLFY);
        System.out.println("lumYTD :"+lumYTD);
        System.out.println("lumLFY :" +lumLFY);

      /*  System.out.println("t_inYTD :"+t_inYTD);
        System.out.println("t_inLFY :"+t_inLFY);*/

        System.out.println("outflowYTD :"+totaloutflowYTD);
        System.out.println("outflowLFY :"+totaloutflowLFY);
        System.out.println("redemYTD :"+redemYTD);
        System.out.println("redemLFY :"+redemLFY);

        System.out.println("swpYTD :"+swpYTD);
        System.out.println("swpLFY :"+swpLFY);
        /*System.out.println("t_outYTD :"+t_outYTD);
        System.out.println("t_outLFY :"+t_outLFY);*/

        System.out.println("netflowYDT :"+netflowYDT);
        System.out.println("netflowLFY :"+netflowLFY);
        System.out.println("NetflowPercentage YDT : " + netperYDT);
        System.out.println("Netflow percentage LFY :"+netperLFY);

    }

}

