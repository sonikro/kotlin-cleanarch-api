package model.user

import domain.entity.User
import io.micronaut.core.annotation.Introspected

@Introspected
data class UserDTO(
    val id: Long? = Long.MIN_VALUE,
    val name: String
)

fun User.toDto() = UserDTO(id = id, name = name)