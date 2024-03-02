import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("plugin.jpa") version "1.9.20"
	kotlin("kapt") version "1.9.20"
}

repositories {
	mavenCentral()
}

subprojects {
	group = "com.th"

	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.jetbrains.kotlin.kapt")

	java.sourceCompatibility = JavaVersion.VERSION_17

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	dependencies {
		// Health Check
		implementation("org.springframework.boot:spring-boot-starter-actuator")

		// tools
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		// kotlin
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

