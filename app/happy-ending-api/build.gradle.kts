plugins {
    id("org.asciidoctor.jvm.convert") version Versions.ASCII_DOCTOR_VERSION
}

dependencyManagement {
    dependencies {
        dependencySet("io.github.resilience4j:2.0.2") {
            entry("resilience4j-spring-boot3")
        }
    }
}

dependencies {
    implementation(project(":domain:account"))
    implementation(project(":domain:withdrawal"))
    implementation(project(":client:securities-client"))
    implementation(project(":infra:db"))
    implementation(project(":infra:redis"))
    implementation(project(":infra:kafka"))
    implementation(project(":support:metric"))
    implementation(project(":support:utils"))

    implementation("io.github.resilience4j:resilience4j-spring-boot3")

    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    implementation(testFixtures(project(":support:testkit")))
}

val snippetsDir by extra { file("$buildDir/generated-snippets") }

tasks {

    val branch = System.getenv()["GIT_BRANCH"]
    asciidoctor {
        onlyIf { branch?.startsWith("develop") ?: false }
        dependsOn(test)
        inputs.dir(snippetsDir)
        attributes(
            // src/docs/asciidoc/index.adoc 에서 사용할 snippets 변수 설정
            mapOf("snippets" to snippetsDir)
        )
    }

    bootJar {
        dependsOn(asciidoctor)

        from("${asciidoctor.get().outputDir}") {
            into("BOOT-INF/classes/static/docs")
        }
    }
}
