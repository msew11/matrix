plugins {
    kotlin("jvm") version "1.9.23"
    id("java")
    id("com.google.protobuf") version "0.9.4"
}

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
    implementation("io.netty:netty-all:4.1.109.Final")
    implementation("com.google.protobuf:protobuf-javalite:3.21.7")
    //implementation("com.google.protobuf:protobuf-kotlin:3.21.7")
    testImplementation(kotlin("test"))

    // protobuf(files("proto/"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.7"
    }
    generateProtoTasks {
        all().forEach { task ->

            println("outputBaseDir=${task.outputBaseDir}")
            println("outputBaseDir=${task.sourceSet}")

            task.builtins {
                getByName("java") {
                    option("lite")
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}