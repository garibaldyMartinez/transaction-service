package quarkus.transactions;

import java.math.BigDecimal;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@Readiness
public class AccountHealthReadinessCheck implements HealthCheck{
    
    @Inject
    @RestClient
    AccountService accountService; 

    BigDecimal balance;

    /**
 * This method is responsible for checking the readiness of the AccountService.
 * It calls the getBalance method of the AccountService to retrieve the account balance.
 * If an exception is thrown, it checks the status of the response.
 * If the status is 500 or above, it returns a DOWN health check response with the exception details.
 * Otherwise, it returns an UP health check response with the account balance.
 *
 * @return a HealthCheckResponse indicating the readiness of the AccountService
 */
@Override
public HealthCheckResponse call() {
    
    try {
        balance = accountService.getBalance(999999999L);
    } catch (WebApplicationException ex) {
        balance = new BigDecimal(Integer.MIN_VALUE);

        if(ex.getResponse().getStatus() >= 500){
            return HealthCheckResponse
            .named("AccountServiceCheck")
            .withData("exception", ex.toString())
            .down()
            .build();
        }
    }

    return HealthCheckResponse
    .named("AccountServiceCheck")
    .withData("balance", balance.toString())
    .up()
    .build();
}

}
