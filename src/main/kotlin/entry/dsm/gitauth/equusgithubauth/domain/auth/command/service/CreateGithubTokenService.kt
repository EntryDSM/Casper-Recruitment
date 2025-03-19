package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.GithubProperties
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request.GithubAccessTokenRequest
import entry.dsm.gitauth.equusgithubauth.domain.user.presentation.dto.response.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CreateGithubTokenService(
    private val githubProperties: GithubProperties,
) {
    companion object {
        private val restTemplate = RestTemplate()
    }

    fun execute(
        code: String
    ): TokenResponse {
        return restTemplate.postForObject(
            githubProperties.tokenUri,
            GithubAccessTokenRequest(
                clientId = githubProperties.clientId,
                clientSecret = githubProperties.clientSecret,
                code = code,
                redirectUri = githubProperties.redirectUri
            ),
            TokenResponse::class.java
        )!!
    }
}