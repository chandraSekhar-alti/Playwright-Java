package API.Tests;

import API.Utils.BaseTest;
import UI.Utils.ReadPropertyFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PATCHApiCallTest extends BaseTest {
    /*
    1. POST - user id - 123
    2. PATCH  - user id - /123
    3. GET  - user id  /123
     */
    protected String BaseUrl;
    protected String bearerToken;

    @Test
    public void updateUserDataPatchCallTest() throws IOException {
        //1. POST USER
        //Create user Object : using builder pattern
        Properties properties = ReadPropertyFile.loadProperties("properties/apiConfig.properties");
        BaseUrl= properties.getProperty("BaseUrl");
        bearerToken = properties.getProperty("goRestBearerToken");

        final Logger logger = LogManager.getLogger(API.Tests.PUTApiCallTest.class);

        Faker faker = new Faker();

        Map<String, Object> data = new HashMap<>();
        data.put("name", faker.name().fullName());
        data.put("email", faker.internet().emailAddress());
        data.put("gender", faker.demographic().sex());
        data.put("status", "active");

        APIRequestContext requestContext = getApiRequestContext();
        APIResponse apiPostResponse = requestContext.post(BaseUrl,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + bearerToken)
                        .setData(data)
        );
        logger.info(apiPostResponse.url());
        logger.info(apiPostResponse.status());

        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        String responseText = apiPostResponse.text();
        logger.info("responseText : {}", responseText);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        logger.info(postJsonResponse.toPrettyString());

        //Capturing the id
        String userID = postJsonResponse.get("id").asText();
        logger.info("userID : {}", userID);

        // 2. update user data by using PATCH method

        // Updating the data object
        data.replace("status", "active", "inactive");


        APIResponse putResponse = requestContext.patch(BaseUrl + userID,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + bearerToken)
                        .setData(data)
        );

        Assert.assertEquals(putResponse.status(), 200);


        // 3.GET updated Response

        APIResponse getResponse = requestContext.get(BaseUrl+ userID,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + bearerToken)
        );

        int statusCode = getResponse.status();
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(getResponse.ok());
        String statusResText = getResponse.statusText();
        Assert.assertEquals(statusResText, "OK");

        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonNode getJsonResponse = objectMapper2.readTree(getResponse.body());
        String jsonPrettyGetResponse = getJsonResponse.toPrettyString();
        logger.info(jsonPrettyGetResponse);


        Assert.assertEquals(getJsonResponse.get("status").asText(), "inactive");


    }
}
