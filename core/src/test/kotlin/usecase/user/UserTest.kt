package usecase.user

import domain.entity.User
import domain.exception.BusinessException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import usecase.repository.UserRepository
import kotlin.test.assertFailsWith
import kotlin.test.expect

class UserTest {
    @Test
    fun `createUser use case returns new user ID`() {
        // Given
        val user = User(name = "Jonathan Nagayoshi")
        val userRepository = mockk<UserRepository>()
        every { userRepository.createUser(user) } returns 1L
        //When
        val userId = createUserUseCase(userRepository).invoke(user)
        //Then
        expect(userId, "to be equal") { 1L }
        verify(exactly = 1) { userRepository.createUser(user) }
    }

    @Test
    fun `createUser returns error when user name is invalid`() {
        // Given
        val user = User(name = "AB")
        val userRepository = mockk<UserRepository>()
        every { userRepository.createUser(user) } returns 1L
        assertFailsWith<BusinessException> {
            val userId = createUserUseCase(userRepository).invoke(user)

        }
    }
}
