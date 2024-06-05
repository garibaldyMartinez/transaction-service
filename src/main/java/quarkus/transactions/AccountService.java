package quarkus.transactions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accounts")
@RegisterRestClient(configKey = "accounts-service")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountService {

    @GET
    @Path("/{acctNumber}/balance")
    BigDecimal getBalance(@PathParam("acctNumber") Long accountNumber);

    @POST
    @Path("/{accountNumber}/transaction")
    Map<String, List<String>> transact(@PathParam("accountNumber") Long accountNumber, BigDecimal amount);

    @POST
    @Path("/{accountNumber}/trancation")
    CompletionStage<Void> transactAsync(@PathParam("accountNumber") Long accountNumber, BigDecimal amount);

}
