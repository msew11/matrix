pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
rootProject.name = "matrix"

include("game-core")
include("game-common")
include("game-protocol")

include("game-client")

include("game-server-gate")
include("game-server-home")
