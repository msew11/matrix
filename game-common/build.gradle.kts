plugins {
    kotlin("jvm") version "1.9.24"
}

dependencies {
    implementation("io.netty:netty-all:4.1.109.Final")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}