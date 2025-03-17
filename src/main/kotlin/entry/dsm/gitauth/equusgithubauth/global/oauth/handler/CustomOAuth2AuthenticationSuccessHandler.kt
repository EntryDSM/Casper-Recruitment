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

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        // CustomOauth2UserDetails에 저장된 attributes에서 토큰 정보 추출
        val oauthUser = authentication.principal as? CustomOauth2UserDetails
        val tokenInfo = oauthUser?.attributes?.let { attributes ->
            mapOf(
                "accessToken" to attributes["accessToken"],
                "accessTokenExpiration" to attributes["accessTokenExpiration"],
                "refreshToken" to attributes["refreshToken"],
                "refreshTokenExpiration" to attributes["refreshTokenExpiration"]
            )
        }
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(tokenInfo))
    }
}