package quarkus.transactions;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class AccountExceptionMapper implements ResponseExceptionMapper<AccountNotFoundException> {

    /**
 * This method is responsible for transforming a JAX-RS response into an AccountNotFoundException.
 *
 * @param response The JAX-RS response that triggered the exception.
 * @return A new AccountNotFoundException with a custom error message.
 */
@Override
public AccountNotFoundException toThrowable(Response response) {
    return new AccountNotFoundException("Failed to retrieve account");
}

    /**
 * This method determines whether the exception mapper should handle the given response.
 *
 * @param status The HTTP status code of the response.
 * @param headers The headers of the response.
 * @return {@code true} if the exception mapper should handle the response, {@code false} otherwise.
 *         In this case, the method returns {@code true} if the status code is 404 (Not Found).
 */
@Override
public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status == 404;
}

}
