package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.oauth.exception.AuthenticationPrincipalMismatch
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.component.OauthTokenService
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class CustomOAuth2AuthenticationSuccessHandler(
    private val oauthTokenService: OauthTokenService,
    @Value("\${oauth.google.redirect2}") private val frontDirectUrl: String,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oauthUser =
            authentication.principal as? CustomOauth2UserDetails
                ?: throw AuthenticationPrincipalMismatch()
        val loginId = oauthUser.username

        val tokenResponse: TokenResponse = oauthTokenService.generateTokenResponse(loginId)

        val redirectUrl = buildRedirectUrl(tokenResponse)

        response.sendRedirect(redirectUrl)
    }

    private fun buildRedirectUrl(tokenResponse: TokenResponse): String {
        val baseUrl = frontDirectUrl
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
