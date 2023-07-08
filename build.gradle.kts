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
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlinx.kover")
    }

    group = "com.kakaopaysec.happy-ending"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = Versions.java
    extra["springCloudVersion"] = "2022.0.1"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.awspring.cloud:spring-cloud-aws-starter:${Versions.AWS}")
        implementation(
            "io.awspring.cloud:spring-cloud-aws-starter-secrets-manager:" +
                Versions.AWS_SECRETS_MANAGER_CONFIG
        )
        implementation("software.amazon.awssdk:sts:${Versions.AWS_SDK_STS}")
        implementation("io.github.microutils:kotlin-logging:${Versions.KOTLIN_LOGGING}")
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
