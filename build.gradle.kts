plugins {
    id("org.springframework.boot") version Versions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version Versions.SPRING_DEPENDENCY_MANAGEMENT_VERSION
    id("org.jlleitschuh.gradle.ktlint") version Versions.KT_LINT_VERSION
    id("org.jlleitschuh.gradle.ktlint-idea") version Versions.KT_LINT_VERSION
    id("java-test-fixtures")
    id("org.jetbrains.kotlinx.kover") version Versions.KOVER_VERSION
    kotlin("jvm") version Versions.KOTLIN
    kotlin("kapt") version Versions.KOTLIN
    kotlin("plugin.spring") version Versions.KOTLIN
    kotlin("plugin.jpa") version Versions.KOTLIN
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.kakaopaysec.happy-ending"
    version = "1.0.0"
}

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("idea")
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlinx.kover")
    }

    group = "com.kakaopaysec.happy-ending"
    version = "1.0.0"
    java.sourceCompatibility = Versions.java
    extra["springCloudVersion"] = "2022.0.3"

    dependencyManagement {
        dependencies {
            dependencySet("io.github.resilience4j:2.0.2") {
                entry("resilience4j-spring-boot3")
            }
        }

        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    val isMacOS: Boolean = System.getProperty("os.name").startsWith("Mac OS X")
    val architecture = System.getProperty("os.arch").toLowerCase()

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.cloud:spring-cloud-stream")
        implementation("io.github.resilience4j:resilience4j-spring-boot3")
        implementation("io.github.resilience4j:resilience4j-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.github.microutils:kotlin-logging:${Versions.KOTLIN_LOGGING}")
        if (isMacOS && architecture == "aarch64") {
            implementation("io.netty:netty-resolver-dns-native-macos:4.1.79.Final:osx-aarch_64")
        }

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation("io.mockk:mockk:1.13.5")
        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
    }

    tasks {
        test {
            useJUnitPlatform()
            finalizedBy(koverVerify)
        }

        koverHtmlReport {
            kover {
                htmlReport {
                    reportDir.set(file("$buildDir/report/kover/kover.html"))
                }
            }
            finalizedBy(koverVerify)
        }

        koverVerify {
            kover {
                verify {
                    rule {
                        bound {
                            minValue = BigInteger.valueOf(
                                when (project.name) {
                                    "happy-ending-api" -> 60
                                    else -> 0
                                }
                            ).toInt()
                            maxValue = 100
                            // coverage type
                            counter = kotlinx.kover.api.CounterType.LINE
                            // value type
                            valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE
                        }
                    }
                }
            }
        }

        compileKotlin {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }

        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "17"
            }
        }
    }
}
