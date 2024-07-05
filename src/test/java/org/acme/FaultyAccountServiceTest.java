package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(WiremockAccountService.class)
public class FaultyAccountServiceTest {

    @Test
    void testTimeout() {
        given()
                .contentType(ContentType.JSON)
                .get("/transactions/123456/balance").then().statusCode(504);

        given()
                .contentType(ContentType.JSON)
                .get("/transactions/456789/balance").then().statusCode(200);
    }

}
