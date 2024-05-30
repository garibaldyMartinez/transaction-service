package quarkus.transactions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accounts")
@RegisterRestClient(configKey  = "accounts-service", baseUri = "http://account-service:80")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountService {

    @GET
    @Path("/{acctNumber}/balance")
    BigDecimal getBalance(@PathParam("acctNumber") Long accountNumber);

    @POST
    @Path("/{AccountNumber}/transaction")
    Map<String, List<String>> transact(@PathParam("AccountNumber") Long accountNumber, BigDecimal amount);

}
