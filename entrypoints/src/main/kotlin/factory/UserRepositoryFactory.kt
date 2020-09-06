package factory

import database.UserRepositoryInMemory
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
internal class UserRepositoryFactory {
    @Singleton
    fun userRepository(): UserRepositoryInMemory {
        return UserRepositoryInMemory()
    }
}