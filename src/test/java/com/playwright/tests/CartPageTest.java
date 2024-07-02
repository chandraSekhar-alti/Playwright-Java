package com.playwright.tests;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Page;
import com.playwright.Utils.BaseTest;
import com.playwright.pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CartPageTest extends BaseTest {
    private CartPage cartPage;


    @Test
    public void verifyingOrderingAnItemFlow(){
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
}
