package quarkus.transactions;

import java.math.BigDecimal;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

public class TransactionResource {

    @Inject
    @RestClient
    AccountService accountService;

    @POST
    @Path("/acctNumber")
    public Response newTransaction(@PathParam("acctnumber") Long accountNumber, BigDecimal amount) {
        accountService.transact(accountNumber, amount);
        return Response.ok().build();
    }
}
