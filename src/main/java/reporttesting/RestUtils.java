package reporttesting;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.util.Map;

public class RestUtils {



    private static RequestSpecification getRequestSpecification (String endpoint,Object requestPayload , Map<String,String> headers){
        return RestAssured.given()
                .baseUri(endpoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestPayload);
    }
    private static void printRequestLogInReport(RequestSpecification requestSpecification){
        QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Base URI :" +queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Get Method :" +queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Header :" +queryableRequestSpecification.getHeaders());
        ExtentReportManager.logInfoDetails("Request Body :" +queryableRequestSpecification.getBody());
    }

    private static void printResponseLogInReport(Response response){
        ExtentReportManager.logInfoDetails("Base URI :" +response.getStatusCode());
        ExtentReportManager.logInfoDetails("Base URI :" +response.getBody());
    }


    public static Response performPost(String endpoint,String requestPayload , Map<String,String> headers){
        RequestSpecification requestSpecification=getRequestSpecification(endpoint,requestPayload,headers);
        Response response= requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }
    public static Response performPost(String endpoint,Map<String,String> requestPayload , Map<String,String> headers){
        RequestSpecification requestSpecification=getRequestSpecification(endpoint,requestPayload,headers);
        Response response= requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performPostTest(String endpoint,Map<String,String> requestPayload, Map<String, String> headers){
      return RestAssured.given().log().all()
              .baseUri(endpoint)
              .headers(headers)
              .contentType(ContentType.JSON)
              .body(requestPayload)
              .post()
              .then().log().all().extract().response();
    }


}
