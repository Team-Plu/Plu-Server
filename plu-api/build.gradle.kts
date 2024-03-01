plugins {
    kotlin("jvm")
}
tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":plu-domain"))
    implementation(project(":plu-external"))
    implementation(project(":plu-common"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation(kotlin("stdlib-jdk8"))

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
