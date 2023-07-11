package org.example.data;

import io.restassured.RestAssured;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.Signin;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class Test_Login {
    public static String Email, password, grandtype, refreshtoken;
    public static double Switch_Units, Switch_Amt;
    public static String SWP_Folio, SWP_Amt, SWP_Date, SWP_Installment;
    public static String Switch_Folio;
    public static String HoldID, FolioID, BaseURL, Inv_Amount, SchemeSearch, Expected_Scheme, SIP_Type, Switch_TargetScheme;
    public static String Redeem_units = null, Redeem_amt = null, Redeem_Folio;
    public static String Alert_Startdate, Alert_Enddate;
    public static String STP_Freq, STP_TargetScheme;
    public static int SIP_Date;

    @Test
    public static String Regression_Test() throws IOException {

        String os = System.getProperty("os.name").toLowerCase();
        String path = null;

        if (os.contains("win")) {
            path = (System.getProperty("user.dir") + "\\src\\main\\testdata\\Login.xlsx");  //for Windows
        } else if (os.contains("mac")) {
            path = (System.getProperty("user.dir") + "/src/main/testdata/Login.xlsx");  //for Windows
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            // Unix-like platform
            // Set platform-specific configurations or perform specific actions
        } else {
            // Unknown platform
            // Handle or display an error message
        }

        try {
            FileInputStream fis = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet1 = wb.getSheetAt(0);
            BaseURL = sheet1.getRow(0).getCell(1).getStringCellValue();
            Email = sheet1.getRow(1).getCell(1).getStringCellValue();
            password = sheet1.getRow(2).getCell(1).getStringCellValue();
            grandtype = sheet1.getRow(3).getCell(1).getStringCellValue();
            refreshtoken = sheet1.getRow(4).getCell(1).getStringCellValue();

            //Holding and Folio Data
            HoldID = sheet1.getRow(6).getCell(1).getStringCellValue();

//Invest More Amount
            FolioID = sheet1.getRow(8).getCell(1).getStringCellValue();
            Inv_Amount = sheet1.getRow(9).getCell(1).getStringCellValue();
//Scheme_Search
            SchemeSearch = sheet1.getRow(10).getCell(1).getStringCellValue();
//Expected_Scheme
            Expected_Scheme = sheet1.getRow(11).getCell(1).getStringCellValue();
//SIP_Details
            SIP_Date = Integer.parseInt(sheet1.getRow(12).getCell(3).getStringCellValue());
            SIP_Type = sheet1.getRow(12).getCell(1).getStringCellValue();
            Alert_Startdate = sheet1.getRow(12).getCell(4).getStringCellValue();
            Alert_Enddate = sheet1.getRow(12).getCell(5).getStringCellValue();

//Redeemtion Data
            Redeem_units = sheet1.getRow(16).getCell(1).getStringCellValue();
            Redeem_amt = sheet1.getRow(17).getCell(1).getStringCellValue();
            Redeem_Folio = sheet1.getRow(18).getCell(1).getStringCellValue();
// Switch Data
            Switch_Units = Double.parseDouble(String.valueOf((int) sheet1.getRow(21).getCell(1).getNumericCellValue()));
            Switch_Amt = Double.parseDouble(String.valueOf((int) sheet1.getRow(22).getCell(1).getNumericCellValue()));

            Switch_Folio = sheet1.getRow(23).getCell(1).getStringCellValue();
            Switch_TargetScheme = sheet1.getRow(24).getCell(1).getStringCellValue();
//SWP Data
            SWP_Folio = sheet1.getRow(28).getCell(1).getStringCellValue();
            SWP_Amt = sheet1.getRow(29).getCell(1).getStringCellValue();
            SWP_Date = sheet1.getRow(30).getCell(1).getStringCellValue();
            SWP_Installment = sheet1.getRow(31).getCell(1).getStringCellValue();

//STP Data
            STP_Freq = sheet1.getRow(39).getCell(1).getStringCellValue();
            STP_TargetScheme = sheet1.getRow(40).getCell(1).getStringCellValue();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Login Payload
        HashMap<String, String> login = new HashMap<>();
        login.put("emailId", Email);
        login.put("password", password);
        login.put("grantType", grandtype);
        login.put("refreshToken", refreshtoken);
        //Login API
        RestAssured.baseURI = BaseURL;
        Signin.Root response = given().log().all()
                .header("x-api-version", "2.0")
                .header("channel-id", "10")
                .header("Content-Type", "application/json")

                .body(login).when().post("/core/auth/sign-in")
                .then().assertThat().statusCode(200)
                .header("Content-Type", "application/json")
                .extract().response().as(Signin.Root.class);
        return response.getData().getAccessToken();


    }

    @Test
    public static String URI() throws IOException {
        FileInputStream fis = new
                FileInputStream(System.getProperty("user.dir")+"\\src\\main\\testdata\\Login.xlsx");  //for Windows

        //  FileInputStream(System.getProperty("user.dir")+"/src/main/testdata/Login.xlsx");     // for MAC
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet1 = wb.getSheetAt(0);
        BaseURL = sheet1.getRow(0).getCell(1).getStringCellValue();
        return(BaseURL);
    }



}
