import database.UserRepositoryInMemory
import domain.entity.User
import org.junit.Test
import kotlin.test.expect

class UserRepositoryInMemoryTest {
    @Test
    fun `userRepository persists user`() {
        //Given
        val repository = UserRepositoryInMemory()
        val user = User(name = "Jonathan Nagayoshi")
        //When
        val createUserId = repository.createUser(user)
        //Then
        expect(0L) { createUserId }
    }

    @Test
    fun `userRepository updates user`() {
        //Given
        val repository = UserRepositoryInMemory()
        val user = User(name = "Jonathan Nagayoshi")
        //When
        val createUserId = repository.createUser(user)
        val changedUser = user.copy(name = "Maradona", id = createUserId)
        repository.updateUser(changedUser)
        val updatedUser = repository.getUser(createUserId)
        //Then
        expect(0L) { updatedUser.id }
        expect("Maradona") { updatedUser.name }
    }
}