package entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.GithubOrganizationResponse
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.GithubUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "githubApiClient", url = "https://api.github.com")
interface GithubApiClient {
    @GetMapping("/user")
    fun getUser(
        @RequestHeader("Authorization") authorization: String,
    ): GithubUserResponse

    @GetMapping("/users/orgs")
    fun getUserOrganizations(
        @RequestHeader("Authorization") authorization: String,
    ): List<GithubOrganizationResponse>
}
