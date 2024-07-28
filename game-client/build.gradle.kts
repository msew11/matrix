import config.Versions

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":game-common"))

    implementation("org.springframework.boot:spring-boot-starter")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}