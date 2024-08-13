buildscript {
}

plugins {
    kotlin("plugin.noarg")
}

dependencies {
    // api("org.matrix.game:game-core:1.0.6")
    api(project(":game-core"))
    api("org.matrix.game:game-protocol:1.0.5")

    implementation("org.jetbrains.kotlin:kotlin-noarg")

    testImplementation(kotlin("test"))
}

noArg {
    annotation("org.matrix.game.common.base.NoArg")
    invokeInitializers = true
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}