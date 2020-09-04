package configuration

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import java.text.DateFormat

fun Application.configContentNegotiation() {
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
}