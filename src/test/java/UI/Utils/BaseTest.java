package UI.Utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import io.qameta.allure.Allure;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    public Page page;
    private BrowserContext context;
    private BrowserFactory browserFactory;
    private AuthActions authActions;
    private String applicationURL;
    private int viewPortWidth;
    private int viewPortHeight;
    private String userName;
    private String password;

    @BeforeClass
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
    public void tearDown(ITestResult result) {
        if (!result.isSuccess()){
            logger.info("Test failed: {}", result.getName());
            if (page != null) {
                String screenshotPath = System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\"+result.getName()+".png";

                Path path = Paths.get(screenshotPath);

                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(path)
                        .setFullPage(true));

               try (InputStream is = Files.newInputStream(path)){
                        Allure.attachment(result.getName()+".png",is);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Screenshot taken for test: {}", result.getName());
            } else {
                logger.warn("Page object is null, cannot take screenshot for test: {}", result.getName());
            }
        }
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
            if (browserFactory.getPlaywright() != null){
                browserFactory.getPlaywright().close();
            }
        } catch (Exception e) {
            logger.error("Failed to tear down browser context and page", e);
        }
    }

    public Page getPage() {
        return page;
    }

    @Attachment(value = "Screenshot of {0}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        try {
            if (page != null) {
                return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            } else {
                logger.warn("Page object is null, cannot take screenshot");
                return new byte[0];
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot for test: {}", name, e);
            return new byte[0];
        }
    }

}
