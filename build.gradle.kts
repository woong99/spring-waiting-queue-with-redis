plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "potato.woong"
version = "0.0.1-SNAPSHOT"
description = "spring-waiting-queue-with-redis"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

configurations {
    // log4j2를 위한 설정
    configureEach {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.1")

    // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.13.0")
    implementation("io.jsonwebtoken:jjwt-jackson:0.13.0")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
