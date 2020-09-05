import configuration.swaggerUi
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.filter.CorsPolicy
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import routes.userRoutes

fun main() {
    val swaggerDescriptionPath = "/api/swagger.json"
    val contractHandlers = contract {
        renderer = OpenApi3(ApiInfo("kotlin-cleanarch-api", "1.0.0", "API description"), Jackson)
        descriptionPath = swaggerDescriptionPath
        routes += userRoutes()
    }

    DebuggingFilters
        .PrintRequestAndResponse()
        .then(ServerFilters.Cors(CorsPolicy(listOf("*"), listOf("*"), Method.values().toList())))
        .then(ServerFilters.CatchLensFailure)
        .then(
            routes(
                contractHandlers,
                swaggerUi(swaggerDescriptionPath)
            )
        )
        .asServer(Jetty(8080))
        .start()
}