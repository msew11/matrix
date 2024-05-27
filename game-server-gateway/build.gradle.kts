plugins {
    java
    `java-library`
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

dependencies {
    api(project(":game-protocol"))
    api(project(":game-common"))

    implementation("org.springframework.boot:spring-boot-starter")
    //implementation("org.springframework.boot:spring-boot-starter-logging")
    // implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}