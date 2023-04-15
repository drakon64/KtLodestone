plugins {
    kotlin("jvm") version "1.8.20"

    id("org.jetbrains.dokka") version "1.8.10"

    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    signing

    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "4.0.0.2929"
}

group = "cloud.drakon"
version = "3.1.0"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.2.4"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")

    implementation("org.jsoup:jsoup:1.15.4")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

tasks.dokkaJekyll.configure {
    outputDirectory.set(buildDir.resolve("dokka"))

    dokkaSourceSets {
        configureEach {
            jdkVersion.set(11)
        }
    }
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(javadocJar.get())
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
