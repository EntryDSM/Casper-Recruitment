object Dependencies {
    const val springBootStarter = "org.springframework.boot:spring-boot-starter"
    const val springBootSecurity = "org.springframework.boot:spring-boot-starter-security"
    const val springBootOAuth2Client = "org.springframework.boot:spring-boot-starter-oauth2-client"
    const val springBootWeb = "org.springframework.boot:spring-boot-starter-web"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    const val jjwtApi = "io.jsonwebtoken:jjwt-api:${DependencyVersions.jjwt}"
    const val jjwtImpl = "io.jsonwebtoken:jjwt-impl:${DependencyVersions.jjwt}"
    const val jjwtJackson = "io.jsonwebtoken:jjwt-jackson:${DependencyVersions.jjwt}"

    const val springDataRedis = "org.springframework.data:spring-data-redis"
    const val springBootDataRedis = "org.springframework.boot:spring-boot-starter-data-redis"

    const val springBootDataJpa = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val lombok = "org.projectlombok:lombok"

    const val springBootTest = "org.springframework.boot:spring-boot-starter-test"
    const val kotlinTestJunit5 = "org.jetbrains.kotlin:kotlin-test-junit5"
    const val junitPlatformLauncher = "org.junit.platform:junit-platform-launcher"

    const val mysqlConnector = "com.mysql:mysql-connector-j:${DependencyVersions.mysqlConnector}"
    const val springCloudOpenFeign = "org.springframework.cloud:spring-cloud-starter-openfeign:${DependencyVersions.springCloudOpenFeign}"

    const val hibernateValidator = "org.hibernate.validator:hibernate-validator:${DependencyVersions.hibernateValidator}"
    const val jakartaValidation = "jakarta.validation:jakarta.validation-api:${DependencyVersions.jakartaValidation}"
}
