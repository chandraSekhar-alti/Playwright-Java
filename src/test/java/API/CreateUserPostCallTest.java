package API;

import API.Utils.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserPostCallTest extends BaseTest {

    @Test
    public void postTest() throws IOException {
        APIRequestContext apiRequestContext = getApiRequestContext();
        Faker faker = new Faker();

        Map<String, Object> data = new HashMap<>();
        data.put("name", faker.name().fullName());
        data.put("email", faker.internet().emailAddress());
        data.put("gender", faker.demographic().sex());
        data.put("status", "active");

        APIResponse apiPostResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer 21ac9b0b3b4fdffcac841e90edc3e6f9313f9330b7538d12ca1ae2368ddc252e")
                        .setData(data)
        );

        System.out.println("Status Code: " + apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");
        System.out.println("apiPostResponse: " + apiPostResponse.text());

        System.out.println("data: " + data);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

        //Capturing the id
        String userID = postJsonResponse.get("id").asText();
        System.out.println("userID : " + userID);

        //GET Call : Fetch the same user by id:
        APIResponse apiGetResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users/" + userID,
                                        RequestOptions.create()
                                            .setHeader("Authorization", "Bearer 21ac9b0b3b4fdffcac841e90edc3e6f9313f9330b7538d12ca1ae2368ddc252e")
        );

        Assert.assertEquals(apiGetResponse.status(),200);
        Assert.assertEquals(apiGetResponse.statusText(),"OK");
        System.out.println(apiGetResponse.text());
        Assert.assertTrue(apiGetResponse.text().contains(userID));



    }
}
