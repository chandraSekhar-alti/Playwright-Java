package Accessibility.Tests;

import Accessibility.Utils.BaseTest;
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

public class HomePageAccessibilityTests extends BaseTest {

    Properties properties = ReadPropertyFile.loadProperties("properties/config.properties");
    private final String applicationUrl = properties.getProperty("appURL");

    @Test(description = "Disabling individual scan rules")
    @Link(name = "Home Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Chandrasekhar Alti")
    public void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() {
        Page page = getPage();

        page.navigate(applicationUrl);
        loginToApplication();
        AxeResults accessibilityScannerResults = new AxeBuilder(page)
                .disableRules(List.of("duplicate-id"))
                .analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScannerResults.getViolations());
    }

    @Test(description = "footer section should not have auto accessibility violation rules")
    @Link(name = "Home Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Chandrasekhar Alti")
    public void footerSectionShouldNotHaveAutomaticallyDetectableAccessibilityViolations() {
        Page page = getPage();

        page.navigate(applicationUrl);
        loginToApplication();

        AxeResults accessibilityScanResults = new AxeBuilder(page)
                .include(List.of(".footer"))
                .analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
    }
}
