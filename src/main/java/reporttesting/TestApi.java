/*
package reporttesting;

import com.aventstack.extentreports.ExtentTest;
import core.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestApi {
    @BeforeSuite
    public void setUp() {
        // Initialize Extent Reports
        String reportName = "TestReport";
        String docTitle = "API Test Reports";
        String fileName = ExtentReportManager.getReportNameWithTime();
        ExtentReportManager.createInstance(fileName, reportName, docTitle);
    }

    @Test
    public void signinAPI() {
        try {
            String testName = "TestName: signinAPI";
            ExtentReportManager.initializeExtentTest(testName);
            ExtentReportManager.logInfoDetails("Test started: signinAPI");

            HashMap<String, String> payload = new HashMap<>();
            payload.put("emailId", "regression@gmail.com");
            payload.put("password", "asdfasdf123");
            payload.put("grantType", "credentials");
            payload.put("refreshToken", "string");

            RestAssured.baseURI = "https://scrum-api.fundsindia.com";
            Signin.Root response = given().log().all()
                    .header("x-api-version", "1.0")
                    .header("channel-id", 11)
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .when().post("/core/auth/sign-in")
                    .then().log().all()
                    .assertThat().statusCode(200)
                    .header("Content-Type", "application/json")
                    .extract().response()
                    .as(Signin.Root.class);

            ExtentReportManager.logPassDetails("API request successful");
            System.out.println(response.getData().getAccessToken());
        } catch (Exception e) {
            ExtentReportManager.logFailureDetails("API request failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        // Perform any cleanup or finalization steps if needed
        ExtentReportManager.logInfoDetails("Test completed: signinAPI");

    }
}


*/
