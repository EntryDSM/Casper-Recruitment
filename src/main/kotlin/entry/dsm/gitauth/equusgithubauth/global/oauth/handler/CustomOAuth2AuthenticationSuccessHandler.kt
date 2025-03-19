package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails

class CustomOAuth2AuthenticationSuccessHandler(
    private val objectMapper: ObjectMapper
) : AuthenticationSuccessHandler {

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val ACCESS_TOKEN_EXPIRATION = "accessTokenExpiration"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val REFRESH_TOKEN_EXPIRATION = "refreshTokenExpiration"
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        // CustomOauth2UserDetails에 저장된 attributes에서 토큰 정보 추출
        val oauthUser = authentication.principal as? CustomOauth2UserDetails
        val tokenInfo = oauthUser?.attributes?.let { attributes ->
            mapOf(
                ACCESS_TOKEN to attributes[ACCESS_TOKEN],
                ACCESS_TOKEN_EXPIRATION to attributes[ACCESS_TOKEN_EXPIRATION],
                REFRESH_TOKEN to attributes[REFRESH_TOKEN],
                REFRESH_TOKEN_EXPIRATION to attributes[REFRESH_TOKEN_EXPIRATION]
            )
        }
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(tokenInfo))
    }
}
