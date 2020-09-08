import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "kotlin-cleanarch-api",
        version = "0.0.1"
    )
)
object Api {
}

fun main(args: Array<String>) {
    Micronaut.build()
        .args(*args)
        .packages("com.sonikro")
        .start()
}