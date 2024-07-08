package Accessibility.Utils;

import UI.Utils.AuthActions;
import UI.Utils.ReadPropertyFile;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;


public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    Properties properties = ReadPropertyFile.loadProperties("config.properties");
    private final String userName = properties.getProperty("username");
    private final String password = properties.getProperty("password");

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        page.close();
        browser.close();
        playwright.close();
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getBrowserContext() {
        return context;
    }

    public void loginToApplication() {
        AuthActions authActions = new AuthActions(page);
        authActions.login(userName, password);
    }

}
