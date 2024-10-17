import org.gradle.api.JavaVersion

object Versions {
    // language
    val java = JavaVersion.toVersion("21")
    const val KOTLIN_VERSION = "2.0.10"

    // spring
    const val SPRING_BOOT_VERSION = "3.3.4"
    const val SPRING_DEPENDENCY_MANAGEMENT_VERSION = "1.1.3"
    const val SPRING_CLOUD_VERSION = "2022.0.4"
    const val SPRING_CLOUD_STREAM = "4.0.4"

    // logging & monirtoring
    const val KOTLIN_LOGGING = "3.0.5"
    const val SENTRY_LOGBACK_VERSION = "6.3.1"

    // util
    const val KOVER_VERSION = "0.6.1"
    const val KT_LINT_VERSION = "11.6.1"
    const val ASCII_DOCTOR_VERSION = "3.3.2"
    const val COMMONS_LANG3 = "3.12.0"
    const val COMMONS_IO = "2.13.0"

    const val RESILIENCE4J_VERSION = "2.0.2"

    // test
    const val KOTEST_VERSION = "5.6.2"
    const val SPRING_MOCK_VERSION = "4.0.2"
    const val MOCKK_VERSION = "1.13.5"
    const val FIXTURE_MONKEY_VERSION = "1.0.4"
    const val KOTEST_EXTENSION_SPRING_VERSION = "1.1.2"
}
