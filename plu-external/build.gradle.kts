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
    implementation(project(":plu-common"))

    // SQS
    api(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    api("io.awspring.cloud:spring-cloud-aws-starter-sqs")

    // WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //jwt
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")

    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
