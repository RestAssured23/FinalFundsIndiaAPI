package reporttesting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.dnd.DropTarget;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ExtentReportManager {
        private static ExtentReports extentReports;

        public static ExtentReports createInstance(String fileName, String reportName, String documentTitle) {
                ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
                extentSparkReporter.config().setReportName(reportName);
                extentSparkReporter.config().setDocumentTitle(documentTitle);
                extentSparkReporter.config().setTheme(Theme.DARK);
                extentSparkReporter.config().setEncoding("utf-8");
                extentReports = new ExtentReports();
                extentReports.attachReporter(extentSparkReporter);
                return extentReports;
        }
        public static String getReportNameWithTimeStamp(){
                DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime localDateTime=LocalDateTime.now();
                String formattedTime =dateTimeFormatter.format(localDateTime);
                String reportName="TestReport" +formattedTime+".html";
                return reportName;
        }

        public static void logPassDetails(String log) {
                SetUp.extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.GREEN));
        }
        public static void logFailDetails(String log) {
                SetUp.extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.RED));
        }
        public static void logInfoDetails(String log) {
                SetUp.extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.GREY));
        }
        public static void logWarningDetails(String log) {
                SetUp.extentTest.get().pass(MarkupHelper.createLabel(log,ExtentColor.YELLOW));
        }
}