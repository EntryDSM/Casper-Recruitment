package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.external.service.TokenAuthenticator
import org.springframework.stereotype.Service

@Service
class GithubUserTokenValidationService(
    private val githubClient: GithubApiClient,
    private val tokenAuthenticator: TokenAuthenticator
) {
    fun validateAccessToken(token: String) {
        require(token.isNotBlank()) { "Access token is empty." }
        try {
            githubClient.getUser(tokenAuthenticator.createAuthorizationHeader(token))
        } catch (ex: Exception) {
            throw IllegalArgumentException("Access token is expired or invalid.")
        }
    }
}
