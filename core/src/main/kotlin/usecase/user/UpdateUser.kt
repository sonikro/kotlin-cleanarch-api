package usecase.user

import domain.entity.User
import domain.entity.validate
import usecase.UseCase
import usecase.repository.UserRepository

fun updateUserUseCase(userRepository: UserRepository) = UseCase<User, Unit> { user ->
    user.validate()
    userRepository.updateUser(user)
}