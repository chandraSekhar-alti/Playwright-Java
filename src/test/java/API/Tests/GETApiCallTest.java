package API.Tests;

import API.Utils.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETApiCallTest extends BaseTest {


    @Test
    public void getSpecificUserApiTest() throws IOException {
        APIRequestContext apiRequestContext = getApiRequestContext();
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users",

                RequestOptions.create()
                        .setQueryParam("id", "7018213")
                        .setQueryParam("status", "active")

        );
        Assert.assertTrue(apiResponse.ok());
        Assert.assertEquals(apiResponse.status(), 200);
        Assert.assertEquals(apiResponse.statusText(), "OK");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(apiResponse.body());
//        String jsonPrettyResponse = response.toPrettyString();

        Assert.assertTrue(response.isArray(), "Response is not an array");
        Assert.assertEquals(response.size(), 1, "Array size is not 1");

        JsonNode user = response.get(0);
        String name = user.get("name").asText();
        String email = user.get("email").asText();
        Assert.assertEquals(name, "Kamla Marar");
        Assert.assertEquals(email, "marar_kamla@kris.example");
    }

    @Test
    public void getUserApiTest() throws IOException {
        APIRequestContext apiRequestContext = getApiRequestContext();

        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");

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
        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");

    }
}
