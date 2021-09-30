plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version (Versions.kotlin)

    id("com.adarshr.test-logger") version (PluginVersions.gradle_test_logger)
    id("idea")
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version (PluginVersions.ktlint)
    id("nebula.release") version "15.3.1"
    id("signing")
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}")
}

group = PluginConstants.groupId
val releasedVersion = PluginConstants.getVersion()
project.extra.set("releasedVersion", releasedVersion)

if (project.hasProperty("sonatypeUsername") && project.hasProperty("public")) {
    publishing {
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])

                groupId = PluginConstants.groupId
                artifactId = PluginConstants.artifactId
                version = version

                pom {
                    name.set("Integration Server Gradle Plugin")
                    description.set("The easy way to get custom setup for Deploy up and running")
                    url.set(PluginConstants.repositoryUrl)
                    licenses {
                        license {
                            name.set("GPLv2 with Digital.ai FLOSS License Exception")
                            url.set(PluginConstants.repositoryLicense)
                        }
                    }

                    scm {
                        url.set(PluginConstants.repositoryScm)
                    }

                    developers {
                        developer {
                            id.set("bnechyporenko")
                            name.set("Bogdan Nechyporenko")
                            email.set("bnechyporenko@digital.ai")
                        }
                        developer {
                            id.set("aalbul")
                            name.set("Alexander Albul")
                            email.set("aalbul@digital.ai")
                        }
                        developer {
                            id.set("sishwarya")
                            name.set("Ishwarya Surendrababu")
                            email.set("sishwarya@digital.ai")
                        }
                        developer {
                            id.set("tonac")
                            name.set("Antonio Sostar")
                            email.set("asostar@digital.ai")
                        }
                    }
                }
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
            }
        }
        repositories {
            maven {
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = project.property("sonatypeUsername").toString()
                    password = project.property("sonatypePassword").toString()
                }
            }
            maven {
                url = uri("https://oss.sonatype.org/content/repositories/snapshots")
                credentials {
                    username = project.property("sonatypeUsername").toString()
                    password = project.property("sonatypePassword").toString()
                }
            }
        }
    }
} else {
    publishing {
        publications {
            create<MavenPublication>("myLibrary") {
                from(components["java"])
            }
        }
        repositories {
            maven {
                url = uri("${project.property("nexusBaseUrl")}/repositories/releases")
                credentials {
                    username = project.property("nexusUserName").toString()
                    password = project.property("nexusPassword").toString()
                }
            }
        }
    }
}

if (project.hasProperty("sonatypeUsername") && project.hasProperty("public")) {
    signing {
        sign(publishing.publications["mavenJava"])
    }

    nexusPublishing {
        repositories {
            sonatype {
                username.set(project.property("sonatypeUsername").toString())
                password.set(project.property("sonatypePassword").toString())
            }
        }
    }
}

tasks {
    register<NebulaRelease>("nebulaRelease")

    register("dumpVersion") {
        file(buildDir).mkdirs()
        file("$buildDir/version.dump").writeText("version=$project.version")
    }
}
