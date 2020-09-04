import configuration.configContentNegotiation
import configuration.configDI
import configuration.configExceptionHandling
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import routes.userRoutes

fun main() {
    val server = embeddedServer(Netty, 8080) {
        configDI()
        configContentNegotiation()
        configExceptionHandling()
        routing {
            userRoutes()
        }
    }
    server.start(wait = true)
}