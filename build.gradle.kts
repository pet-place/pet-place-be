import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.script.experimental.jvm.util.KotlinJars.stdlib

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.3.61"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.5.21"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"

}

group = "com.pet-place.be"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
//	implementation(project(":common"))

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.5")
	implementation("com.google.api-client:google-api-client:2.0.0")
	implementation("com.google.api-client:google-api-client-jackson2:2.0.0")
	implementation("org.projectlombok:lombok:1.18.22")
	// https://mvnrepository.com/artifact/joda-time/joda-time
	implementation("joda-time:joda-time:2.12.1")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
	implementation("org.apache.commons:commons-lang3:3.12.0")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-configuration-processor
	implementation("org.springframework.boot:spring-boot-configuration-processor:2.7.5")









	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
