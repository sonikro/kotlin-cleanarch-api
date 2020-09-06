package controller

import domain.entity.User
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag
import model.user.*
import usecase.invoke
import usecase.repository.UserRepository
import usecase.user.*

@Tag(name = "User")
@Controller("/user")
class UserController(
    private val userRepository: UserRepository
) {

    @Get
    fun getAllUsers(): GetUsersResponse {
        return listUsersUseCase(userRepository)
            .invoke()
            .let { users ->
                GetUsersResponse(users = users.map { it.toDto() })
            }

    }

    @Get("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): UserDTO {
        return getUserUseCase(userRepository)
            .invoke(userId)
            .toDto()
    }

    @Post
    fun createUser(@Body body: PostUserRequest): PostUserResponse {
        return createUserUseCase(userRepository)
            .invoke(User(name = body.name))
            .let { createdUserId ->
                PostUserResponse(createdUserId)
            }
    }

    @Put("/{userId}")
    fun updateUser(@Body body: PutUserRequest, @PathVariable("userId") userId: Long) {
        updateUserUseCase(userRepository).invoke(User(name = body.name, id = userId))
    }

    @Delete("/{userId}")
    fun deleteUser(@PathVariable("userId") userId: Long): UserDTO {
        return deleteUserUseCase(userRepository).invoke(userId).toDto()
    }

}