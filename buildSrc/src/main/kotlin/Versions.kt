import org.gradle.api.JavaVersion

object Versions {
    // spring
    const val SPRING_BOOT_VERSION = "3.1.0"
    const val SPRING_DEPENDENCY_MANAGEMENT_VERSION = "1.1.0"
    const val SPRING_CLOUD = "2022.0.3"

    const val KT_LINT_VERSION = "11.1.0"
    const val ASCII_DOCTOR_VERSION = "3.3.2"

    // language
    val java = JavaVersion.VERSION_17
    const val KOTLIN = "1.7.22"

    const val COMMONS_LANG3 = "3.12.0"
    const val JACKSON = "2.13.2"
    const val KOTLIN_LOGGING = "2.1.21"
    const val SENTRY_LOGGING = "6.3.1"

    const val RESILIENCE4J_VERSION = "2.0.2"

    const val KOTEST_RUNNER_JUNIT5 = "5.3.0"
    const val KOTEST_VERSION = "5.5.4"
    const val KOTEST_EXTENSION_SPRING_VERSION = "1.1.2"

    const val AWS = "3.0.0-RC1"
    const val AWS_SECRETS_MANAGER_CONFIG = "3.0.0-RC1"
    const val AWS_SDK_STS = "2.20.22"
}
