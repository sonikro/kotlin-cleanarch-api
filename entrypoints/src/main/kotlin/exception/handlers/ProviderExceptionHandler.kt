package exception.handlers

import exception.ProviderException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import model.exception.ExceptionResponse
import javax.inject.Singleton

@Produces
@Singleton
@Requirements(
    Requires(classes = [ProviderException::class])
)
class ProviderExceptionHandler : ExceptionHandler<ProviderException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>?, exception: ProviderException?): HttpResponse<*> {
        return HttpResponse.serverError(ExceptionResponse(message = exception?.message ?: "Internal server error"))
    }

}