package configuration

import io.javalin.core.JavalinConfig
import io.javalin.plugin.openapi.OpenApiPlugin

fun JavalinConfig.setup() {
    enableCorsForAllOrigins()
    showJavalinBanner = false
    registerPlugin(OpenApiPlugin(getOpenApiOptions()))
}