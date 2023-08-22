dependencies {
    implementation(project(":support:utils"))
    implementation("io.sentry:sentry-logback:6.17.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    compileOnly("org.apache.tomcat.embed:tomcat-embed-core")
    implementation("org.springframework:spring-webmvc")
    implementation("commons-io:commons-io:2.11.0")
}

tasks {
    bootJar { enabled = false }
    jar { enabled = true }
}
