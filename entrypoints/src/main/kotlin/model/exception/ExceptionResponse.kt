package model.exception

import io.micronaut.core.annotation.Introspected

@Introspected
data class ExceptionResponse(
    val message: String
)