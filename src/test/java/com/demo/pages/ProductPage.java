package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Product Detail page.
 */
public class ProductPage extends BasePage {

    // ===== Locators =====
    private final By backToProductsButton = By.id("back-to-products");
    private final By productName = By.className("inventory_details_name");
    private final By productDescription = By.className("inventory_details_desc");
    private final By productPrice = By.className("inventory_details_price");
    private final By productImage = By.className("inventory_details_img");
    private final By addToCartButton = By.cssSelector(".btn_inventory");
    private final By removeButton = By.cssSelector(".btn_secondary");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // ===== Actions =====

    public HomePage backToProducts() {
        click(backToProductsButton);
        return new HomePage(driver);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void removeFromCart() {
        if (isElementDisplayed(removeButton)) {
            click(removeButton);
        }
    }

    // ===== Verification =====

    public String getProductName() {
        return getText(productName);
    }

    public String getProductDescription() {
        return getText(productDescription);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public boolean isProductImageDisplayed() {
        return isElementDisplayed(productImage);
    }

    public boolean isAddToCartButtonDisplayed() {
        return isElementDisplayed(addToCartButton);
    }

    public boolean isRemoveButtonDisplayed() {
        return isElementDisplayed(removeButton);
    }
}

