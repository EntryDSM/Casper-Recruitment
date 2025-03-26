package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import entry.dsm.gitauth.equusgithubauth.global.oauth.JwtConstants
import entry.dsm.gitauth.equusgithubauth.global.security.auth.CustomOauth2UserDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class CustomOAuth2AuthenticationSuccessHandler(
    private val objectMapper: ObjectMapper,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        // CustomOauth2UserDetails에 저장된 attributes에서 토큰 정보 추출
        val oauthUser = authentication.principal as? CustomOauth2UserDetails
        val tokenInfo =
            oauthUser?.attributes?.let { attributes ->
                mapOf(
                    JwtConstants.ACCESS_TOKEN to attributes[JwtConstants.ACCESS_TOKEN],
                    JwtConstants.ACCESS_TOKEN_EXPIRATION to attributes[JwtConstants.ACCESS_TOKEN_EXPIRATION],
                    JwtConstants.REFRESH_TOKEN to attributes[JwtConstants.REFRESH_TOKEN],
                    JwtConstants.REFRESH_TOKEN_EXPIRATION to attributes[JwtConstants.REFRESH_TOKEN_EXPIRATION],
                )
            }
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(tokenInfo))
    }
}
