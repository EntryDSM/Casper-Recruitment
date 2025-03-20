package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.response

import java.time.LocalDateTime

data class LoginSuccessResponse(
    val isUser: Boolean,
    val accessToken: String,
    val accessTokenExpiration: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiration: LocalDateTime
)
