plugins {
    java
    `java-library`
}

dependencies {
    api(project(":game-protocol"))

    //implementation("org.springframework.boot:spring-boot-starter")
    implementation("io.netty:netty-all:4.1.109.Final")
}

//tasks.test {
//    useJUnitPlatform()
//}
//kotlin {
//    jvmToolchain(21)
//}