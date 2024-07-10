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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class LoginAccessibilityTests extends BaseTest {
    Properties properties = ReadPropertyFile.loadProperties("properties/config.properties");
    private final String applicationUrl = properties.getProperty("appURL");

    @Test(description = "Excluding individual elements from a scan in the login page")
    @Link(name = "Login Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Chandrasekhar Alti")
    public void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() {
        Page page = getPage();

        page.navigate(applicationUrl);

        AxeResults accessibilityScannerResults = new AxeBuilder(page)
                .exclude(Arrays.asList(".login_logo", ".login_credentials_wrap-inner"))
                .analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScannerResults.getViolations());
    }

    @Test(description = "credentials wrapper box should not have any accessibility issues")
    @Link(name = "Login Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Chandrasekhar Alti")
    public void credentialsContainerShouldNotHaveAnyAccessibilityIssues() {
        Page page = getPage();

        page.navigate(applicationUrl);

        AxeResults accessibilityScannerResults = new AxeBuilder(page)
                .include(List.of(".login_credentials_wrap-inner"))
                .analyze();

        Assert.assertEquals(Collections.emptyList(), accessibilityScannerResults.getViolations());
    }

    @Test(description = "credentials wrapper box should not have any accessibility issues with specific WCC tags")
    @Link(name = "Login Page", url = "https://dev.example.com/cart")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Chandrasekhar Alti")
    public void loginHeaderShouldNotHaveAnyAccessibilityIssues() {
        Page page = getPage();

        page.navigate(applicationUrl);

        AxeResults accessibilityScannerResults = new AxeBuilder(page)
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .include(List.of(".login_logo"))
                .analyze();

        Assert.assertEquals(Collections.emptyList(), accessibilityScannerResults.getViolations());
    }
}
