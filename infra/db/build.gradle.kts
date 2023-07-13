dependencies {
    runtimeOnly("com.h2database:h2")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa")
    api(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    kapt("com.querydsl:querydsl-apt:4.2.1:jpa")
    implementation("mysql:mysql-connector-java:5.1.49")
}
