package configuration

import database.UserRepositoryInMemory
import io.ktor.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import usecase.repository.UserRepository

fun Application.configDI() {
    di {
        bind<UserRepository>() with singleton { UserRepositoryInMemory() }
    }
}