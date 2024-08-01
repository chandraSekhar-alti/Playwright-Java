package API.MockApi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class Master {

    public static final String HOST = "localhost";
    public static final int PORT = 9090;
    public static final int HTTPS_PORT = 8089;
    public static final String wiremockFilesPath ="src/test/resources/wiremock";

    public static void main(String[] args) {
        WireMockServer server = new WireMockServer(
                WireMockConfiguration.options()
                        .port(PORT)
                        .httpsPort(HTTPS_PORT)
                        .usingFilesUnderDirectory(wiremockFilesPath)
                        .notifier(new ConsoleNotifier(true))
        );

        server.start();

        System.out.println("WireMock server started on " + HOST + ":" + PORT);
    }
}
