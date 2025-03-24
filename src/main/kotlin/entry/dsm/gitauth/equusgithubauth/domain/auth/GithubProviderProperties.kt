package entry.dsm.gitauth.equusgithubauth.domain.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.github")
class GithubProviderProperties(
    val authorizationUrl: String,
    val tokenUrl: String,
    val userInfoUrl: String,
    val userNameAttribute: String,
)
