package entry.dsm.gitauth.equusgithubauth.global.oauth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import entry.dsm.gitauth.equusgithubauth.global.exception.CustomException
import entry.dsm.gitauth.equusgithubauth.global.exception.CustomOauth2AuthenticationException
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorCode
import entry.dsm.gitauth.equusgithubauth.global.exception.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@Component
class CustomOAuth2AuthenticationFailureHandler(
    private val objectMapper: ObjectMapper,
) : AuthenticationFailureHandler {
    companion object {
        private const val DEFAULT_OAUTH_ERROR_MESSAGE = "OAuth2 인증 실패"
        private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        val (statusCode, errorMessage) =
            when (exception) {
                is CustomOauth2AuthenticationException -> {
                    val cause = exception.cause
                    if (cause is CustomException) {
                        cause.statusCode to cause.message
                    } else {
                        ErrorCode.INTERNAL_SERVER_ERROR.status to DEFAULT_OAUTH_ERROR_MESSAGE
                    }
                }
                is CustomException -> exception.statusCode to exception.message
                else -> ErrorCode.INTERNAL_SERVER_ERROR.status to DEFAULT_OAUTH_ERROR_MESSAGE
            }

        val errorResponse =
            ErrorResponse(
                statusCode = statusCode,
                message = errorMessage ?: UNKNOWN_ERROR_MESSAGE,
                timestamp = LocalDateTime.now(),
                path = request.requestURI,
                method = request.method,
            )

        response.status = statusCode
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
        response.writer.flush()
    }
}
