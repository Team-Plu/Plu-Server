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
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
