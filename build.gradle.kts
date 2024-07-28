import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.24" apply false

    kotlin("plugin.noarg") version "1.9.24" apply false

    id("org.springframework.boot") version "3.3.0" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
    kotlin("plugin.spring") version "1.9.24" apply false

    id("com.google.protobuf") version "0.9.4" apply false


}

subprojects {
    group = "org.matrix.game"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://repo.akka.io/maven") }

        mavenCentral()
    }

    dependencies {
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
        implementation("ch.qos.logback:logback-classic:1.5.6")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }
}
