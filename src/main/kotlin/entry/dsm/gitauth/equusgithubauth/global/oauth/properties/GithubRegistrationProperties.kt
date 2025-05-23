package entry.dsm.gitauth.equusgithubauth.global.oauth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.github")
class GithubRegistrationProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String,
    val scope: String,
    val clientName: String,
)
