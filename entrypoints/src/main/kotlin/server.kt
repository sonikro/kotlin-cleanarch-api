import configuration.setup
import io.javalin.Javalin
import routes.userRoutes

fun main() {
    val app = Javalin
        .create { it.setup() }
        .apply {
            userRoutes()
        }
        .start(8080)
}