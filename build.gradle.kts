plugins {
    kotlin("jvm") version "1.9.10"
}

group = "cloud.drakon"
version = "7.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
