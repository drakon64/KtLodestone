import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    val kotlinVersion = "1.8.21"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("org.jetbrains.dokka") version "1.8.10"

    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    signing

    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "4.0.0.2929"
}

group = "cloud.drakon"
version = "4.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.3.0"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")

    implementation("org.jsoup:jsoup:1.15.4")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val jvmToolchain = 11

kotlin {
    jvmToolchain(jvmToolchain)
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            jdkVersion.set(jvmToolchain)
            languageVersion.set("1.8")
        }
    }
}

val dokkaHtml by tasks.getting(DokkaTask::class)
val htmlJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaHtml)
    archiveClassifier.set("html-docs")
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(javadocJar.get())
            artifact(htmlJar.get())
            artifact(sourcesJar.get())

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
                    url.set("https://github.com/drakon64/KtLodestone")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/drakon64/KtLodestone")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
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
    sign(publishing.publications)
}

kover {
    engine.set(kotlinx.kover.api.DefaultJacocoEngine)
}

sonarqube {
    properties {
        property("sonar.projectKey", "KtLodestone")
        property("sonar.organization", "drakon64")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/xml/report.xml")
    }
}
