plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "plu"
include("plu-api")
include("plu-notification")
include("plu-domain")
include("plu-external")
include("plu-common")

