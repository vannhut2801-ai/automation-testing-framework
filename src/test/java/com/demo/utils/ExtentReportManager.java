package com.demo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton manager for ExtentReport.
 */
public class ExtentReportManager {

    private static ExtentReports extent;

    private ExtentReportManager() {}

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportPath = "test-output/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName(
                    ConfigReader.getProperty("report.name", "Automation Report"));
            sparkReporter.config().setDocumentTitle(
                    ConfigReader.getProperty("report.title", "Test Execution Report"));
            sparkReporter.config().setTimelineEnabled(true);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System info
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("Browser",
                    ConfigReader.getProperty("browser", "chrome"));
            extent.setSystemInfo("Base URL",
                    ConfigReader.getProperty("base.url", "https://www.saucedemo.com"));
        }
        return extent;
    }
}

