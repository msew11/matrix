import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.24" apply false
}

allprojects {
    apply(plugin = "java")

    group = "org.matrix.game"
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
        implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }
}
