import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    application
}
group = "com.sonikro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/kotlin/ktor")
    }
}
dependencies {
    implementation(project(":core"))
    implementation(project(":providers"))

    //HTTP Library
    implementation(platform("org.http4k:http4k-bom:3.260.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-contract")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-jackson")

    //Swagger UI
    implementation("org.webjars:swagger-ui:3.25.2")

    //Logs
    implementation("org.slf4j:slf4j-simple:1.7.30")
    //Tests
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "ServerKt"
}