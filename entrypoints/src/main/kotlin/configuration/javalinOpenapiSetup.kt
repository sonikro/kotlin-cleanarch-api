package configuration

import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.ui.ReDocOptions
import io.swagger.v3.oas.models.info.Info

fun getOpenApiOptions(): OpenApiOptions {
    return OpenApiOptions(Info().apply {
        version = "1.0.0"
        description = "kotlin-cleanarch-api"
    })
        .path("/swagger-docs")
        .reDoc(ReDocOptions("/redoc").title("Kotlin API Documentation"))
}