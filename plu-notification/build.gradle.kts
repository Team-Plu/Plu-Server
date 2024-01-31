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

    // firebase-cloud
    implementation("com.google.firebase:firebase-admin:9.2.0")

    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
