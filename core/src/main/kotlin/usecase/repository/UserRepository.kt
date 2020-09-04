package usecase.repository

import domain.entity.User

interface UserRepository {
    fun createUser(user: User): Long
    fun getUser(id: Long): User
    fun deleteUser(id: Long): User
    fun listUsers(): List<User>
    fun updateUser(user: User)
}