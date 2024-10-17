dependencies {
    implementation(project(":infra:mysql"))
    implementation(project(":client:securities-client"))
    implementation(project(":support:utils"))
    implementation(project(":support:logging"))
    implementation(project(":support:metric"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    // implementation("org.springframework.cloud:spring-cloud-starter-vault-config")

    testImplementation("org.springframework.batch:spring-batch-test")
}
