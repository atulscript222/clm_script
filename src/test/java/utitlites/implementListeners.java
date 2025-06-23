package utitlites;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.base;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class implementListeners implements ITestListener {

    ExtentReports extent;
    ExtentTest test;
    Map<String, ExtentTest> testMap = new HashMap<>();

    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("🟢 Test Suite Started: " + context.getName());

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "C:/Users/admin/eclipse-workspace/123/reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Functional Test Suite");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Atul Prajapathi");
        extent.setSystemInfo("Environment", "Beta");

        // ✅ Set dynamic system info from XML parameters
        String os = context.getCurrentXmlTest().getParameter("os");
        String browser = context.getCurrentXmlTest().getParameter("browser");

        extent.setSystemInfo("Operating System", os);
        extent.setSystemInfo("Browser", browser);
        
     // Inside your onStart method:
        List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();

        if (!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());
    }
    }


    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🔵 Test Started: " + result.getName());
        test = extent.createTest(result.getMethod().getMethodName());
        testMap.put(result.getMethod().getMethodName(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // Assign groups
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new base().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }


    @Override
    public void onFinish(ITestContext testContext) {
        System.out.println("🔴 Test Suite Finished: " + testContext.getName());
        extent.flush();

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String repName = "ExtentReport_" + timestamp + ".html";
        
        String pathOfExtentReport = "C:\\Users\\admin\\eclipse-workspace\\123\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);

            // Create the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("youremail@gmail.com", "yourAppPassword")); // Use App Password
            email.setSSLOnConnect(true);

            email.setFrom("youremail@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find the attached automation test report...");

            email.addTo("receiver@example.com"); // Receiver
            email.attach(url, "Extent Report", "Please check the automation report...");

            email.send(); // Send the email
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }

}
