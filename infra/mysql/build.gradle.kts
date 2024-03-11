plugins {
    kotlin("plugin.jpa")
    id("java-test-fixtures")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(project(":support:utils"))
    implementation(project(":support:logging"))

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
