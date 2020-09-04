package configuration

import domain.exception.BusinessException
import exception.BadRequestException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configExceptionHandling() {
    install(StatusPages) {
        exception<BadRequestException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad Request")
        }
        exception<BusinessException> { cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Business error")
        }
    }
}