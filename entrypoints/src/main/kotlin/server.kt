import configuration.swaggerUi
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.format.Gson
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import routes.userRoutes

fun main() {
    val swaggerDescriptionPath = "/api/swagger.json"
    val contractHandlers = contract {
        renderer = OpenApi3(ApiInfo("kotlin-cleanarch-api", "1.0.0", "API description"), Gson)
        descriptionPath = swaggerDescriptionPath
        routes += userRoutes()
    }
    routes(
        contractHandlers,
        swaggerUi(swaggerDescriptionPath)
    )
        .asServer(Jetty(8080))
        .start()

}