plugins {
    java
    `java-library`
    kotlin("jvm")
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("plugin.spring") version "1.9.24"
}

dependencies {
    implementation(project(":game-common"))
    api("org.springframework.boot:spring-boot-starter")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}