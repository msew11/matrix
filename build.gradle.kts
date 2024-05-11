plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.matrix"
version = "1.0-SNAPSHOT"

repositories {
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("https://maven.aliyun.com/repository/public") }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }

    google()
    mavenCentral()
    gradlePluginPortal()
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