package entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.response.LoginAccessTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "github-oauth-api-client",
    url = "\${spring.security.oauth2.client.registration.github.base-uri}"
)
interface GithubOAuthApiClient {
    @PostMapping("/login/oauth/access_token", consumes = ["application/json"], produces = ["application/json"])
    fun codeToToken(
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
    ): LoginAccessTokenResponse
}
