package usecase

fun interface UseCase<Input, Output> {
    fun invoke(input: Input): Output
}

//Helper extension function to avoid having to pass Unit
fun <R> UseCase<Unit, R>.invoke(): R = this.invoke(Unit)
