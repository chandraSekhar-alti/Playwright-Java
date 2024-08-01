package API.MockApi.Tests;

import API.MockApi.MockBaseTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PutApiTest extends MockBaseTest {


    /*
        1. POST data
        2. GET data
        3. Update by PUT method
        4. Verify the response again from the server
     */

    @Test
    public void updateProjectDetailsTest(){

        String postData = "\"name\": \"xxxx\",\n" +
                "    \"projectName\": \"xyz\",\n" +
                "    \"info\": {\n" +
                "      \"startDate\": \"\",\n" +
                "      \"endDate\": \"\"\n" +
                "    }";

        WireMock.configureFor(HOST,PORT);
        WireMock.stubFor(post(urlEqualTo("/project/info/"))
                .willReturn(aResponse().withBody(postData))
        );

        System.out.println("POSTED successfully");
        System.out.println("debugger");

//        server.saveMappings();

    }
}
