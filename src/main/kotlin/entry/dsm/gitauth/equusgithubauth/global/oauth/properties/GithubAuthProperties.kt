package entry.dsm.gitauth.equusgithubauth.global.oauth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github.auth")
class GithubAuthProperties(
    val redirectUrl: String,
    val successAuthenticationUrl: String,
    val failureAuthenticationUrl: String,
    val failure: String,
    val frontDirectUrl: String,
)
