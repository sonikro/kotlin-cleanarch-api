package model.user

import io.micronaut.core.annotation.Introspected

@Introspected
data class PutUserRequest(
    val name: String
)