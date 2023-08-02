package core.basepath;

import core.model.Signin;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AccessPropertyFile {

    public static double  switch_unitpro,switch_amtpro,inv_amount;
    public static String holdingid_pro, folio_pro, units_pro, amount_pro,
            targetscheme_pro,switch_target, amcschemeSearch,expectedscheme,alertStartdate,alertEnddate;
    public static String dbusr, dbpwd, dburl, databasename, encrypt, dbdrivername;
    public static int sipDate;
    static Properties properties = new Properties();

    @Test
    public String getBasePath() {
        BasePlatform uri = new BasePlatform();
        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            return properties.getProperty("baseURI");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public String getAccessToken() {
        BasePlatform uri = new BasePlatform();
        holdingid_pro = properties.getProperty("holdingid");
        folio_pro = properties.getProperty("folio");
        units_pro = properties.getProperty("units");
        amount_pro = properties.getProperty("amount");
        targetscheme_pro = properties.getProperty("targetscheme");
        switch_unitpro = Double.parseDouble(properties.getProperty("switchunits"));
        switch_amtpro = Double.parseDouble(properties.getProperty("switchamount"));
        switch_target = properties.getProperty("targetscheme");



   //SIP Details and Invest more
        sipDate= Integer.parseInt(properties.getProperty("sipdate"));
        alertStartdate=properties.getProperty("startdate");
        alertEnddate=properties.getProperty("enddate");
        inv_amount= Double.parseDouble(properties.getProperty("investamount"));
        amcschemeSearch =properties.getProperty("SchemeSearch");
        expectedscheme=properties.getProperty("ExpectedScheme");

 //DB Details
        dbusr = properties.getProperty("dbuserName");
        dbpwd = properties.getProperty("dbpassword");
        dburl = properties.getProperty("url");
        databasename = properties.getProperty("databasename");
        dbdrivername = properties.getProperty("drivername");

        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            if (properties.getProperty("accesstoken").isEmpty()) {
                //Login Payload
                HashMap<String, String> login = new HashMap<>();
                login.put("emailId", properties.getProperty("email"));
                login.put("password", properties.getProperty("password"));
                login.put("grantType", "credentials");
                login.put("refreshToken", "string");
                //Login API
                RestAssured.baseURI = properties.getProperty("baseURI");
                Signin.Root response = given().log().all()
                        .header("x-api-version", "2.0")
                        .header("channel-id", "10")
                        .header("Content-Type", "application/json")
                        .body(login)
                        .when()
                        .post("/core/auth/sign-in")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .header("Content-Type", "application/json")
                        .extract()
                        .response()
                        .as(Signin.Root.class);
                return response.getData().getAccessToken();
            } else {
                return properties.getProperty("accesstoken");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public String getAdminAccessToken() {
        BasePlatform uri = new BasePlatform();
        try {
            FileInputStream fis = new FileInputStream(uri.platform());
            properties.load(fis);
            //Login Payload
            HashMap<String, String> login = new HashMap<>();
            login.put("emailId", properties.getProperty("ad_email"));
            login.put("password", properties.getProperty("ad_password"));
            login.put("grantType", "credentials");
            login.put("refreshToken", "string");
            //Login API
            RestAssured.baseURI = properties.getProperty("baseURI");
            Signin.Root response = given().log().all()
                    .header("x-api-version", "2.0")
                    .header("channel-id", "10")
                    .header("Content-Type", "application/json")
                    .body(login)
                    .when()
                    .post("/core/auth/sign-in")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .header("Content-Type", "application/json")
                    .extract()
                    .response()
                    .as(Signin.Root.class);
            return response.getData().getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}