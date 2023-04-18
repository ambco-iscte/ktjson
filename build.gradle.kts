import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.dokka") version "1.8.10"
    application
}

group = "iscte.ambco.pa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.antlr:antlr4:4.11.1")
    implementation("org.junit.platform:junit-platform-suite-engine:1.9.2")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.8.10")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<DokkaTask>().configureEach {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "(c) 2023 Afonso Manuel Barral Caniço"
    }

    // Suppress ANTLR-generated Java files
    dokkaSourceSets.configureEach {
        perPackageOption {
            matchingRegex.set("antlr")
            suppress.set(true)
        }
    }
}

tasks.dokkaHtml {
    outputDirectory.set(projectDir.resolve("docs"))
    moduleName.set("ktjson")
}

application {
    mainClass.set("MainKt")
}