package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.github")
class GithubProviderProperties(
    val authorizationUri: String,
    val tokenUri: String,
    val userInfoUri: String,
    val userNameAttribute: String
)