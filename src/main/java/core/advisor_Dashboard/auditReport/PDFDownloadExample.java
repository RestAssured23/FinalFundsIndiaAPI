package core.advisor_Dashboard.auditReport;

import core.advisor_Dashboard.AD_AccessPropertyFile;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class PDFDownloadExample extends AD_AccessPropertyFile {
   static String userId="764594";   //112071

    public static void main(String[] args) {
        try {
            downloadAndSavePDF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void downloadAndSavePDF() throws IOException {
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri(getADBasePath())
                .addHeader("x-api-version", "1.0")
                .addHeader("channel-id", "10")
                // .addHeader("x-fi-access-token", token)
                .addHeader("x-fi-access-token", getAdminAccessToken())
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();


        Response response = given()
                .queryParam("uuid", userId)
                .queryParam("isInvestmentStyle", true)
                .spec(req)
                .get("/tools/portfolios/scan/download")
                .then().log().all()
                .statusCode(200)
                .contentType("application/pdf")
                .extract().response();

        byte[] pdfContent = response.getBody().asByteArray();
        System.out.println("Received PDF content length: " + pdfContent.length);

        String directoryPath = "download/AuditReport/";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String filePath = directoryPath + "Report_" + userId +"_"+ timestamp + ".pdf";
        savePDFToFile(pdfContent, filePath);

        // Verify PDF content using PDFBox
        verifyPDFContent(filePath);
    }

    private static void savePDFToFile(byte[] pdfContent, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfContent);
            System.out.println("PDF saved to: " + filePath);
        }
    }

    private static void verifyPDFContent(String filePath) {
        File pdfFile = new File(filePath);

        if (!pdfFile.exists() || pdfFile.length() == 0) {
            System.err.println("Error: The PDF file is either missing or empty.");
            return;
        }
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println("PDF Content: " +text);
        } catch (IOException e) {
            System.err.println("Error loading PDF file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
