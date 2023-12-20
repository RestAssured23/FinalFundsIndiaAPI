package reporttesting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtenReportManager {

        public static ExtentReports extentReports;

        public static ExtentReports createInstance(String fileName,String reportName,String docTitle){
                ExtentSparkReporter extentSparkReporter=new ExtentSparkReporter(fileName);
                extentSparkReporter.config().setReportName(reportName);
                extentSparkReporter.config().setDocumentTitle(docTitle);
                extentReports=new ExtentReports();
                extentReports.attachReporter(extentSparkReporter);
                return extentReports;
        }
        public static String getReportName(){
                DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss");
                LocalDateTime localDateTime=LocalDateTime.now();
                String formattedtime=dateTimeFormatter.format(localDateTime);
                String reportName="TestReport" + formattedtime+ ".html";
                return reportName;
        }
        public static void logPassDetails(){

        }
}
