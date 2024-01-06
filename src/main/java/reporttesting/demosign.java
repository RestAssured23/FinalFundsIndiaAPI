package reporttesting;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class demosign {

    String baseuri="https://scrum-api.fundsindia.com";
    String end="/core/auth/sign-in";
    @Test
    public void createpayload() {
        String endpoint=baseuri+end;
        System.out.println(endpoint);
        Map<String, String> payload = demoPayloads.getLoginPayload("regression@gmail.com", "asdfasdf123", "credentials", "string");
        Map<String, String> headers = demoPayloads.getHeaders("1.0","11");


    Response response=RestUtils.performPostTest(endpoint,payload,headers);
        Assert.assertEquals(response.statusCode(),200);

    }
}
