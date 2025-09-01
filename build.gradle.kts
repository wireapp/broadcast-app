import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "2.2.10"
    id("com.gradleup.shadow") version "9.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    application
}

group = "com.wire"
version = "1.0"

repositories {
    mavenCentral()
}
application {
    mainClass.set("com.wire.broadcastapp.ApplicationKt")
}

dependencies {
    implementation("com.wire", "wire-apps-jvm-sdk", "0.0.13")

    val exposedVersion = "1.0.0-beta-4"
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.postgresql", "postgresql", "42.7.3")

    testImplementation("io.mockk", "mockk", "1.14.5")
    testImplementation(kotlin("test"))
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}

detekt {
    toolVersion = "1.23.8"
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
    parallel = true
    buildUponDefaultConfig = true
    source.setFrom("src/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        archiveBaseName.set("broadcast-app")
        manifest {
            attributes["Main-Class"] = application.mainClass.get()
        }
    }
    build {
        dependsOn(shadowJar)
    }
}
