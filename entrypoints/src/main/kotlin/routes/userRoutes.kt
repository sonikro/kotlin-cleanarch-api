package routes

import database.UserRepositoryInMemory
import domain.entity.User
import model.user.*
import org.http4k.contract.ContractRoute
import org.http4k.contract.meta
import org.http4k.core.*
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.long
import usecase.invoke
import usecase.repository.UserRepository
import usecase.user.*

val sampleUserDto = UserDTO(id = 1, name = "Sample User")

val userIdPath = Path.long().of("userId")
val getUsersLens = Body.auto<GetUsersResponse>().toLens()
val postUserRequestLens = Body.auto<PostUserRequest>().toLens()
val postUserResponseLens = Body.auto<PostUserResponse>().toLens()
val userDtoLens = Body.auto<UserDTO>().toLens()
val putUserRequestLens = Body.auto<PutUserRequest>().toLens()


fun listAll(userRepository: UserRepository): HttpHandler = {
    val users = listUsersUseCase(userRepository).invoke()
    val response = GetUsersResponse(users = users.map { it.toDto() })
    Response(OK)
        .with(getUsersLens of response)
}

fun create(userRepository: UserRepository): HttpHandler = { request ->
    val body = postUserRequestLens(request)
    val userId = createUserUseCase(userRepository).invoke(User(name = body.name))
    Response(CREATED)
        .with(postUserResponseLens of PostUserResponse(createdUserId = userId))
}

fun getOne(userRepository: UserRepository): HttpHandler = { request ->
    val userId = userIdPath(request)
    val user = getUserUseCase(userRepository).invoke(userId)
    Response(OK)
        .with(userDtoLens of user.toDto())
}

fun update(userRepository: UserRepository): HttpHandler = { request ->
    val userId = userIdPath(request)
    val body = putUserRequestLens(request)
    updateUserUseCase(userRepository).invoke(User(id = userId, name = body.name))
    Response(OK)
}

fun delete(userRepository: UserRepository): HttpHandler = { request ->
    val userId = userIdPath(request)
    val user = deleteUserUseCase(userRepository).invoke(userId)
    Response(OK)
        .with(userDtoLens of user.toDto())
}

fun userRoutes(): List<ContractRoute> {
    val userRepository = UserRepositoryInMemory()
    return listOf(
        "/user" meta {
            summary = "List all users"
            returning(OK, getUsersLens to GetUsersResponse(users = listOf(sampleUserDto)))
        } bindContract Method.GET to listAll(userRepository),

        "/user" meta {
            summary = "Create user"
            receiving(postUserRequestLens to PostUserRequest(name = "New username"))
            returning(CREATED, postUserResponseLens to PostUserResponse(createdUserId = 1L))
        } bindContract Method.POST to create(userRepository),

        "/user/{userId}" meta {
            summary = "Fetch single user"
            returning(OK, userDtoLens to sampleUserDto)
        } bindContract Method.GET to getOne(userRepository),

        "/user/{userId}" meta {
            summary = "Update user"
            receiving(putUserRequestLens to PutUserRequest(name = "New name"))
            returning(OK)
        } bindContract Method.PUT to update(userRepository),

        "/user/{userId}" meta {
            summary = "Delete user"
            returning(OK, userDtoLens to sampleUserDto)
        } bindContract Method.DELETE to delete(userRepository)
    )
}