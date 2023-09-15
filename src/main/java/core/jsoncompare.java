package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.basepath.BasePlatform;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import core.basepath.AccessPropertyFile;
import org.testng.annotations.Test;

public class jsoncompare extends AccessPropertyFile {
    RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri(getBasePath())
            .addHeader("x-api-version", "2.0")
            .addHeader("channel-id", "10")
            .addHeader("x-fi-access-token", getAccessToken())
            .setContentType(ContentType.JSON).build().log().all();
    ResponseSpecification respec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
    String Holdingid;
    String InvestorId;

    public jsoncompare() throws IOException {
    }


    @Test
    public void compare() throws IOException {
        BasePlatform uri = new BasePlatform();
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(uri.setUp());
        properties.load(fis);
        String obj1 = "{\n" +
                "  \"code\": 0,\n" +
                "  \"desc\": \"string\",\n" +
                "  \"success\": true,\n" +
                "  \"errors\": [],\n" +
                "  \"type\": \"object\",\n" +
                "  \"data\": {\n" +
                "    \"accessToken\": \"string\",\n" +
                "    \"refreshToken\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"AuthenticationResponse\"\n" +
                "}";

        HashMap<String, String> login = new HashMap<>();
        login.put("emailId", properties.getProperty("email"));
        login.put("password", properties.getProperty("password"));
        login.put("grantType", "credentials");
        login.put("refreshToken", "string");

        //Login API

        RequestSpecification res = given().spec(req)
                .body(login);
        JsonNode Feature= (JsonNode) res.when().post("/core/auth/sign-in")
                .then().log().all().spec(respec);
        System.out.println(Feature);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper.readTree(obj1);
        JsonNode jsonNode2 = objectMapper.readTree(Feature.traverse());
        System.out.println(jsonNode1.equals(jsonNode2));
    }
}
