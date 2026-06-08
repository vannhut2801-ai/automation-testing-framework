package com.demo.data;

import com.demo.utils.ConfigReader;
import org.testng.annotations.DataProvider;

/**
 * Data providers for Data-Driven Testing.
 */
public class TestDataProvider {

    // ===== Login Data =====

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return new Object[][]{
                {ConfigReader.getProperty("standard.user", "standard_user"),
                 ConfigReader.getProperty("standard.password", "secret_sauce")},
                {ConfigReader.getProperty("problem.user", "problem_user"),
                 ConfigReader.getProperty("standard.password", "secret_sauce")},
                {ConfigReader.getProperty("performance.user", "performance_glitch_user"),
                 ConfigReader.getProperty("standard.password", "secret_sauce")},
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"", "", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"invalid_user", "secret_sauce",
                 "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "wrong_password",
                 "Epic sadface: Username and password do not match any user in this service"},
        };
    }

    @DataProvider(name = "lockedUserData")
    public Object[][] lockedUserData() {
        return new Object[][]{
                {ConfigReader.getProperty("locked.user", "locked_out_user"),
                 ConfigReader.getProperty("locked.password", "secret_sauce"),
                 "Epic sadface: Sorry, this user has been locked out."},
        };
    }

    // ===== Sort Options =====

    @DataProvider(name = "sortOptions")
    public Object[][] sortOptions() {
        return new Object[][]{
                {"Name (A to Z)"},
                {"Name (Z to A)"},
                {"Price (low to high)"},
                {"Price (high to low)"},
        };
    }

    // ===== Checkout Data =====

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return new Object[][]{
                {"Nguyen", "Van A", "700000"},
                {"Tran", "Thi B", "100000"},
                {"Le", "Van C", "550000"},
        };
    }
}

