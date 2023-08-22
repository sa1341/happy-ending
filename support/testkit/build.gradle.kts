plugins {
    id("java-test-fixtures")
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.6.2")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testFixturesImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testFixturesImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testFixturesImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testFixturesImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testFixturesImplementation("com.github.tomakehurst:wiremock:3.0.0-beta-10")
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
