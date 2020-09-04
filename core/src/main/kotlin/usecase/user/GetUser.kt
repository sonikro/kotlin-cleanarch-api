package usecase.user

import domain.entity.User
import usecase.UseCase
import usecase.repository.UserRepository

fun getUserUseCase(userRepository: UserRepository) = UseCase<Long, User> {
    userRepository.getUser(it)
}