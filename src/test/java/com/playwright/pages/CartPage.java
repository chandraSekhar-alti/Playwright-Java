package com.playwright.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.playwright.Utils.UI;

import java.util.List;

public class CartPage {
    private Page page;
    private UI ui;
    private HomePage homePage;

    public CartPage(Page page) {
        this.page = page;
        this.ui = new UI(page);
        this.homePage = new HomePage(page);
    }

    public final String cartButtons = "//button[text()='ADD TO CART']";
    public final String checkOutButton = "//a[text()='CHECKOUT']";
    public final String firstName = "#first-name";
    public final String lastName = "#last-name";
    public final String postalCode = "#postal-code";
    public final String continueButton = "input[value='CONTINUE']";
    public final String finishButton = "//a[text()='FINISH']";
    public final String orderSuccessMessage = ".complete-header";
    public final String continueShoppingButton = ".btn_secondary";
    public final String cartListSection = ".cart_list";



    public void clickOnFinishButton(){
        page.locator(finishButton).isVisible();
        page.locator(finishButton).click();
    }

    public void enteringDetails(String userName, String userLastName, String userPostalCode){
        page.locator(firstName).isVisible();
        page.locator(firstName).fill(userName);
        page.locator(lastName).isVisible();
        page.locator(lastName).fill(userLastName);
        page.locator(postalCode).isVisible();
        page.locator(postalCode).fill(userPostalCode);
    }

    public void clickOnContinueButton(){
        page.locator(continueButton).isVisible();
        page.locator(continueButton).click();
    }

    public boolean checkAddedToCartButtonsVisibility() {
        Locator elementLocators = page.locator(cartButtons);
        List<ElementHandle> elements = elementLocators.elementHandles();
        boolean valueToReturn = false;

        for (ElementHandle element : elements) {
            if (element.isVisible()) {
                ui.highlightElementByGreen(element);
                valueToReturn = true;
            } else {
                ui.highlightElementByRed(element);
                valueToReturn = false;
                return valueToReturn;
            }
        }
        return valueToReturn;
    }

    public void addingProductToCart(){
        page.locator(homePage.homePageItems).first().isVisible();
        page.locator(cartButtons).first().click();
        page.locator(homePage.cartButton).isVisible();
        page.locator(homePage.cartButton).click();
    }

    public void clickOnCheckOutButton(){
        page.locator(checkOutButton).isVisible();
        page.locator(checkOutButton).click();
    }
}
