package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import org.springframework.stereotype.Service

@Service
class ValidateGithubOrganizationService(
    private val githubApiClient: GithubApiClient
) {
    companion object {
        private const val TARGET_ORG = "EntryDSM"
    }

    fun execute(accessToken: String, username: String): Boolean {
        val userOrganizations = githubApiClient.getUserOrganizations(accessToken)

        return userOrganizations.any { it.login == TARGET_ORG }
    }
}