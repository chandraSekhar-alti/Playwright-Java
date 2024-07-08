package Accessibility.Tests;

import UI.Utils.ReadPropertyFile;
import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Properties;

public class HomePageTest {

    Properties properties = ReadPropertyFile.loadProperties("config.properties");

    private final String applicationUrl = properties.getProperty("appURL");
    @Test
    public void shouldNotHaveAutomaticallyDetectableAccessibilityIssues(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate(applicationUrl);

        AxeResults accessibilityScannerResults = new AxeBuilder(page).analyze();
        Assert.assertEquals(Collections.emptyList(), accessibilityScannerResults.getViolations());
    }
}
