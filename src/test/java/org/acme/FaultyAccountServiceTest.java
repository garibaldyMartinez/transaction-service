package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(WiremockAccountService.class)
public class FaultyAccountServiceTest {

    /**
 * This test method is designed to verify the behavior of the faulty account service.
 * It sends HTTP requests to the account service endpoints and checks the response status codes.
 *
 * The first request is expected to time out and return a 504 status code.
 * The second request is expected to succeed and return a 200 status code.
 *
 * @throws Exception if any unexpected error occurs during the test execution
 */
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
