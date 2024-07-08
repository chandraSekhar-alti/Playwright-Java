package UI.Utils;


import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class BrowserFactory {
    private static final Logger logger = LogManager.getLogger(BrowserFactory.class);
    public Browser browser;
    Playwright playwright;
    public void BrowserSetUp(){
        Properties properties = ReadPropertyFile.loadProperties("config.properties");
        String browserName =properties.getProperty("browser");
        String channelName = properties.getProperty("binary");
        boolean runMode = Boolean.parseBoolean(properties.getProperty("headless"));

        logger.info("This will run before the class");
        playwright = Playwright.create();

        if (browserName.equalsIgnoreCase("chrome")){
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setChannel(channelName).setHeadless(runMode)
            );
        }
        else if (browserName.equalsIgnoreCase("edge")){
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setChannel(channelName).setHeadless(runMode)
            );
        }
       else if (browserName.equalsIgnoreCase("firefox")){
            browser = playwright.firefox().launch(
                    new BrowserType.LaunchOptions().setChannel(channelName).setHeadless(runMode)
            );
       } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(
                    new BrowserType.LaunchOptions().setChannel(channelName).setHeadless(runMode)
            );
       } else {
            logger.error("Invalid browser type {}",browserName);
        }
    }


    public Browser getBrowser(){
        return browser;
    }

    public Playwright getPlaywright(){
        return playwright;
    }

    public BrowserContext createBrowserContext(int width, int height){
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(width,height);
        return this.browser.newContext(contextOptions);
    }
}
