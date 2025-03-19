package entry.dsm.gitauth.equusgithubauth

import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(value = [ JwtProperties::class])
@SpringBootApplication
class EquusGithubAuthApplication

fun main(args: Array<String>) {
    runApplication<EquusGithubAuthApplication>(*args)
}
