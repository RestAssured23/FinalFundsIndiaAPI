package reporttesting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class setUp implements ITestListener {
private static ExtentReports extentReports;
public static ThreadLocal<ExtentTest> extentTestThreadLocal=new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentTest extentTest=extentReports.createTest("TestName : " +iTestResult.getTestClass().getName()
        +"-" + iTestResult.getMethod().getMethodName());
        extentTestThreadLocal.set(extentTest);
    }
    @Override
    public void onStart(ITestContext iTestContext) {
        String fileName=ExtenReportManager.getReportNameWithTime();
        String fullReportPath=System.getProperty("user.dir"+"\\reports\\"+fileName);
        extentReports=ExtenReportManager.createInstance(fullReportPath,"Test Automation Report","TestAPI");
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


    @Override
    public void onFinish(ITestContext iTestContext) {
        if(extentReports!=null){
            extentReports.flush();
        }
    }
}
