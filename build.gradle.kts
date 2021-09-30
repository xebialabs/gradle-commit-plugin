plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version (Versions.kotlin)

    id("idea")
    id("org.jlleitschuh.gradle.ktlint") version (PluginVersions.ktlint)
    id("com.adarshr.test-logger") version (PluginVersions.gradle_test_logger)
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
version = PluginConstants.getVersion()

if (project.hasProperty("sonatypeUsername") && project.hasProperty("public")) {
    publishing {
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])

                groupId = "com.xebialabs.gradle.plugins"
                artifactId = "integration-server-gradle-plugin"
                version = version

                pom {
                    name.set("Integration Server Gradle Plugin")
                    description.set("The easy way to get custom setup for Deploy up and running")
                    url.set("https://github.com/xebialabs/integration-server-gradle-plugin.git")
                    licenses {
                        license {
                            name.set("GPLv2 with Digital.ai FLOSS License Exception")
                            url.set("https://github.com/xebialabs/integration-server-gradle-plugin/blob/master/LICENSE")
                        }
                    }

                    scm {
                        url.set("https://github.com/xebialabs/integration-server-gradle-plugin")
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
    project.afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("mavenPublish") {
                    groupId = PluginConstants.groupId
                    artifactId = PluginConstants.artifactId
                    version = version

                    pom {
                        name.set(PluginConstants.displayName)
                        description.set(PluginConstants.description)
                        inceptionYear.set("2020")
                        url.set(PluginConstants.repositoryUrl)
                        developers {
                            developer {
                                name.set("Bogdan Nechyporenko")
                                id.set("acierto")
                            }
                        }
                        licenses {
                            license {
                                name.set("MIT")
                                url.set("https://opensource.org/licenses/MIT")
                            }
                        }
                    }
                }
            }
        }
    }
}
