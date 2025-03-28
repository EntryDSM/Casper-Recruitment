package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2AuthenticationFailureHandler : AuthenticationFailureHandler {

    private val objectMapper = ObjectMapper()

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json; charset=UTF-8"

        val errorResponse = mapOf(
            "error" to "구글 로그인 실패",
            "message" to (exception.message ?: "올바른 이메일 도메인이 아닙니다.")
        )

        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}