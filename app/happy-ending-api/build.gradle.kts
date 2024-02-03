plugins {
    id("org.asciidoctor.jvm.convert") version Versions.ASCII_DOCTOR_VERSION
}

dependencies {
    implementation(project(":domain:account"))
    implementation(project(":domain:withdrawal"))
    implementation(project(":client:securities-client"))
    implementation(project(":infra:mysql"))
    implementation(project(":infra:redis"))
    implementation(project(":infra:kafka"))
    implementation(project(":support:metric"))
    implementation(project(":support:logging"))
    implementation(project(":support:utils"))
    implementation(project(":support:model"))
    implementation(project(":support:yaml-importer"))
    implementation("commons-io:commons-io:2.13.0")
    //implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    implementation(testFixtures(project(":support:testkit")))

    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
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
