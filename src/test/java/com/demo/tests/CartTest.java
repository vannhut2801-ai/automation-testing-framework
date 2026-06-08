package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.data.TestDataProvider;
import com.demo.pages.CartPage;
import com.demo.pages.HomePage;
import com.demo.pages.LoginPage;
import com.demo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Cart functionality.
 * TC09 – TC12
 */
public class CartTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void loginFirst() {
        String username = ConfigReader.getProperty("standard.user", "standard_user");
        String password = ConfigReader.getProperty("standard.password", "secret_sauce");
        LoginPage loginPage = new LoginPage(driver);
        homePage = loginPage.login(username, password);
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Login failed, cannot continue.");
    }

    // ===== TC09: Thêm 1 sản phẩm vào giỏ hàng =====

    @Test(priority = 1,
          description = "TC09: Thêm 1 sản phẩm vào giỏ hàng và kiểm tra badge")
    public void testAddSingleProductToCart() {
        Assert.assertEquals(homePage.getCartBadgeCount(), "0",
                "Cart badge should not be visible when cart is empty");

        homePage.addProductToCart(0);

        Assert.assertTrue(homePage.isCartBadgeDisplayed(),
                "Cart badge should appear after adding a product");
        Assert.assertEquals(homePage.getCartBadgeCount(), "1",
                "Cart badge should show '1'");
    }

    // ===== TC10: Thêm nhiều sản phẩm (3 sản phẩm) =====

    @Test(priority = 2,
          description = "TC10: Thêm 3 sản phẩm vào giỏ hàng")
    public void testAddMultipleProductsToCart() {
        // Add 3 sản phẩm đầu tiên
        homePage.addProductToCart(0);
        homePage.addProductToCart(1);
        homePage.addProductToCart(2);

        Assert.assertEquals(homePage.getCartBadgeCount(), "3",
                "Cart badge should show '3' after adding 3 products");
    }

    // ===== TC11: Xóa sản phẩm khỏi giỏ hàng =====

    @Test(priority = 3,
          description = "TC11: Xóa sản phẩm khỏi giỏ hàng")
    public void testRemoveProductFromCart() {
        // Add 1 sản phẩm trước
        homePage.addProductToCart(0);
        CartPage cartPage = homePage.goToCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item");

        cartPage.removeItem(0);

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only item");
    }

    // ===== TC12: Checkout flow hoàn chỉnh (Data-Driven) =====

    @Test(priority = 4,
          description = "TC12: Hoàn tất checkout flow",
          dataProvider = "checkoutData",
          dataProviderClass = TestDataProvider.class)
    public void testCompleteCheckoutFlow(String firstName, String lastName,
                                          String postalCode) {
        // Add sản phẩm
        homePage.addProductToCart(0);
        CartPage cartPage = homePage.goToCart();

        // Kiểm tra có sản phẩm trong giỏ
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item before checkout");

        // Thực hiện checkout
        cartPage.completeCheckout(firstName, lastName, postalCode);

        // Kiểm tra kết quả
        Assert.assertTrue(
                cartPage.getCompleteHeader().contains("Thank you"),
                "Complete header should say thank you");
        Assert.assertTrue(
                cartPage.getCompleteText().contains("Your order has been dispatched"),
                "Complete text should confirm order dispatch");

        // Quay về home
        HomePage returnedHome = cartPage.backHome();
        Assert.assertTrue(returnedHome.isHomePageDisplayed(),
                "Should return to home page after checkout");
    }

    // ===== TC bổ sung: Checkout thiếu thông tin =====

    @Test(priority = 5,
          description = "TC: Checkout khi thiếu thông tin bắt buộc")
    public void testCheckoutWithMissingInfo() {
        homePage.addProductToCart(0);
        CartPage cartPage = homePage.goToCart();

        // Để trống tất cả các trường
        cartPage.proceedToCheckout();
        cartPage.fillCheckoutInfo("", "", "");
        cartPage.clickContinue();

        Assert.assertTrue(cartPage.isErrorMessageDisplayed(),
                "Error should be displayed when checkout info is missing");
        Assert.assertTrue(
                cartPage.getErrorMessage().contains("First Name is required"),
                "Error should mention First Name is required");
    }
}

