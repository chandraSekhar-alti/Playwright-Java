package API.MockApi.Tests;

import API.MockApi.MockBaseTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class PostDataTest extends MockBaseTest {

    @Test
    public void postStudentsDataInMockServerTest(){
        WireMock.configureFor(HOST,PORT);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/student/rollNo=1")).
                willReturn(aResponse().withBodyFile("StudentsMappings/studentInfoMapping1.json")
                        .withHeader("Content-Type","application/json")));
    }

    @Test
    public void postCustomerShippingAddressInServerTest(){
        WireMock.configureFor(HOST,PORT);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/shipping/address")).
                willReturn(aResponse().withBodyFile("customerShippingAddress.json")
                        .withHeader("Content-Type","application/json")));
    }

    @Test
    public void postDataAsTextInServerTest(){
        WireMock.configureFor(HOST,PORT);
        WireMock.stubFor(WireMock.get((WireMock.urlEqualTo("/text/data")))
                .willReturn(aResponse().withBody("{\n" +
                        "  \"entity\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Sample Entity\",\n" +
                        "    \"type\": \"Example\",\n" +
                        "    \"value\": 100\n" +
                        "  }\n" +
                        "}\n")));

//        server.saveMappings();
    }
}
