package configuration

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static

fun swaggerUi(descriptionPath: String): RoutingHttpHandler = routes(
    "docs" bind Method.GET to {
        Response(Status.FOUND).header("Location", "/docs/index.html?url=$descriptionPath")
    },
    // For some reason the static handler does not work without "/" path prefix.
    "/docs" bind static(Classpath("META-INF/resources/webjars/swagger-ui/3.25.2"))
)