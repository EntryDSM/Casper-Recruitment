package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import org.springframework.stereotype.Service

@Service
class ValidateGithubOrganizationService(
    private val githubApiClient: GithubApiClient
) {
    companion object {
        private const val ORG_NAME = "EntryDSM"
    }

    fun execute(
        accessToken: String
    ): Boolean {
        val org = githubApiClient.getUserOrganizations("Bearer $accessToken")
        return org.any { it.login == ORG_NAME}
    }
}