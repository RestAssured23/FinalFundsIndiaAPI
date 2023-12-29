package core.advisor_Dashboard.auditReport;

import core.advisor_Dashboard.AD_AccessPropertyFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class auditReportPDF extends AD_AccessPropertyFile {
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();



    public auditReportPDF() {
        req = new RequestSpecBuilder()
                .setBaseUri(getADBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", "10")
              // .addHeader("x-fi-access-token", token)
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

    @Test   // (invocationCount = 10, threadPoolSize = 1)
    public void postScanSearch(){
        List<Map> searchPayload = new ArrayList<>();
            Map<String, Object> payload = new HashMap<>();
            payload.put("email", "");
            payload.put("pan", "");
            payload.put("mobile", "");
        searchPayload.add(payload);
        RequestSpecification res=given().spec(req)
                        .body(searchPayload);
        res.post("/tools/portfolios/scan/search")
                .then().log().all().spec(respec);
    }

    @Test(enabled = true)
    public void scanDownload() throws IOException {
        RequestSpecification res = given()
                .queryParam("uuid ", "string")
                 .spec(req);
           res.get("/tools/portfolios/scan/download")
                   .then().log().all().contentType("application/pdf");

       /* // Verify response status code if needed
        .then().log().all().statusCode(200);*/

     String directoryPath = "download/AuditReport/";
    File directory = new File(directoryPath);        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

    savePDFToFile(res.head().asByteArray(), directoryPath + "Report.pdf");     // Save the PDF content to a file
    }
    private void savePDFToFile(byte[] pdfContent, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfContent);
        }
    }
}






