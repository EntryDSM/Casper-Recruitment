package entry.dsm.gitauth.equusgithubauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EquusGithubAuthApplication

fun main(args: Array<String>) {
    runApplication<EquusGithubAuthApplication>(*args)
}
