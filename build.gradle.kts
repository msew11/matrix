plugins {
    java
    kotlin("jvm") version "1.9.23" apply false
}

allprojects {

    group = "org.matrix.game"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }

        google()
        mavenCentral()
        gradlePluginPortal()
    }

    tasks {
        withType<JavaCompile>() {
            // 启用在单独的daemon进程中编译
            options.encoding = "UTF-8"
            options.isFork = true
            options.forkOptions.jvmArgs = listOf("-Xmx3g")
        }

        withType<Javadoc> {
            options.encoding = "UTF-8"
        }
    }

    /*dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("io.netty:netty-all:4.1.109.Final")
        //implementation("com.google.protobuf:protobuf-kotlin:3.21.7")

        testImplementation(kotlin("test"))
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        // protobuf(files("proto/"))
    }*/
}

//tasks.test {
//    useJUnitPlatform()
//}
