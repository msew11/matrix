import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * 构建相关参考：
 * @see <a href="https://docs.spring.io/spring-boot/reference/features/kotlin.html">Kotlin Support</a>
 * @see <a href="https://spring.io/guides/tutorials/spring-boot-kotlin">Guide</a>
 */
plugins {
    java

    id("org.springframework.boot") version "3.3.0" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false

    kotlin("jvm") version "1.9.24" apply false
    kotlin("plugin.noarg") version "1.9.24" apply false
    kotlin("plugin.spring") version "1.9.24" apply false

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

        maven("http://localhost:8081/repository/maven-public") {
            isAllowInsecureProtocol = true
            credentials {
                username = "admin"
                password = "123456"
            }
        }

        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }
}
