tasks.jar {
	enabled = true
}

dependencies {
	implementation(project(":plu-domain"))
	implementation(project(":plu-external"))
	implementation(project(":plu-common"))

	// web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.session:spring-session-data-redis")
}
