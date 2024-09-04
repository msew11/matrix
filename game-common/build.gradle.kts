buildscript {
}

plugins {
    `java-test-fixtures`
}

dependencies {
    // api("org.matrix.game:game-core:1.0.6")
    api(project(":game-core"))
    api("org.matrix.game:game-protocol:1.0.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}