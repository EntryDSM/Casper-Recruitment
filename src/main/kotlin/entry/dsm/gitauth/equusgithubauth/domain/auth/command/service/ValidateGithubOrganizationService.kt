package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service

import entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.GithubApiClient
import org.springframework.stereotype.Component

@Component
class ValidateGithubOrganizationService(
    private val githubApiClient: GithubApiClient,
) {
    companion object {
        private const val TARGET_ORG = "EntryDSM"
    }

    fun validGithubOrganization(accessToken: String): Boolean {
        val userOrganizations = githubApiClient.getUserOrganizations(accessToken)
        return userOrganizations.any { it.login == TARGET_ORG }
    }
}
