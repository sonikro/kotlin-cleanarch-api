package routes

import database.UserRepositoryInMemory
import domain.entity.User
import model.user.*
import org.http4k.contract.ContractRoute
import org.http4k.contract.bindContract
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Gson.auto
import org.http4k.lens.Path
import org.http4k.lens.long
import usecase.invoke
import usecase.user.*


val userIdPath = Path.long().of("userId")
val getUsersLens = Body.auto<GetUsersResponse>().toLens()
val postUserRequestLens = Body.auto<PostUserRequest>().toLens()
val postUserResponseLens = Body.auto<PostUserResponse>().toLens()
val userDtoLens = Body.auto<UserDTO>().toLens()
val putUserRequestLens = Body.auto<PutUserRequest>().toLens()

fun userRoutes(): List<ContractRoute> {
    val userRepository = UserRepositoryInMemory()
    return listOf(
        "/user" bindContract Method.GET to { _ ->
            val users = listUsersUseCase(userRepository).invoke()
            val response = GetUsersResponse(users = users.map { it.toDto() })
            Response(OK)
                .with(getUsersLens of response)
        },
        "/user" bindContract Method.POST to { request ->
            val body = postUserRequestLens(request)
            val userId = createUserUseCase(userRepository).invoke(User(name = body.name))
            Response(CREATED)
                .with(postUserResponseLens of PostUserResponse(createdUserId = userId))
        },
        "/user/{userId}" bindContract Method.GET to { request ->
            val userId = userIdPath(request)
            val user = getUserUseCase(userRepository).invoke(userId)
            Response(OK)
                .with(userDtoLens of user.toDto())
        },
        "/user/{userId}" bindContract Method.PUT to { request ->
            val userId = userIdPath(request)
            val body = putUserRequestLens(request)
            updateUserUseCase(userRepository).invoke(User(id = userId, name = body.name))
            Response(OK)
        },
        "/user/{userId}" bindContract Method.DELETE to { request ->
            val userId = userIdPath(request)
            val user = deleteUserUseCase(userRepository).invoke(userId)
            Response(OK)
                .with(userDtoLens of user.toDto())
        }
    )
}