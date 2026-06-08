package com.demo.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.demo.base.BaseTest;
import com.demo.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for ExtentReport integration.
 * Captures test status and attaches screenshots on failure.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // Test is already created in BaseTest.setup()
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest baseTest && baseTest.test != null) {
            baseTest.test.log(Status.PASS, MarkupHelper.createLabel(
                    "Test Passed: " + result.getName(), ExtentColor.GREEN));
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest baseTest && baseTest.test != null) {
            ExtentTest test = baseTest.test;

            // Log the failure reason
            if (result.getThrowable() != null) {
                test.log(Status.FAIL, result.getThrowable());
            }

            test.log(Status.FAIL, MarkupHelper.createLabel(
                    "Test Failed: " + result.getName(), ExtentColor.RED));

            // Attach screenshot
            String screenshotPath = ScreenshotUtil.takeScreenshot(result.getName());
            if (!screenshotPath.isEmpty()) {
                test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest baseTest && baseTest.test != null) {
            if (result.getThrowable() != null) {
                baseTest.test.log(Status.SKIP, result.getThrowable());
            }
            baseTest.test.log(Status.SKIP, MarkupHelper.createLabel(
                    "Test Skipped: " + result.getName(), ExtentColor.ORANGE));
        }
    }

    @Override
    public void onStart(ITestContext context) {
        // Suite-level setup done in BaseTest
    }

    @Override
    public void onFinish(ITestContext context) {
        // Suite-level teardown done in BaseTest
    }
}

