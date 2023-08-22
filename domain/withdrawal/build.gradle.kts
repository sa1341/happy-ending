apply {
    plugin("java-test-fixtures")
}

dependencies {
    implementation(project(":infra:mysql"))
    implementation(project(":client:securities-client"))
    implementation(project(":support:utils"))
    implementation(project(":infra:mysql"))

    testImplementation(project(":support:testkit"))
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
