package model.user

import domain.entity.User

data class UserDTO(
    val id: Long? = Long.MIN_VALUE,
    val name: String
)

fun User.toDto() = UserDTO(id = id, name = name)