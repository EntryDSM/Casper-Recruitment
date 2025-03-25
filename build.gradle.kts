plugins {
    id("org.jetbrains.kotlin.jvm") version PluginVersions.kotlin
    id("org.jetbrains.kotlin.plugin.spring") version PluginVersions.kotlin
    id("org.springframework.boot") version PluginVersions.springBoot
    id("io.spring.dependency-management") version PluginVersions.dependencyManagement
    id("org.jetbrains.kotlin.plugin.jpa") version PluginVersions.kotlin
    id("org.jlleitschuh.gradle.ktlint") version PluginVersions.ktlint
}

group = "entry.dsm.gitauth"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.springBootStarter)
    implementation(Dependencies.springBootSecurity)
    implementation(Dependencies.springBootOAuth2Client)
    implementation(Dependencies.springBootWeb)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.jjwtApi)
    runtimeOnly(Dependencies.jjwtImpl)
    runtimeOnly(Dependencies.jjwtJackson)

    implementation(Dependencies.springDataRedis)
    implementation(Dependencies.springBootDataRedis)

    implementation(Dependencies.springBootDataJpa)
    implementation(Dependencies.lombok)

    testImplementation(Dependencies.springBootTest)
    testImplementation(Dependencies.kotlinTestJunit5)
    testRuntimeOnly(Dependencies.junitPlatformLauncher)

    runtimeOnly(Dependencies.mysqlConnector)
    implementation(Dependencies.springCloudOpenFeign)

    implementation(Dependencies.hibernateValidator)
    implementation(Dependencies.jakartaValidation)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

noArg {
    annotation("jakarta.persistence.Entity")
}

tasks.test {
    useJUnitPlatform()
}
