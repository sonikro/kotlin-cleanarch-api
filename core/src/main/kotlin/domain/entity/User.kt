package domain.entity

import domain.exception.BusinessException

data class User(
    val id: Long = Long.MIN_VALUE,
    val name: String
)

fun User.validate() {
    if (name.length < 3) {
        throw BusinessException("User name must be longer than 3 characters")
    }
}