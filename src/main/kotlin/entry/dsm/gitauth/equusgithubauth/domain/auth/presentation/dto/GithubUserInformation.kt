package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto

import java.time.LocalDateTime

data class GithubUserInformation(
    val githubId: String,
    val username: String,
    val email: String?,
    val profileUrl: String,
    val avatarUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val accessToken: String,
    val tokenExpiration: LocalDateTime
)