package model.user

import io.micronaut.core.annotation.Introspected

@Introspected
data class PostUserResponse(
    val createdUserId: Long
)