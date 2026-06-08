package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.data.TestDataProvider;
import com.demo.pages.HomePage;
import com.demo.pages.LoginPage;
import com.demo.pages.ProductPage;
import com.demo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class for Product functionality.
 * TC05 – TC08
 */
public class ProductTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void loginFirst() {
        String username = ConfigReader.getProperty("standard.user", "standard_user");
        String password = ConfigReader.getProperty("standard.password", "secret_sauce");
        LoginPage loginPage = new LoginPage(driver);
        homePage = loginPage.login(username, password);
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Login failed, cannot continue.");
    }

    // ===== TC05: Sắp xếp sản phẩm =====

    @Test(priority = 1,
          description = "TC05: Sắp xếp sản phẩm theo các tiêu chí khác nhau",
          dataProvider = "sortOptions",
          dataProviderClass = TestDataProvider.class)
    public void testSortProducts(String sortOption) {
        homePage.sortProducts(sortOption);

        switch (sortOption) {
            case "Price (low to high)" -> {
                List<Double> prices = homePage.getAllProductPrices();
                for (int i = 0; i < prices.size() - 1; i++) {
                    Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                            "Prices should be sorted low to high. Failed at index " + i);
                }
            }
            case "Price (high to low)" -> {
                List<Double> prices = homePage.getAllProductPrices();
                for (int i = 0; i < prices.size() - 1; i++) {
                    Assert.assertTrue(prices.get(i) >= prices.get(i + 1),
                            "Prices should be sorted high to low. Failed at index " + i);
                }
            }
            case "Name (A to Z)" -> {
                List<String> names = homePage.getAllProductNames();
                for (int i = 0; i < names.size() - 1; i++) {
                    Assert.assertTrue(
                            names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0,
                            "Names should be sorted A-Z. Failed at index " + i);
                }
            }
            case "Name (Z to A)" -> {
                List<String> names = homePage.getAllProductNames();
                for (int i = 0; i < names.size() - 1; i++) {
                    Assert.assertTrue(
                            names.get(i).compareToIgnoreCase(names.get(i + 1)) >= 0,
                            "Names should be sorted Z-A. Failed at index " + i);
                }
            }
        }
    }

    // ===== TC06: Xem chi tiết sản phẩm đầu tiên =====

    @Test(priority = 2,
          description = "TC06: Xem chi tiết sản phẩm đầu tiên trong danh sách")
    public void testViewProductDetail() {
        String expectedName = homePage.getFirstProductName();
        ProductPage productPage = homePage.clickProduct(0);

        Assert.assertEquals(productPage.getProductName(), expectedName,
                "Product name should match the one clicked");
        Assert.assertFalse(productPage.getProductDescription().isEmpty(),
                "Product description should not be empty");
        Assert.assertFalse(productPage.getProductPrice().isEmpty(),
                "Product price should not be empty");
        Assert.assertTrue(productPage.isProductImageDisplayed(),
                "Product image should be displayed");
        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(),
                "Add to cart button should be displayed");
    }

    // ===== TC07: Quay lại danh sách sản phẩm =====

    @Test(priority = 3,
          description = "TC07: Click Back to Products từ trang chi tiết")
    public void testBackToProducts() {
        homePage.clickProduct(0);
        ProductPage productPage = new ProductPage(driver);
        HomePage returnedPage = productPage.backToProducts();

        Assert.assertTrue(returnedPage.isHomePageDisplayed(),
                "Should return to product list page");
        Assert.assertTrue(returnedPage.getProductCount() > 0,
                "Product list should be displayed");
    }

    // ===== TC08: Thêm sản phẩm từ trang chi tiết =====

    @Test(priority = 4,
          description = "TC08: Thêm sản phẩm vào giỏ từ trang chi tiết")
    public void testAddToCartFromDetailPage() {
        homePage.clickProduct(0);
        ProductPage productPage = new ProductPage(driver);

        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(),
                "Add to cart button should be visible");

        productPage.addToCart();

        Assert.assertTrue(productPage.isRemoveButtonDisplayed(),
                "Remove button should appear after adding to cart");
    }
}

