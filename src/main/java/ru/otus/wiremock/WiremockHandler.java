package ru.otus.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class WiremockHandler {

    private static WireMockServer wireMockServer;

    public static void close() {
        wireMockServer.stop();
    }

    public static void start(int port) {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
    }

    public static WireMockServer getWireMockServer() {
        if (wireMockServer == null) {
            throw new NullPointerException("Wiremock не инициализирован");
        }
        return wireMockServer;
    }

}
