package API.Tests;

import API.Utils.BaseTest;
import API.Utils.Users;
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

public class POSTApiTest extends BaseTest {

    Faker faker = new Faker();


    @Test
    public void PostDataTest() throws IOException {
        Map<String, Object> data = new HashMap<>();
        String fullName = faker.name().fullName();
        String emailAddress = faker.internet().emailAddress();
        String gender = faker.demographic().sex();

        data.put("name", fullName);
        data.put("email", emailAddress);
        data.put("gender", gender);

        APIRequestContext apiRequestContext = getApiRequestContext();
        APIResponse postResponse = apiRequestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setData(data));

        Assert.assertTrue(postResponse.ok());
        System.out.println("response status code is :- "+postResponse.status());
        Assert.assertEquals(postResponse.status(), 201);
        Assert.assertEquals(postResponse.statusText(), "Created");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(postResponse.body());

        Assert.assertEquals(response.at("/name").asText(), fullName, "Name in response does not match the sent name");
        Assert.assertEquals(response.at("/email").asText(), emailAddress, "email in response does not match the sent email");
        Assert.assertEquals(response.at("/gender").asText(), gender, "gender in response does not match the sent gender");

    }

    @Test
    public void postDataUsingLombok(){
        //Create user Object : using builder pattern
        Users userData = Users.builder()
                                .name("sampleName")
                                .email("demo@gmail.com")
                                .gender("male")
                                .status("active").build();

        APIRequestContext apiRequestContext = getApiRequestContext();
        APIResponse postResponse = apiRequestContext.post("https://reqres.in/api/users",
                RequestOptions.create()
                        .setData(userData)
        );
        Assert.assertEquals(postResponse.status(),201);
        Assert.assertEquals(postResponse.statusText(),"Created");

        String responseText = postResponse.text();
        System.out.println("response text is : "+responseText);

        System.out.println("userData: "+userData);

    }
}
