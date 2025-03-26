package entry.dsm.gitauth.equusgithubauth.domain.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github.auth")
class GithubAuthProperties(
    val redirectUrl: String,
    val successAuthenticationUrl: String,
    val failureAuthenticationUrl: String,
    val failure: String,
)
