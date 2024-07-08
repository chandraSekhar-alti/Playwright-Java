package UI.tests;

import UI.pages.CartPage;
import UI.pages.HomePage;
import com.github.javafaker.Faker;
import com.microsoft.playwright.Page;
import UI.Utils.BaseTest;
import UI.Utils.UI;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Cart Functionality")
public class CartPageTest extends BaseTest {
    private CartPage cartPage;
    private HomePage homePage;
    private UI ui;

    @Test(description = "This test verifies the complete flow of ordering an item.")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.CRITICAL)
    @Story("As a user, I should be able to add items to the cart, proceed to checkout, and place an order successfully.")
    @Link(name = "Cart Page", url = "https://dev.example.com/cart")
    public void testCompleteOrderFlow(){
        Page page = getPage();
        cartPage = new CartPage(page);
        Faker faker = new Faker();
        String[] data = {faker.name().firstName(),faker.name().lastName(), faker.address().zipCode()};

        boolean valueToReturn = cartPage.checkAddedToCartButtonsVisibility();
        Assert.assertTrue(valueToReturn,"Add to Cart buttons are not visible");

        cartPage.addingProductToCart();
        cartPage.clickOnCheckOutButton();
        cartPage.enteringDetails(data[0],data[1],data[2]);
        cartPage.clickOnContinueButton();
        cartPage.clickOnFinishButton();
        page.locator(cartPage.orderSuccessMessage).isVisible();
        String actualText = page.locator(cartPage.orderSuccessMessage).innerText();
        Assert.assertEquals(actualText,"THANK YOU FOR YOUR ORDER");
    }

    @Test(description = "This test verifies the visibility of various elements on the cart page and home page.")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.NORMAL)
    @Story("As a user, I should be able to see and interact with essential elements on the cart page and home page.")
    @Link(name = "Cart Page", url = "https://dev.example.com/cart")
    public void testCartPageVisibilityChecks(){
        Page page = getPage();
        cartPage = new CartPage(page);
        homePage = new HomePage(page);
        ui = new UI(page);

        page.locator(homePage.cartButton).isVisible();
        ui.highlightElementByGreen(homePage.cartButton);
        page.locator(homePage.cartButton).click();
        page.locator(cartPage.checkOutButton).isVisible();
        homePage.checkLinkedInIconVisibility();
        homePage.checkFacebookIconVisibility();
        homePage.checkTwitterIconVisibility();
        page.locator(cartPage.continueShoppingButton).isVisible();
        ui.highlightElementByGreen(cartPage.continueShoppingButton);
        page.locator(cartPage.cartListSection).isVisible();
        homePage.checkFooterRobotImageVisibility();
    }

    @Test(description = "This test case written for explicitly failing a test case and verifying the reports")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Cart Page", url = "https://dev.example.com/cart")
    public void failTestCase() {
        Assert.fail("Intentionally failing the test");
    }
}
