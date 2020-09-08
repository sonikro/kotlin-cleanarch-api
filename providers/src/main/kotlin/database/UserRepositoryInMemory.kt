package database

import domain.entity.User
import exception.ProviderException
import usecase.repository.UserRepository

class UserRepositoryInMemory : UserRepository {
    private val userList = mutableListOf<User>()
    override fun createUser(user: User): Long {
        val nextId = userList.size
        val userWithId = user.copy(id = nextId.toLong())
        userList.add(userWithId)
        return userWithId.id
    }

    override fun getUser(id: Long): User {
        return kotlin.runCatching {
            userList[id.toInt()].copy()
        }.getOrElse {
            throw ProviderException("Could not find User with id $id")
        }
    }

    override fun deleteUser(id: Long): User {
        return kotlin.runCatching {
            userList.removeAt(id.toInt()).copy()
        }.getOrElse {
            throw ProviderException("Could not find user with id $id")
        }
    }

    override fun listUsers(): List<User> = userList

    override fun updateUser(user: User) {
        val existingUser = userList.find { userTarget -> userTarget.id == user.id }
            ?: throw ProviderException("Could not find user $user")
        userList[existingUser.id.toInt()] = user.copy()
    }

}