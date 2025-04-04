package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubRegistrationProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.command.dto.response.GithubAccessTokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.GitHubOAuthException
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubOAuthApiClient
import org.springframework.stereotype.Service

@Service
class GenerateGithubTokenService(
    private val githubOAuthApiClient: GithubOAuthApiClient,
    private val githubRegistrationProperties: GithubRegistrationProperties,
) {
    fun execute(code: String): GithubAccessTokenResponse {
        try {
            val response =
                githubOAuthApiClient.codeToToken(
                    redirectUrl = githubRegistrationProperties.redirectUrl,
                    clientSecret = githubRegistrationProperties.clientSecret,
                    clientId = githubRegistrationProperties.clientId,
                    code = code,
                )

            return GithubAccessTokenResponse(response.accessToken)
        } catch (e: Exception) {
            throw GitHubOAuthException()
        }
    }
}
