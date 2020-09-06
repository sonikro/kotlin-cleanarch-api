package exception.handlers

import domain.exception.BusinessException
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
    Requires(classes = [BusinessException::class])
)
class BusinessExceptionHandler : ExceptionHandler<BusinessException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>?, exception: BusinessException?): HttpResponse<*> {
        return HttpResponse.badRequest(ExceptionResponse(message = exception?.message ?: "Business Error"))
    }

}