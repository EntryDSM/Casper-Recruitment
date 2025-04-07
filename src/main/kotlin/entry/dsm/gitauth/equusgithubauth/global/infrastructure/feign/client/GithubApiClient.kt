package entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client

import entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.dto.response.GithubOrganizationResponse
import entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.dto.response.GithubUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "github-api-client",
    url = "\${spring.security.oauth2.client.registration.github.api-base-url}",
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
