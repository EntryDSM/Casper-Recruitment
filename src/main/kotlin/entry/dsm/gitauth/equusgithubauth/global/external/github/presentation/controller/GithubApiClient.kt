package entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.response.GithubOrganizationResponse
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.response.GithubUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "github-api-client",
    url = "\${spring.security.oauth2.client.registration.github.api-base-uri}"
)
interface GithubApiClient {
    @GetMapping("/user")
    fun getUser(
        @RequestHeader("Authorization") authorization: String,
    ): GithubUserResponse

    @GetMapping("/user/orgs")
    fun getUserOrganizations(
        @RequestHeader("Authorization") authorization: String,
    ): List<GithubOrganizationResponse>
}
