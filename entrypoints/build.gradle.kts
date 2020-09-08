import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("org.jetbrains.kotlin.kapt") version "1.3.72"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    application
}

val micronautVersion = "2.0.1"
val kotlinVersion = "1.3.72"

dependencies {
    //Modules
    implementation(project(":core"))
    implementation(project(":providers"))
    //External
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut:micronaut-graal")
    kapt("io.micronaut.configuration:micronaut-openapi")
    compileOnly(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    compileOnly("org.graalvm.nativeimage:svm")
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.xml:micronaut-jackson-xml")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    //Tests
    kaptTest(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "ServerKt"
}
allOpen {
    annotation("io.micronaut.aop.Around")
}

kapt {
    arguments {
        arg("micronaut.openapi.views.spec", "redoc.enabled=true")
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "com.sonikro.*")
        arg("micronaut.processing.group", "com.sonikro")
        arg("micronaut.processing.module", "entrypoints")
    }
}

tasks.withType<JavaExec> {
    jvmArgs("-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    if (gradle.startParameter.isContinuous) {
        systemProperties(
            "micronaut.io.watch.restart" to "true",
            "micronaut.io.watch.enabled" to "true",
            "micronaut.io.watch.paths" to "src/main"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ShadowJar>() {
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}