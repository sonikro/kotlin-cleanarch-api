package model.user

import io.micronaut.core.annotation.Introspected

@Introspected
data class GetUsersResponse(
    val users: List<UserDTO>
)