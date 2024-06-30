import config.Versions

plugins {
    kotlin("jvm") version "1.9.24"
}

dependencies {
    api(project(":game-protocol"))
    implementation("io.netty:netty-all:${Versions.NETTY_VERSION}")
    testImplementation(kotlin("test"))

    implementation(platform("com.typesafe.akka:akka-bom_${Versions.SCALA_BINARY}:${Versions.AKKA_VERSION}"))
    implementation("com.typesafe.akka:akka-actor-typed_${Versions.SCALA_BINARY}")
    testImplementation("com.typesafe.akka:akka-actor-testkit-typed_${Versions.SCALA_BINARY}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}