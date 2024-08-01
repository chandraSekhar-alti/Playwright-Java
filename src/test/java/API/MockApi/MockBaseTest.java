package API.MockApi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class MockBaseTest {
    private static final Logger logger = LogManager.getLogger(MockBaseTest.class);
    public static final String HOST = "localhost";
    public static final int PORT = 9090;
    public static final int HTTPS_PORT = 8089;
    public static final String wiremockFilesPath = "src/test/resources/wiremock";
    public static WireMockServer server;

    @BeforeSuite
    public static void setup() {
        logger.info("server starting !!!");
        server = new WireMockServer(
                WireMockConfiguration.options()
                        .port(PORT)
                        .httpsPort(HTTPS_PORT)
                        .usingFilesUnderDirectory(wiremockFilesPath)
                        .notifier(new ConsoleNotifier(true)));

        server.start();
        logger.info("WireMock server Running on port :- " +"http://"+ HOST + ":" + PORT);
    }

    @AfterSuite
    public void tearDown(){
//        server.saveMappings();
        if (server != null){
            System.out.println("Closing server!!!");
            server.stop();
        }
    }
}
