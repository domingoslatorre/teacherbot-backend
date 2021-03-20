import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("io.gitlab.arturbosch.detekt") version "1.16.0"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
	kotlin("plugin.jpa") version "1.4.30"
	jacoco
}

group = "com.domingoslatorre.teacherbot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
	testImplementation("io.kotest:kotest-property-jvm:4.4.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xallow-result-return-type")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

buildscript {
	repositories {
		jcenter()
	}
}

detekt {
	toolVersion = "1.16.0"
	config = files("config/detekt/detekt.yml")
	buildUponDefaultConfig = true
}

jacoco {
	toolVersion = "0.8.6"
	reportsDirectory.set(file("$buildDir/customJacocoReportDir"))
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.isEnabled = true
		csv.isEnabled = true
		html.destination = file("$buildDir/jacocoHtml")
	}
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "1".toBigDecimal()
			}
		}
	}
}
