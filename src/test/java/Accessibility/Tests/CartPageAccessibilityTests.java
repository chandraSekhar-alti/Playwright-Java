package Accessibility.Tests;

import Accessibility.Utils.BaseTest;
import UI.Utils.AuthActions;
import UI.Utils.ReadPropertyFile;
import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.microsoft.playwright.Page;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class CartPageAccessibilityTests extends BaseTest {

    Properties properties = ReadPropertyFile.loadProperties("properties/config.properties");
    private final String userName = properties.getProperty("username");
    private final String password = properties.getProperty("password");
    private final String applicationUrl = properties.getProperty("appURL");

    @Test(description = "Scan a specific part of cart page")
    @Link(name = "Cart Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Chandrasekhar Alti")
    public void navigationToCartPageShouldNotHaveAutomaticallyDetectableAccessibilityViolations() {
        Page page = getPage();

        page.navigate(applicationUrl);
        loginToApplication();

        page.locator(".shopping_cart_container > a > svg").click();
        page.locator("//div[@class='subheader' and text()='Your Cart']").waitFor();

        AxeResults accessibilityScanResults = new AxeBuilder(page)
                .include(List.of("#cart_contents_container"))
                .analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
    }


    @Test(description = "scanning header of cart page")
    @Link(name = "Cart Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Chandrasekhar Alti")
    public void headerOfCartPageShouldNotHaveAnyAccessibilityViolations() {
        Page page = getPage();
        AuthActions authActions = new AuthActions(page);

        page.navigate(applicationUrl);
        authActions.login(userName, password);

        page.locator(".shopping_cart_container > a > svg").click();
        page.locator("#header_container").waitFor();

        AxeResults accessibilityScanResults = new AxeBuilder(page)
                .include(List.of("#header_container"))
                .analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
    }
}
