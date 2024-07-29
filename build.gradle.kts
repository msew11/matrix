import config.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.lang.Runtime.Version

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
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://repo.akka.io/maven")

        mavenCentral()
    }

    dependencies {
        implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK_VERSION}")
        implementation("io.github.oshai:kotlin-logging-jvm:${Versions.KT_LOGGING_VERSION}")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }
}
