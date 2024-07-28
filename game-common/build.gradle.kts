import config.Versions

plugins {
    kotlin("plugin.noarg")
}

dependencies {
    api(project(":game-protocol"))

    api("io.netty:netty-all:${Versions.NETTY_VERSION}")

    api(platform("com.typesafe.akka:akka-bom_${Versions.SCALA_BINARY}:${Versions.AKKA_VERSION}"))
    api("com.typesafe.akka:akka-actor_${Versions.SCALA_BINARY}")
    api("com.typesafe.akka:akka-cluster-sharding_${Versions.SCALA_BINARY}")
    api("com.typesafe.akka:akka-serialization-jackson_${Versions.SCALA_BINARY}")
    implementation("com.typesafe.akka:akka-slf4j_${Versions.SCALA_BINARY}")

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