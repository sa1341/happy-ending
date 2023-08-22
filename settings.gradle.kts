rootProject.name = "happy-ending"

include(
    ":app:happy-ending-api",
    ":app:happy-ending-batch"
)

include(":client:securities-client")
include(
    ":domain:account",
    ":domain:withdrawal"
)
include(
    ":infra:mysql",
    ":infra:redis",
    ":infra:kafka"
)
include(
    ":support:metric",
    ":support:model",
    ":support:logging",
    ":support:utils",
    ":support:testkit",
    ":support:yaml-importer"
)
