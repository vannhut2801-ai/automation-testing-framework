package com.demo.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.demo.utils.ConfigReader;
import com.demo.utils.ExtentReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Base class for all test classes.
 * Manages WebDriver lifecycle, ExtentReport, and test configuration.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    public ExtentTest test;

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        extent = ExtentReportManager.getInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        String browser = System.getProperty("browser",
                ConfigReader.getProperty("browser", "chrome"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless",
                ConfigReader.getProperty("headless", "false")));

        driver = createDriver(browser, headless);
        driverThreadLocal.set(driver);

        int implicitWait = Integer.parseInt(
                ConfigReader.getProperty("implicit.wait", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("base.url",
                "https://www.saucedemo.com"));

        test = extent.createTest(method.getName());
    }

    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}

