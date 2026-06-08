package com.demo.base;

import com.demo.utils.ConfigReader;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Objects.
 * Provides common Selenium operations and wait utilities.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(
                ConfigReader.getProperty("explicit.wait", "15"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        PageFactory.initElements(driver, this);
    }

    // ===== Wait Utilities =====

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== Click Actions =====

    protected void click(By locator) {
        waitForElementClickable(locator).click();
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // ===== Input Actions =====

    protected void sendKeys(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    // ===== Get Text =====

    protected String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    // ===== Select Dropdown =====

    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByValue(value);
    }

    // ===== Page Navigation =====

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    // ===== Utility =====

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }
}

