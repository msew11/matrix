import config.Versions

plugins {
    kotlin("plugin.noarg")
}

dependencies {
    api("org.matrix.game:game-protocol:1.0-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-noarg")

    api(platform("com.typesafe.akka:akka-bom_${Versions.SCALA_BINARY}:${Versions.AKKA_VERSION}"))
    api("com.typesafe.akka:akka-actor_${Versions.SCALA_BINARY}")
    api("com.typesafe.akka:akka-cluster-sharding_${Versions.SCALA_BINARY}")
    api("com.typesafe.akka:akka-serialization-jackson_${Versions.SCALA_BINARY}")
    implementation("com.typesafe.akka:akka-slf4j_${Versions.SCALA_BINARY}")

    api("io.netty:netty-all:${Versions.NETTY_VERSION}")

    implementation("com.mysql:mysql-connector-j:${Versions.MYSQL_CONNECTOR_VERSION}")
    implementation("org.hibernate:hibernate-core:${Versions.HIBERNATE_VERSION}")


    testImplementation(kotlin("test"))
}

noArg {
    annotation("org.matrix.game.common.akka.NoArg")
    invokeInitializers = true
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}