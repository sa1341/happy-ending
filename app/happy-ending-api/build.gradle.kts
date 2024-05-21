plugins {
    // id("org.asciidoctor.jvm.convert") version Versions.ASCII_DOCTOR_VERSION
    id("com.epages.restdocs-api-spec") version "0.18.4"
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
    // implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    implementation(testFixtures(project(":support:testkit")))

    // Rest Assured
    testImplementation("io.rest-assured:rest-assured:5.3.1")

    // Rest Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    testImplementation("com.epages:restdocs-api-spec-restassured:0.18.4")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.4")
}

openapi3 {
    this.setServer("http://localhost:8080") // list로 넣을 수 있어 각종 환경의 URL을 넣을 수 있음!
    title = "My API"
    description = "My API description"
    version = "0.1.0"
    format = "yaml" // or json
}

tasks.register<Copy>("copyOasToSwagger") {
    delete("src/main/resources/static/swagger-ui/openapi3.yml") // 기본 OAS 파일
    from("$buildDir/api-spec/openapi3.yaml") // 복제할 OAS 파일 지정
    into("BOOT-INF/classes/static/swagger-ui/.") // 타겟 디렉터리로 파일 복제
    // into("src/main/resources/static/swagger-ui/.") // 타겟(로컬에서 호출 시에) 디렉터리로 파일 복제
    dependsOn("openapi3") // openapi3 Task가 먼저 실행되도록 설정
}
