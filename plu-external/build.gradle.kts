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

	// SQS
	api("org.springframework.cloud:spring-cloud-aws-messaging:2.2.6.RELEASE")
	implementation(kotlin("stdlib-jdk8"))
}
repositories {
	mavenCentral()
}
kotlin {
	jvmToolchain(17)
}
