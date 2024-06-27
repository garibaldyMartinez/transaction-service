package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(WiremockAccountService.class)
class TransactionServiceTest {

    /**
 * This test method is used to verify the functionality of the transaction service.
 * It sends a POST request to the "/transactions/{accountNumber}" endpoint with a JSON body containing the transaction amount.
 * The test asserts that the response status code is 200, indicating a successful transaction.
 */
@Test
void testTransaction() {
    given()
            .body("142.12") // The transaction amount in JSON format
            .contentType(ContentType.JSON) // The content type of the request
            .when().post("/transactions/{accountNumber}", 121212) // The account number to which the transaction is made
            .then()
            .statusCode(200); // The expected status code of the response
}
}
