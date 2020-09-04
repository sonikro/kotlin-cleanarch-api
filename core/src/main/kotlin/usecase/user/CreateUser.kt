package usecase.user

import domain.entity.User
import domain.entity.validate
import usecase.UseCase
import usecase.repository.UserRepository

fun createUserUseCase(
    userRepository: UserRepository
) = UseCase<User, Long> { user ->
    user.validate();
    userRepository.createUser(user)
}
