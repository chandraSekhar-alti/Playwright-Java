package com.playwright.Utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    protected Page page;
    private BrowserContext context;
    private BrowserFactory browserFactory;
    private AuthActions authActions;
    private String applicationURL;
    private int viewPortWidth;
    private int viewPortHeight;
    private String userName;
    private String password;

    @BeforeSuite
    public void loadEnvVariables() {
        try {
            logger.info("Running BeforeSuite");
            browserFactory = new BrowserFactory();
            Properties properties = ReadPropertyFile.loadProperties("config.properties");
            applicationURL = properties.getProperty("appURL");
            userName = properties.getProperty("username");
            password = properties.getProperty("password");
            viewPortWidth = Integer.parseInt(properties.getProperty("view_port_width"));
            viewPortHeight = Integer.parseInt(properties.getProperty("view_port_height"));
        } catch (Exception e) {
            logger.error("Test failed while Running Before Suite Hooks", e);
            throw new RuntimeException("Test Failed While Running loadEnvVariables function in Before Suite hooks", e);
        }
    }

    @BeforeMethod
    public void setUp() {
        logger.info("Running SetUp function in BeforeMethod");
        try {
            browserFactory.BrowserSetUp();
            context = browserFactory.createBrowserContext(viewPortWidth, viewPortHeight);
            page = context.newPage();
            page.navigate(applicationURL);
            authActions = new AuthActions(page);
            authActions.login(userName, password);
        } catch (Exception e) {
            logger.error("Test failed while running BeforeHooks ", e);
            throw new RuntimeException("Test Failed while Running Before Method Hooks" + e);
        }
    }

    @AfterMethod
    public void tearDown() {
        authActions = new AuthActions(page);
        authActions.logout();

        try {
            if (context != null) {
                context.clearCookies();
                context.close();
            }
            if (page != null) {
                page.close();
            }
            if (browserFactory.getBrowser() != null) {
                browserFactory.getBrowser().close();
            }
        } catch (Exception e) {
            logger.error("Failed to tear down browser context and page", e);
        }
    }

    public Page getPage() {
        return page;
    }
}
