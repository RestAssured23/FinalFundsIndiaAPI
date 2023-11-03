package core.api.extendreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class ExtentManager {
    private static ExtentReports extent;
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter spark = new ExtentSparkReporter("Spark.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
    }

    @AfterSuite
    public void tearDown() {
        extentReports.flush();
    }
}
