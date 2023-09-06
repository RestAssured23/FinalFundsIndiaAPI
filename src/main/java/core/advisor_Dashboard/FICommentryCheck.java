package core.advisor_Dashboard;

import core.advisor_Dashboard.model.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.Double.parseDouble;

public class FICommentryCheck extends AD_AccessPropertyFile{
    private final RequestSpecification req;
    private final ResponseSpecification respec;
    SoftAssert softAssert = new SoftAssert();
    public FICommentryCheck() {
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
    public void FIComments_Test() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Fi-User\\Desktop\\FICommentry.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet1 = wb.getSheetAt(0);

            int rowCount = sheet1.getPhysicalNumberOfRows();
            for(int i=5;i<6; i++) {
                Row row = sheet1.getRow(i);
                int value= (int) row.getCell(0).getNumericCellValue();
                Cell cell = row.getCell(6);
                String expectedcomment = String.valueOf(cell);
                System.out.println(value);

                if (cell != null) {
                    RequestSpecification res = given().spec(req)
                            .queryParam("schemeCode", value);
                    FiCommentsResponse.Root response = res.when().get("/core/portfolio-review/fi-comments")
                            .then().log().all().spec(respec).extract().response().as(FiCommentsResponse.Root.class);

                    String expectedNormalized = expectedcomment.replaceAll("\\s+", " ").trim();
                    String actualNormalized = response.getData().getComments().replaceAll("\\s+", " ").trim();
                    Assert.assertEquals(expectedNormalized, actualNormalized, String.valueOf(value));

                } else {
                    System.out.println("No comment found in the cell.");
                }
            }
            wb.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

