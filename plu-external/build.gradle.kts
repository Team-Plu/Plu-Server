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
	api(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
	api("io.awspring.cloud:spring-cloud-aws-starter-sqs")
	implementation(kotlin("stdlib-jdk8"))

	// firebase-cloud
	implementation("com.google.firebase:firebase-admin:6.8.1")
}
repositories {
	mavenCentral()
}
kotlin {
	jvmToolchain(17)
}
