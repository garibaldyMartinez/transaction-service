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

    /**
 * This test method is used to verify the functionality of the balance retrieval in the transaction service.
 * It sends a POST request to the "/transactions/{accountNumber}/balance" endpoint with a JSON body containing the transaction amount.
 * The test asserts that the response status code is 200, indicating a successful balance retrieval.
 *
 * @param accountNumber The account number to which the transaction is made.
 *                      This parameter is used in the POST request URL and should be a valid account number.
 *
 * @param transactionAmount The transaction amount in JSON format.
 *                          This parameter is used in the POST request body and should be a valid decimal number.
 *
 * @return The method does not return any value.
 *         However, it asserts the status code of the response to ensure the balance retrieval is successful.
 */
@Test
void testBalance() {
    given()
            .body("142.12") // The transaction amount in JSON format
            .contentType(ContentType.JSON) // The content type of the request
            .when().post("/transactions/{accountNumber}/balance", 121212) // The account number to which the transaction is made
            .then()
            .statusCode(200); // The expected status code of the response
}
}
