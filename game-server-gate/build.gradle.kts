plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
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