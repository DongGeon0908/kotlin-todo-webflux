import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"

    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    idea
}

group = "com.goofy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

object DependencyVersion {
    const val KOTLIN_LOGGING_VERSION = "3.0.4"
}

dependencies {
    // spring starter
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // database
    runtimeOnly("mysql:mysql-connector-java")

    // database
    implementation("io.github.microutils:kotlin-logging:${DependencyVersion.KOTLIN_LOGGING_VERSION}")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "7.3.1"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
