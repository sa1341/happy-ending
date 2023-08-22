dependencies {
    api(project(":support:model"))
    implementation(project(":support:utils"))
    implementation(project(":support:logging"))
    testImplementation(testFixtures(project(":support:testkit")))
}
