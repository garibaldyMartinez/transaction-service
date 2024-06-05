package quarkus.transactions;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {
	
	@Inject
	@RestClient
	AccountService accountService;

	@POST
	@Path("/{acctNumber}")
	public Map<String, List<String>> newTransaction(@PathParam("acctNumber") Long accountNumber, BigDecimal amount) {
		try {
			return accountService.transact(accountNumber, amount);
		} catch (Throwable t) {
			t.printStackTrace();
			Map<String, List<String>> response = new HashMap<>();
			response.put("EXCEPTION - " + t.getClass(), Collections.singletonList(t.getMessage()));
			return response;
		}
	}

	// @POST
	// @Path("/async/{acctNumber}")
	// public CompletionStage<Map<String, List<String>>>
	// newTransactionAsync(@PathParam("acctNumber") Long accountNumber,
	// BigDecimal amount) {
	// return accountService.transactAsync(accountNumber, amount);
	// }
	//
	// @POST
	// @Path("/api/{acctNumber}")
	// public Response newTransactionWithApi(@PathParam("acctNumber") Long
	// accountNumber, BigDecimal amount)
	// throws MalformedURLException {
	// AccountServiceProgrammatic acctService =
	// RestClientBuilder.newBuilder().baseUrl(new URL(accountServiceUrl))
	// .connectTimeout(500, TimeUnit.MILLISECONDS).readTimeout(1200,
	// TimeUnit.MILLISECONDS)
	// .build(AccountServiceProgrammatic.class);
	//
	// acctService.transact(accountNumber, amount);
	// return Response.ok().build();
	// }
	//
	// @POST
	// @Path("/api/async/{acctNumber}")
	// public CompletionStage<Void>
	// newTransactionWithApiAsync(@PathParam("acctNumber") Long accountNumber,
	// BigDecimal amount) throws MalformedURLException {
	// AccountServiceProgrammatic acctService =
	// RestClientBuilder.newBuilder().baseUrl(new URL(accountServiceUrl))
	// .build(AccountServiceProgrammatic.class);
	//
	// return acctService.transactAsync(accountNumber, amount);
	// }
}
