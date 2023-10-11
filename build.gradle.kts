plugins {
    kotlin("jvm") version "1.9.10"

    id("org.jetbrains.dokka") version "1.9.0"

    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    signing

    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("org.sonarqube") version "4.4.1.3373"
}

group = "cloud.drakon"
version = "7.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.3.5"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")

    implementation("org.jsoup:jsoup:1.16.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

kover {
    useJacoco()
}

sonarqube {
    properties {
        property("sonar.projectKey", "KtLodestone")
        property("sonar.organization", "drakon64")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml"
        )
    }
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("ktlodestone")
                description.set("Kotlin/JVM parser for The Lodestone")
                url.set("https://github.com/drakon64/KtLodestone")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/license/MIT/")
                    }
                }

                developers {
                    developer {
                        id.set("drakon64")
                        name.set("Adam Chance")
                        email.set("6444703+drakon64@users.noreply.github.com")
                    }
                }

                scm {
                    connection.set("https://github.com/drakon64/KtLodestone.git")
                    developerConnection.set("git@github.com:drakon64/KtLodestone.git")
                    url.set("https://github.com/drakon64/KtLodestone")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(System.getenv("SONATYPE_USERNAME"))
            password.set(System.getenv("SONATYPE_PASSWORD"))
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}
