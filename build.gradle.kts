import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.0"
    id("com.gradleup.shadow") version "9.4.2"
}

group = "net.malusic"
version = file("src/main/resources/paper-plugin.yml")
    .readLines()
    .first { it.startsWith("version:") }
    .substringAfter(":")
    .trim()
    .trim('"', '\'')

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("de.hexaoxi:carbonchat-api:3.0.0-beta.39")

    implementation("org.xerial:sqlite-jdbc:3.49.1.0") {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        archiveClassifier.set("plain")
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

if (file("dev.gradle.kts").exists()) apply(from = "dev.gradle.kts")
