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
    implementation("io.javalin:javalin:3.10.1")
    implementation("io.javalin:javalin-openapi:3.10.1")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "ServerKt"
}