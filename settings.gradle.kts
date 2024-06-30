plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ReBorn"
include("game-protocol")
include("game-common")
include("game-server-gateway")
include("game-client")
include("game-server-home")
