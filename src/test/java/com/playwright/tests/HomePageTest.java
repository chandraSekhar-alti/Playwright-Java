package com.playwright.tests;

import com.microsoft.playwright.Page;
import com.playwright.Utils.BaseTest;
import com.playwright.pages.HomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class HomePageTest extends BaseTest {
    private HomePage homePage;

    @Test(description = "Verify visibility of header items on the home page")
    @Description("This test verifies the visibility of header items like app logo, cart button, product label, and sorting button on the home page.")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Website", url = "https://example.com/")
    @Epic("Homepage")
    @Feature("Header")
    @Story("Visibility Checks")
    public void homePageHeaderItemsVisibilityChecks() {
        Page page = getPage();
        homePage = new HomePage(page);

        homePage.validateAppLogoVisibility();
        homePage.checkCartButtonVisibility();
        homePage.checkProductLabelVisibility();
        homePage.checkSortingButtonVisibility();
    }

    @Test(description = "Verify visibility of container items on the home page")
    @Description("This test verifies the visibility of items within the home page container.")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Website", url = "https://example.com/")
    @Epic("Homepage")
    @Feature("Container")
    @Story("Visibility Checks")
    public void homePageContainerItemsVisibilityCheck() {
        Page page = getPage();
        homePage = new HomePage(page);

        boolean returnedValue = homePage.checkHomePageItemsVisibilityCheck();
        Assert.assertTrue(returnedValue, "Not all products are visible on the home page.");
    }

    @Test(description = "Verify visibility of footer items on the home page")
    @Description("This test verifies the visibility of footer section items like social account icons, privacy policy section, and footer robot image.")
    @Owner("Chandrasekhar Alti")
    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Website", url = "https://example.com/")
    @Epic("Homepage")
    @Feature("Footer")
    @Story("Visibility Checks")
    public void homePageFooterVisibilityCheck() {
        Page page = getPage();
        homePage = new HomePage(page);

        homePage.checkFooterSectionVisibility();
        homePage.checkSocialAccountContainerVisibility();
        homePage.checkTwitterIconVisibility();
        homePage.checkFacebookIconVisibility();
        homePage.checkLinkedInIconVisibility();
        homePage.checkPrivacyPolicySectionVisibility();
        homePage.checkFooterRobotImageVisibility();
    }
}
