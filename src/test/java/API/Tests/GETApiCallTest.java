package API.Tests;

import API.Utils.BaseTest;
import UI.Utils.ReadPropertyFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class GETApiCallTest extends BaseTest {

    private String BaseUrl;
    private String userId;

    @Test(dependsOnMethods = "API.Tests.POSTApiTest.PostDataTest")
    public void getSpecificUserApiTest() throws IOException {
        final Logger logger = LogManager.getLogger(API.Tests.PUTApiCallTest.class);
        Properties properties = ReadPropertyFile.loadProperties("properties/apiConfig.properties");
        BaseUrl= properties.getProperty("BaseUrl");
        userId = properties.getProperty("post.id");

        APIRequestContext apiRequestContext = getApiRequestContext();
        APIResponse apiResponse = apiRequestContext.get(BaseUrl,

                RequestOptions.create()
                        .setQueryParam("id", userId)

        );
        Assert.assertTrue(apiResponse.ok());
        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertEquals(apiResponse.statusText(), "OK");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = response.toPrettyString();
        logger.info(jsonPrettyResponse);
    }

    @Test
    public void getUserApiTest() throws IOException {
        Properties properties = ReadPropertyFile.loadProperties("properties/apiConfig.properties");
        BaseUrl= properties.getProperty("reqResUrl");

        APIRequestContext apiRequestContext = getApiRequestContext();
        APIResponse apiResponse = apiRequestContext.get(BaseUrl);

        int statusCode = apiResponse.status();
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(apiResponse.ok());
        String statusResText = apiResponse.statusText();
        Assert.assertEquals(statusResText, "OK");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();

        Map<String, String> headersMap = apiResponse.headers();
        Assert.assertEquals(headersMap.get("connection"), "close");
        //optional assertion
//        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");

    }
}
