package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.GitHubOAuthException
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.LoginSuccessResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubOAuthApiClient
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubRegistrationProperties
import org.springframework.stereotype.Service

@Service
class GenerateGithubTokenService(
    private val githubOAuthApiClient: GithubOAuthApiClient,
    private val githubRegistrationProperties: GithubRegistrationProperties,
    private val userService: UserService
) {
    fun execute(code: String): LoginSuccessResponse {
        try {
            val githubAccessToken =
                githubOAuthApiClient.codeToToken(
                    redirectUrl = githubRegistrationProperties.redirectUrl,
                    clientSecret = githubRegistrationProperties.clientSecret,
                    clientId = githubRegistrationProperties.clientId,
                    code = code,
                ).accessToken

            return userService.execute(githubAccessToken)
        } catch (e: Exception) {
            throw GitHubOAuthException()
        }
    }
}
