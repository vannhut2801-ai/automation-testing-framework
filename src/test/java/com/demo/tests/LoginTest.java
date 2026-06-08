package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.data.TestDataProvider;
import com.demo.pages.HomePage;
import com.demo.pages.LoginPage;
import com.demo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Login functionality.
 * TC01 – TC04
 */
public class LoginTest extends BaseTest {

    // ===== TC01: Login với tài khoản hợp lệ (Data-Driven) =====

    @Test(priority = 1,
          description = "TC01: Login với nhiều loại tài khoản hợp lệ",
          dataProvider = "validLoginData",
          dataProviderClass = TestDataProvider.class)
    public void testLoginWithValidCredentials(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.login(username, password);

        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Home page should be displayed after successful login");
        Assert.assertEquals(homePage.getAppLogoText(), "Swag Labs",
                "App logo text should be 'Swag Labs'");
    }

    // ===== TC02: Login với tài khoản bị khóa =====

    @Test(priority = 2,
          description = "TC02: Login với tài khoản locked_out_user",
          dataProvider = "lockedUserData",
          dataProviderClass = TestDataProvider.class)
    public void testLoginWithLockedUser(String username, String password,
                                         String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingError(username, password);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked user");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Error message should contain: " + expectedError);
    }

    // ===== TC03: Login với sai password (Data-Driven) =====

    @Test(priority = 3,
          description = "TC03: Login với thông tin không hợp lệ",
          dataProvider = "invalidLoginData",
          dataProviderClass = TestDataProvider.class)
    public void testLoginWithInvalidCredentials(String username, String password,
                                                 String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingError(username, password);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid credentials");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Expected: " + expectedError + " | Actual: " + loginPage.getErrorMessage());
    }

    // ===== TC04: Login để trống cả 2 trường =====

    @Test(priority = 4,
          description = "TC04: Login với username và password trống")
    public void testLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingError("", "");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed when fields are empty");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username is required"),
                "Error should mention username is required");
    }

    // ===== TC bổ sung: Login rồi logout =====

    @Test(priority = 5,
          description = "TC: Login thành công sau đó logout")
    public void testLoginAndLogout() {
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("standard.user", "standard_user");
        String password = ConfigReader.getProperty("standard.password", "secret_sauce");

        HomePage homePage = loginPage.login(username, password);
        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Should be on home page after login");

        homePage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should be on login page after logout");
    }
}

