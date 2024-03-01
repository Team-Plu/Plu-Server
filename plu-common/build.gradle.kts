plugins {
    kotlin("jvm")
}
tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
