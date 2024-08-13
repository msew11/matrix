import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":game-common"))
    testImplementation("org.springframework.boot:spring-boot-starter")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
