rootProject.name = "happy-ending"

include(":app:happy-ending-api")
include(":client:securities-client")
include(
    ":domain:account",
    ":domain:withdrawal"
)
include(
    ":infra:db",
    ":infra:redis",
    ":infra:kafka"
)
include(
    ":support:metric",
    ":support:utils",
    ":support:testkit"
)
