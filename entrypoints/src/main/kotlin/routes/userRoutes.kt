package routes

import domain.entity.User
import exception.BadRequestException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import model.user.PostUserRequest
import model.user.PostUserResponse
import model.user.PutUserRequest
import model.user.toDto
import org.kodein.di.instance
import org.kodein.di.ktor.di
import usecase.invoke
import usecase.repository.UserRepository
import usecase.user.*

fun Routing.userRoutes() {
    val userRepository by di().instance<UserRepository>()

    route("/user") {
        get {
            listUsersUseCase(userRepository)
                .invoke()
                .let { users ->
                    call.respond(HttpStatusCode.OK, users.map { it.toDto() })
                }
        }
        get("/{userId}") {
            val userId = call.parameters["userId"]?.toLong() ?: throw BadRequestException("Missing userId")
            getUserUseCase(userRepository)
                .invoke(userId)
                .let { user ->
                    call.respond(HttpStatusCode.OK, user.toDto())
                }
        }
        post {
            call
                .receive<PostUserRequest>()
                .let { body ->
                    createUserUseCase(userRepository).invoke(User(name = body.name))
                }
                .let { userId ->
                    call.respond(HttpStatusCode.Created, PostUserResponse(userId))
                }
        }

        put("/{userId}") {
            val userId = call.parameters["userId"]?.toLong() ?: throw BadRequestException("Missing userId")
            call
                .receive<PutUserRequest>()
                .let { body ->
                    updateUserUseCase(userRepository).invoke(User(name = body.name, id = userId))
                }
                .let {
                    call.respond(HttpStatusCode.NoContent)
                }
        }

        delete("/{userId}") {
            val userId = call.parameters["userId"]?.toLong() ?: throw BadRequestException("Missing userId")
            deleteUserUseCase(userRepository)
                .invoke(userId)
                .let { user ->
                    call.respond(HttpStatusCode.OK, user.toDto())
                }
        }

    }
}