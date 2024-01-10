plugins {
    kotlin("jvm")
}
tasks.jar {
	enabled = true
}

dependencies {
	implementation(project(":plu-common"))
	implementation(project(":plu-external"))

	// web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// SQS
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-aws-messaging:2.2.6.RELEASE")
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
