package quarkus.transactions;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

	@Inject
	@RestClient
	AccountService accountService;

	@ConfigProperty(name = "account.service", defaultValue = "http://localhost:8080")
	String accountServiceUrl;

	/**
 * This method is used to process a new transaction.
 *
 * @param accountNumber The account number for the transaction.
 * @param amount The amount to be transferred.
 * @return A Map<String, List<String>> containing the transaction result.
 *         If the transaction is successful, the map will contain a single entry with key "SUCCESS" and a list containing the transaction message.
 *         If the transaction fails, the map will contain an entry with key "ERROR" and a list containing the error message(s).
 *         If an exception occurs during the transaction, the map will contain an entry with key "EXCEPTION - <exception class>" and a list containing the exception message.
 * @throws Exception If an error occurs during the transaction.
 */
@POST
@Path("/{acctNumber}")
public Map<String, List<String>> newTransaction(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) {
    try {
        return accountService.transact(accountNumber, amount);
    } catch (Exception t) {
        Map<String, List<String>> response = new HashMap<>();
        response.put("EXCEPTION - " + t.getClass(), Collections.singletonList(t.getMessage()));
        return response;
    }
}

	/**
 * This method is used to make an asynchronous transaction.
 *
 * @param accountNumber The account number for the transaction.
 * @param amount The amount to be transferred.
 * @return A CompletionStage that completes with a Map<String, List<String>> when the transaction is completed.
 *         The map contains the transaction result. If the transaction is successful, the map will contain a single entry with key "SUCCESS" and a list containing the transaction message.
 *         If the transaction fails, the map will contain an entry with key "ERROR" and a list containing the error message(s).
 * @throws Exception If an error occurs during the transaction.
 */
@POST
@Path("/async/{acctNumber}")
public CompletionStage<Map<String, List<String>>> newTransactionAsync(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) {
    return accountService.transactAsync(accountNumber, amount);
}

	/**
 * This method is used to make a transaction using a programmatic REST client.
 *
 * @param accountNumber The account number for the transaction.
 * @param amount The amount to be transferred.
 * @return A Response object with HTTP status 200 (OK) if the transaction is successful.
 * @throws MalformedURLException If the account service URL is malformed.
 * @throws URISyntaxException If the account service URL is not a valid URI.
 */
@POST
@Path("/api/{acctNumber}")
@Bulkhead(1)
public Response newTransactionWithApi(@PathParam("acctNumber") Long accountNumber, BigDecimal amount)
        throws MalformedURLException, URISyntaxException {
    URI uri = new URI(accountServiceUrl);
    AccountServiceProgrammatic acctService = RestClientBuilder.newBuilder()
            .baseUrl(uri.toURL())
            .connectTimeout(500, TimeUnit.MILLISECONDS)
            .readTimeout(1200, TimeUnit.MILLISECONDS)
            .build(AccountServiceProgrammatic.class);

    acctService.transact(accountNumber, amount);
    return Response.ok().build();
}

/**
 * This method is used to make an asynchronous transaction using a programmatic REST client.
 *
 * @param accountNumber The account number for the transaction.
 * @param amount The amount to be transferred.
 * @return A CompletionStage that completes with a Void when the transaction is completed.
 * @throws MalformedURLException If the account service URL is malformed.
 * @throws URISyntaxException If the account service URL is not a valid URI.
 */
@POST
@Path("/api/async/{acctNumber}")
public CompletionStage<Void> newTransactionWithApiAsync(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) throws MalformedURLException, URISyntaxException {
    URI uri = new URI(accountServiceUrl);
    AccountServiceProgrammatic acctService = RestClientBuilder.newBuilder()
            .baseUrl(uri.toURL())
            .build(AccountServiceProgrammatic.class);

    return acctService.transactAsync(accountNumber, amount);
}
}
