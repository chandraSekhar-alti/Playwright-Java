package com.playwright.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.playwright.Utils.UI;

import java.util.List;

public class HomePage {
    private Page page;
    private UI ui;

    public HomePage(Page page) {
        this.page = page;
        this.ui = new UI(page);
    }

    public final String appLogo = ".app_logo";
    public final String cartButton = ".shopping_cart_container > a > svg ";
    public final String productLabel = ".product_label";
    public final String sortingButton = ".product_sort_container";
    public final String homePageItems = ".inventory_list > div";
    public final String footerSection = ".footer";
    public final String socialAccountContainer = ".social";
    public final String twitterIcon = ".social_twitter";
    public final String facebookIcon = ".social_facebook";
    public final String linkedInIcon = ".social_linkedin";
    public final String privacyPolicySection = ".footer_copy";
    public final String footerRobitImage = ".footer_robot";


    public void validateAppLogoVisibility() {
        if (page.locator(appLogo).isVisible()) {
            ui.highlightElementByGreen(appLogo);
        }
    }

    public void checkCartButtonVisibility() {
        if (page.locator(cartButton).isVisible()) {
            ui.highlightElementByGreen(cartButton);
        }
    }

    public void checkProductLabelVisibility() {
        if (page.locator(productLabel).isVisible()) {
            ui.highlightElementByGreen(productLabel);
        }
    }

    public void checkSortingButtonVisibility() {
        if (page.locator(sortingButton).isVisible()) {
            ui.highlightElementByGreen(sortingButton);
        }
    }

    public boolean checkHomePageItemsVisibilityCheck() {
        Locator elementLocators = page.locator(homePageItems);
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

    public void checkFooterSectionVisibility(){
        page.locator(footerSection).isVisible();
        ui.highlightElementByGreen(footerSection);
    }

    public void checkSocialAccountContainerVisibility(){
        page.locator(socialAccountContainer).isVisible();
        ui.highlightElementByGreen(socialAccountContainer);
    }

    public void checkTwitterIconVisibility(){
        page.locator(twitterIcon).isVisible();
        ui.highlightElementByGreen(twitterIcon);
    }

    public void checkFacebookIconVisibility(){
        page.locator(facebookIcon).isVisible();
        ui.highlightElementByGreen(facebookIcon);
    }

    public void checkLinkedInIconVisibility(){
        page.locator(linkedInIcon).isVisible();
        ui.highlightElementByGreen(linkedInIcon);
    }
    public void checkPrivacyPolicySectionVisibility(){
        page.locator(privacyPolicySection).isVisible();
        ui.highlightElementByGreen(privacyPolicySection);
    }

    public void checkFooterRobotImageVisibility(){
        page.locator(footerRobitImage).isVisible();
        ui.highlightElementByGreen(footerRobitImage);
    }

}
