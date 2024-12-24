package entry.dsm.gitauth.equusgithubauth

import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(value = [ JwtProperties::class])
class EquusGithubAuthApplication

fun main(args: Array<String>) {
    runApplication<EquusGithubAuthApplication>(*args)
}
