package usecase.user

import domain.entity.User
import usecase.UseCase
import usecase.repository.UserRepository

fun listUsersUseCase(userRepository: UserRepository) = UseCase<Unit, List<User>> {
    userRepository.listUsers()
}
