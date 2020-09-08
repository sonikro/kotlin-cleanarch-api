package model.user

import io.micronaut.core.annotation.Introspected

@Introspected
data class PostUserRequest(
    val name: String
)