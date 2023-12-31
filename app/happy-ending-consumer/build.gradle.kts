dependencies {
    implementation(project(":support:metric"))
    implementation(project(":support:utils"))
    implementation(project(":support:model"))
    implementation(project(":support:logging"))
    implementation(project(":support:yaml-importer"))

    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("org.apache.kafka:kafka-streams:3.3.1")
}
