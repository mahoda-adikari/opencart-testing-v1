package com.opencart.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.opencart.testbase.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentReportManager.getInstance();
    private static final ThreadLocal<ExtentTest> tTest =  new ThreadLocal<>();
    private static final ThreadLocal<Logger> tLogger = new ThreadLocal<>();

    public void setTest(ExtentTest test) {
        tTest.set(test);
    }

    public ExtentTest getTest() {
        return tTest.get();
    }

    public void setLogger(Logger logger) {
        tLogger.set(logger);
    }

    public Logger getLogger() {
        return tLogger.get();
    }

    @Override
    public void onStart(ITestContext context) {
        Logger logger = LogManager.getLogger(this.getClass());
        setLogger(logger);
        getLogger().info("Starting Test Suite :" + context.getName());

        String os = context.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System @param", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser @param", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Included Groups", includedGroups.toString());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            getLogger().info("Finishing Test Suite: " + context.getName());
            ExtentReportManager.flushReport(extent);
        } finally {
            tTest.remove();
            tLogger.remove();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        setTest(test);
        getLogger().info("Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            getTest().log(Status.PASS, "Test Passed");
            getLogger().info("Test Passed: " + result.getMethod().getMethodName());
        } finally {
            tTest.remove();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure Thread: " + Thread.currentThread().getName());
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                WebDriver driver = ((BaseTest) testInstance).getDriver();
                String screenshotPath = ScreenshotUtility.captureScreen(driver, result.getMethod().getMethodName());
                getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
                try {
                    getTest().addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    getLogger().error("Failed to add screenshot to report: " + e.getMessage(), e);
                }
                getLogger().error("Test Failed: " + result.getMethod().getMethodName(), result.getThrowable());
            }
        } finally {
            tTest.remove();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            getTest().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
            getLogger().warn("Test Skipped: " + result.getMethod().getMethodName(), result.getThrowable());
        } finally {
            tTest.remove();
        }
    }

}
