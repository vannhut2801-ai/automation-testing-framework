package com.demo.utils;

import com.demo.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots.
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "test-output/screenshots";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Take screenshot and return the file path.
     */
    public static String takeScreenshot(String testName) {
        WebDriver driver = BaseTest.getDriver();
        if (driver == null) {
            return "";
        }

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String fileName = testName + "_" + timestamp + ".png";

        File destDir = new File(SCREENSHOT_DIR);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, fileName);
        try {
            FileUtils.copyFile(srcFile, destFile);
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return "";
        }
    }
}

