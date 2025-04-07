package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.GithubOAuthException
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.domain.user.service.UserService
import entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.GithubOAuthApiClient
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubAuthProperties
import entry.dsm.gitauth.equusgithubauth.global.oauth.properties.GithubRegistrationProperties
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class GithubOauthService(
    private val githubOAuthApiClient: GithubOAuthApiClient,
    private val githubRegistrationProperties: GithubRegistrationProperties,
    private val githubAuthProperties: GithubAuthProperties,
    private val userService: UserService,
) {
    fun execute(
        code: String,
        response: HttpServletResponse,
    ) {
        try {
            val githubAccessToken =
                githubOAuthApiClient.codeToToken(
                    redirectUrl = githubRegistrationProperties.redirectUrl,
                    clientSecret = githubRegistrationProperties.clientSecret,
                    clientId = githubRegistrationProperties.clientId,
                    code = code,
                ).accessToken

            val tokenResponse = userService.generateTokens(githubAccessToken)

            val redirectUrl = buildRedirectUrl(tokenResponse)

            response.sendRedirect(redirectUrl)
        } catch (e: Exception) {
            throw GithubOAuthException()
        }
    }

    private fun buildRedirectUrl(tokenResponse: TokenResponse): String {
        val baseUrl = githubAuthProperties.frontDirectUrl
        val queryParams =
            mapOf(
                "accessToken" to tokenResponse.accessToken,
                "refreshToken" to tokenResponse.refreshToken,
                "accessTokenExpiration" to tokenResponse.accessTokenExpiration.toString(),
                "refreshTokenExpiration" to tokenResponse.refreshTokenExpiration.toString(),
            )
        val encodedParams =
            queryParams.map { (key, value) ->
                "${URLEncoder.encode(key, StandardCharsets.UTF_8.toString())}=" +
                    URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
            }.joinToString("&")
        return "$baseUrl?$encodedParams"
    }
}
