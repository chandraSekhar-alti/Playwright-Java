package API.Utils;

import UI.Utils.ReadPropertyFile;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;

public class BaseTest {
    Playwright playwright;
    APIRequest apiRequest;
    @Getter
    APIRequestContext apiRequestContext;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @AfterMethod
    public void tearDown(){
        playwright.close();
    }
}
