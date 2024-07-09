package org.acme;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.util.Collections;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class WiremockAccountService implements QuarkusTestResourceLifecycleManager {

    private WireMockServer mockServer;

    @Override
    public Map<String, String> start() {
        mockServer = new WireMockServer(wireMockConfig().port(8081));
        mockServer.start();

        mockTimeout();
        mockAccountService();
        

        return Collections.singletonMap("quarkus.transactions.AccountService/mp-rest/url", mockServer.baseUrl());

    }

    protected void mockAccountService() {
        stubFor(get(urlEqualTo("/accounts/121212/balance"))
            .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody("435.76")));
    
        stubFor(post(urlEqualTo("/accounts/121212/transaction"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200).withBody("{}")));
      }
    
      protected void mockTimeout() {
        stubFor(get(urlEqualTo("/accounts/123456/balance"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(200).withFixedDelay(200).withBody("435.76")));
    
        stubFor(get(urlEqualTo("/accounts/456789/balance"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json").withStatus(200).withBody("435.76")));
      }

    @Override
    public void stop() {
        if (null != mockServer) {
            mockServer.stop();
        }
    }

}
