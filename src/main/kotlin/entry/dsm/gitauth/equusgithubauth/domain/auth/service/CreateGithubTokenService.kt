package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.GithubAccessTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.response.GithubAccessTokenResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CreateGithubTokenService(
    private val githubProperties: GithubProperties,
    private val restTemplate: RestTemplate
) {
    companion object {
        private const val ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token"
    }
    fun execute(
        code: String
    ): GithubAccessTokenResponse {
        return restTemplate.postForObject(
            ACCESS_TOKEN_URL,
            GithubAccessTokenRequest(
                clientId = githubProperties.clientId,
                clientSecret = githubProperties.clientSecret,
                code = code,
                redirectUri = githubProperties.redirectUri
            ),
            GithubAccessTokenResponse::class.java
        )!!
    }


}