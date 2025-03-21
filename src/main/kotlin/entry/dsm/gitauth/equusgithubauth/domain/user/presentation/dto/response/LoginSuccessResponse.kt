package entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response

import java.time.LocalDateTime

data class LoginSuccessResponse(
    val isMember: Boolean,
    val accessToken: String,
    val accessTokenExpiration: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiration: LocalDateTime
)
