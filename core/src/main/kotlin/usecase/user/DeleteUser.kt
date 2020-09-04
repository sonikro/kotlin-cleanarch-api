package usecase.user

import domain.entity.User
import usecase.UseCase
import usecase.repository.UserRepository


fun deleteUserUseCase(userRepository: UserRepository) = UseCase<Long, User> { userId ->
    userRepository.deleteUser(userId)
}