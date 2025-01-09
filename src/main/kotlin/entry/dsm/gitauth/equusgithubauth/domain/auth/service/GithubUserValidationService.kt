package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.controller.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.GithubOrganizationResponse
import entry.dsm.gitauth.equusgithubauth.global.external.service.TokenAuthenticator
import org.springframework.stereotype.Service

@Service
class GithubUserValidationService(
    private val githubApiClient: GithubApiClient,
    private val tokenAuthenticator: TokenAuthenticator,
) {
    companion object {
        private const val TARGET_ORGANIZATION = "EntryDSM"
    }

    fun validateUserMembership(
        token: String,
        username: String,
    ): Boolean {
        return try {
            val authorizationHeader = tokenAuthenticator.createAuthorizationHeader(token)
            val currentUsername = githubApiClient.getUser(authorizationHeader).login
            if (currentUsername != username) {
                return false
            }
            val isMemberOfOrg =
                githubApiClient.getUserOrganizations(authorizationHeader, username)
                    .any { org: GithubOrganizationResponse -> org.login == TARGET_ORGANIZATION }
            isMemberOfOrg
        } catch (e: Exception) {
            false
        }
    }
}
