plugins {
    kotlin("jvm") version "1.4.0"
}
dependencies {
    implementation(project(":core"))
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}