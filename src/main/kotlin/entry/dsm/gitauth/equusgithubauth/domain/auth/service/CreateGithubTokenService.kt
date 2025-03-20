package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubProperties
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubOAuthApiClient
import org.springframework.stereotype.Service

@Service
class CreateGithubTokenService(
    private val githubOAuthApiClient: GithubOAuthApiClient,
    private val githubProperties: GithubProperties
) {
    fun execute(code: String): String {
        val response = githubOAuthApiClient.codeToToken(
            redirectUri = githubProperties.redirectUri,
            clientSecret = githubProperties.clientSecret,
            clientId = githubProperties.clientId,
            code = code
        )
        return response.accessToken
    }
}