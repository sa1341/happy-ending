plugins {
    id("org.springframework.boot") version Versions.SPRING_BOOT_VERSION apply false
    id("io.spring.dependency-management") version Versions.SPRING_DEPENDENCY_MANAGEMENT_VERSION
    kotlin("jvm") version Versions.KOTLIN
    kotlin("plugin.spring") version Versions.KOTLIN apply false
    kotlin("plugin.jpa") version Versions.KOTLIN apply false
    kotlin("kapt") version Versions.KOTLIN
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.kakaopaysec.happy-ending"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "com.kakaopaysec.happy-ending"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = Versions.java
    extra["springCloudVersion"] = "2022.0.3"

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
      /*  implementation("io.awspring.cloud:spring-cloud-aws-starter:${Versions.AWS}")
        implementation(
            "io.awspring.cloud:spring-cloud-aws-starter-secrets-manager:" +
                Versions.AWS_SECRETS_MANAGER_CONFIG
        )*/
        implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.3")
        implementation("org.springframework.cloud:spring-cloud-starter-aws-secrets-manager-config:2.2.6.RELEASE")
        implementation("software.amazon.awssdk:sts:${Versions.AWS_SDK_STS}")
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
