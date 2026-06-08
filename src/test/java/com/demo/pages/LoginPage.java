package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Login page.
 */
public class LoginPage extends BasePage {

    // ===== Locators =====
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By errorCloseButton = By.cssSelector(".error-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ===== Actions =====

    public void enterUsername(String username) {
        sendKeys(usernameInput, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Perform login with given credentials.
     * @return HomePage if login successful
     */
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new HomePage(driver);
    }

    /**
     * Perform login and expect to stay on login page (negative test).
     */
    public void loginExpectingError(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ===== Verification =====

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public void dismissError() {
        if (isElementDisplayed(errorCloseButton)) {
            click(errorCloseButton);
        }
    }
}

