package com.demo.pages;

import com.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for SauceDemo Home / Inventory page.
 */
public class HomePage extends BasePage {

    // ===== Locators =====
    private final By appLogo = By.className("app_logo");
    private final By productSortDropdown = By.className("product_sort_container");
    private final By productList = By.className("inventory_item");
    private final By productName = By.className("inventory_item_name");
    private final By productPrice = By.className("inventory_item_price");
    private final By addToCartButton = By.cssSelector(".btn_inventory");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ===== Actions =====

    public void sortProducts(String sortOption) {
        selectByVisibleText(productSortDropdown, sortOption);
    }

    public ProductPage clickProduct(int index) {
        List<WebElement> products = driver.findElements(productName);
        if (index >= 0 && index < products.size()) {
            products.get(index).click();
        }
        return new ProductPage(driver);
    }

    public ProductPage clickProductByName(String name) {
        List<WebElement> products = driver.findElements(productName);
        for (WebElement product : products) {
            if (product.getText().equalsIgnoreCase(name)) {
                product.click();
                return new ProductPage(driver);
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }

    public void addProductToCart(int index) {
        List<WebElement> buttons = driver.findElements(addToCartButton);
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void addMultipleProductsToCart(int... indices) {
        for (int index : indices) {
            addProductToCart(index);
        }
    }

    public CartPage goToCart() {
        click(cartIcon);
        return new CartPage(driver);
    }

    public void logout() {
        click(menuButton);
        waitForElementClickable(logoutLink);
        click(logoutLink);
    }

    // ===== Verification =====

    public boolean isHomePageDisplayed() {
        return isElementDisplayed(appLogo);
    }

    public String getAppLogoText() {
        return getText(appLogo);
    }

    public int getProductCount() {
        return getElementCount(productList);
    }

    public String getFirstProductName() {
        return getText(driver.findElements(productName).get(0));
    }

    public String getFirstProductPrice() {
        return getText(driver.findElements(productPrice).get(0));
    }

    public String getLastProductName() {
        List<WebElement> names = driver.findElements(productName);
        return getText(names.get(names.size() - 1));
    }

    public String getLastProductPrice() {
        List<WebElement> prices = driver.findElements(productPrice);
        return getText(prices.get(prices.size() - 1));
    }

    public String getCartBadgeCount() {
        if (isElementDisplayed(cartBadge)) {
            return getText(cartBadge);
        }
        return "0";
    }

    public boolean isCartBadgeDisplayed() {
        return isElementDisplayed(cartBadge);
    }

    public List<String> getAllProductNames() {
        return driver.findElements(productName)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<Double> getAllProductPrices() {
        return driver.findElements(productPrice)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }
}

