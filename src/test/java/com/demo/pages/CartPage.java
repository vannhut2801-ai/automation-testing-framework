package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Cart page and Checkout pages.
 */
public class CartPage extends BasePage {

    // ===== Cart Locators =====
    private final By cartItem = By.className("cart_item");
    private final By cartItemName = By.className("inventory_item_name");
    private final By removeButton = By.cssSelector(".cart_button");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");

    // ===== Checkout Step 1 Locators =====
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // ===== Checkout Step 2 Locators =====
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");
    private final By summarySubtotal = By.className("summary_subtotal_label");
    private final By summaryTotal = By.className("summary_total_label");

    // ===== Checkout Complete Locators =====
    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    // ===== Error Locators =====
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ===== Cart Actions =====

    public void removeItem(int index) {
        var buttons = driver.findElements(removeButton);
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public HomePage continueShopping() {
        click(continueShoppingButton);
        return new HomePage(driver);
    }

    public void proceedToCheckout() {
        click(checkoutButton);
    }

    // ===== Checkout Step 1 Actions =====

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        sendKeys(firstNameInput, firstName);
        sendKeys(lastNameInput, lastName);
        sendKeys(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    // ===== Checkout Step 2 Actions =====

    public void clickFinish() {
        click(finishButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    // ===== Cart Verification =====

    public int getCartItemCount() {
        return getElementCount(cartItem);
    }

    public boolean isCartEmpty() {
        return !isElementPresent(cartItem);
    }

    public String getFirstItemName() {
        return getText(driver.findElements(cartItemName).get(0));
    }

    // ===== Checkout Verification =====

    public String getSummarySubtotal() {
        return getText(summarySubtotal);
    }

    public String getSummaryTotal() {
        return getText(summaryTotal);
    }

    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    public String getCompleteText() {
        return getText(completeText);
    }

    public HomePage backHome() {
        click(backHomeButton);
        return new HomePage(driver);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    // ===== Full Checkout Flow =====

    /**
     * Complete the full checkout flow from cart.
     */
    public void completeCheckout(String firstName, String lastName, String postalCode) {
        proceedToCheckout();
        fillCheckoutInfo(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
    }
}

