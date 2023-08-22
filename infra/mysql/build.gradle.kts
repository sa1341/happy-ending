plugins {
    kotlin("plugin.jpa")
    id("java-test-fixtures")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(project(":support:utils"))
    implementation(project(":support:logging"))
    implementation("com.h2database:h2")
    implementation("com.mysql:mysql-connector-j")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    testImplementation("com.h2database:h2")
    testFixturesImplementation((project(":support:utils")))
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
