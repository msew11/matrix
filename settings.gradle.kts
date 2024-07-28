pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
rootProject.name = "Matrix"
include("game-protocol")
include("game-common")
include("game-server-gate")
include("game-client")
include("game-server-home")
