package org.acme;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Collections;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class WiremockAccountService implements QuarkusTestResourceLifecycleManager {

    private WireMockServer mockServer;

    @Override
    public Map<String, String> start() {
        mockServer = new WireMockServer();
        mockServer.start();
        stubFor(get(urlEqualTo("/accounts/121212/balance"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("435.76")));

        stubFor(post(urlEqualTo("/accounts/121212/transaction"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{}")));

        return Collections.singletonMap("quarkus.transactions.AccountService/mp-rest/url", mockServer.baseUrl());

    }

    @Override
    public void stop() {
        if (null != mockServer) {
            mockServer.stop();
        }
    }

}
