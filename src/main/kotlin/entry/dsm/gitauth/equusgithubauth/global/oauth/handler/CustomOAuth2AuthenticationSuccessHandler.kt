package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import entry.dsm.gitauth.equusgithubauth.global.oauth.service.component.OauthTokenService
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2AuthenticationSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val oauthTokenService: OauthTokenService,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        // CustomOauth2UserDetails에서 loginId 추출
        val oauthUser =
            authentication.principal as? CustomOauth2UserDetails
                ?: throw IllegalStateException("Authentication principal must be CustomOauth2UserDetails")

        val loginId = oauthUser.username

        val tokenResponse: TokenResponse = oauthTokenService.generateTokenResponse(loginId)

        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(tokenResponse))
        response.writer.flush()
    }
}
