plugins {
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":support:model"))
    implementation(project(":support:utils"))
    implementation("org.apache.commons:commons-pool2:2.11.1")

    api("org.springframework.boot:spring-boot-starter-data-redis")

    testFixturesImplementation("io.github.microutils:kotlin-logging:1.5.9")
    testFixturesApi("it.ozimov:embedded-redis:0.7.3") {
        exclude("org.slf4j", "slf4j-simple")
    }
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testFixturesImplementation("io.mockk:mockk:1.10.0")
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
