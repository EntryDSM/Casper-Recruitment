package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.client.GithubApiClient
import org.springframework.stereotype.Service

@Service
class GithubUserTokenValidationService(
    private val githubClient: GithubApiClient
) {
    fun validateAccessToken(token: String) {
        require(token.isNotBlank()) { "Access token is empty." }
        try {
            githubClient.getUser("Bearer $token")
        } catch (ex: Exception) {
            throw IllegalArgumentException("Access token is expired or invalid.")
        }
    }
}
