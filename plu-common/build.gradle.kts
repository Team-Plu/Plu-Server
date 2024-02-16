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

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    //jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
