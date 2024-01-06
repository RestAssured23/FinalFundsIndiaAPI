package reporttesting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class SetUp implements ITestListener {
    private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();

    @Override
    public void onStart(ITestContext iTestContext) {
        String filename=ExtentReportManager.getReportNameWithTimeStamp();
        String fullReportPath=System.getProperty("user.dir") +"\\demoreports\\" +filename;
        extentReports=ExtentReportManager.createInstance(fullReportPath,"Test API Automation Report","Test Excetution");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        if(extentReports !=null){
            extentReports.flush();
        }
    }
    @Override
    public void onTestStart(ITestResult iTestResult) {
          ExtentTest  test= extentReports.createTest("Test Name" +iTestResult.getTestClass().getName() +"-"+iTestResult.getMethod().getMethodName());
          extentTest.set(test);
    }



    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }



   /* @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentTest extentTest = extentReports.createTest("TestName: " +
                iTestResult.getTestClass().getName() + "-" + iTestResult.getMethod().getMethodName());
        extentTestThreadLocal.set(extentTest);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        String fileName = ExtentReportManager.getReportNameWithTime();
       String fullReportPath = "C:\\Users\\Fi-User\\fi-repositories\\fi-test-automation\\data\\Report\\" + fileName;
     //   String fullReportPath = System.getProperty("user.dir") + "\\reports\\" + fileName;

        // Ensure the "reports" directory exists, create it if not
        File reportsDir = new File(System.getProperty("user.dir") + "\\reports");
        if (!reportsDir.exists()) {
            boolean created = reportsDir.mkdir();
            if (created) {
                System.out.println("Directory 'reports' created successfully.");
            } else {
                System.out.println("Failed to create directory 'reports'.");
            }
        }

        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test Automation Report", "TestAPI");
    }
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        if (extentTestThreadLocal.get() != null) {
            ExtentReportManager.logPassDetails("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        if (extentTestThreadLocal.get() != null) {
            ExtentReportManager.logFailureDetails("Test Failed");
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        if (extentTestThreadLocal.get() != null) {
            ExtentReportManager.logWarningDetails("Test Skipped");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Implement if needed
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }*/
}