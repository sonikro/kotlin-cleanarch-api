package domain.entity

import domain.exception.BusinessException

data class Skill(
    val id: Long,
    val name: String,
    val level: String
)

fun Skill.validate() {
    if (name.length < 3) {
        throw BusinessException("Skill name must be longer than 3 characters")
    }
    if (level.isEmpty()) {
        throw BusinessException("Skill level must be longer than 1 character")
    }
}