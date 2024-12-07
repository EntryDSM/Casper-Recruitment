package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto

data class GithubUserInformation(
    val githubId: String,
    val username: String,
    val email: String?,
    val profileUrl: String,
    val avatarUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val accessToken: String,
    val tokenExpiration: String
)