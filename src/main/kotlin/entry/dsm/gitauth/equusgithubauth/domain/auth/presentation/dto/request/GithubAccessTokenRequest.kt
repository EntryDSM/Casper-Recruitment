package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request

data class GithubAccessTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val code: String,
    val redirectUri: String,
)
