package API.Tests;

import API.Utils.BaseTest;
import API.Utils.ConfigUtils;
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

public class PUTApiCallTest extends BaseTest {
    /*
    1. POST - user id - 123
    2. PUT  - user id - /123
    3. GET  - user id  /123
     */
    private String BaseUrl;
    private String bearerToken;
//    private ConfigUtils configUtils;

    @Test
    public void updateUserPutCallTest() throws IOException {
        //1. POST USER
        //Create user Object : using builder pattern
//        configUtils = new ConfigUtils();
        final Logger logger = LogManager.getLogger(API.Tests.PUTApiCallTest.class);
        Properties properties = ReadPropertyFile.loadProperties("properties/apiConfig.properties");

        BaseUrl= properties.getProperty("BaseUrl");
        bearerToken = properties.getProperty("goRestBearerToken");

        Faker faker = new Faker();

        Map<String, Object> data = new HashMap<>();
        data.put("name", faker.name().fullName());
        data.put("email", faker.internet().emailAddress());
        data.put("gender", faker.demographic().sex());
        data.put("status", "active");
        System.out.println("data :" + data);

        APIRequestContext requestContext = getApiRequestContext();
        APIResponse apiPostResponse = requestContext.post(BaseUrl,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + bearerToken)
                        .setData(data)
        );
        System.out.println(apiPostResponse.url());
        System.out.println(apiPostResponse.status());

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

        //updating id of the user into properties file for future use
        ConfigUtils.updatePostIdProperty(userID);

        // 2. update user data by using PUT method

        // Updating the data object
        data.replace("status", "active", "inactive");

        System.out.println("data after updation is :- " + data);

        APIResponse putResponse = requestContext.put(BaseUrl + userID,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + bearerToken)
                        .setData(data)
        );

        Assert.assertEquals(putResponse.status(), 200);


        // 3.GET updated Response

        APIResponse getResponse = requestContext.get(BaseUrl + userID,
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
