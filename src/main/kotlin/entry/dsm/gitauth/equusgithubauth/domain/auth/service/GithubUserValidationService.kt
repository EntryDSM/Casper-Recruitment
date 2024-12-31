package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.client.GithubApiClient
import entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto.GithubOrganizationResponse
import entry.dsm.gitauth.equusgithubauth.global.external.service.TokenAuthenticator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GithubUserValidationService(
    private val githubApiClient: GithubApiClient,
    private val tokenAuthenticator: TokenAuthenticator,
) {
    private val logger = LoggerFactory.getLogger(GithubUserValidationService::class.java)

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
                logger.error("Token username mismatch: $currentUsername != $username")
                return false
            }

            val isMemberOfOrg =
                githubApiClient.getUserOrganizations(authorizationHeader, username)
                    .any { org: GithubOrganizationResponse -> org.login == TARGET_ORGANIZATION }

            logger.info("Membership status for $username in $TARGET_ORGANIZATION: $isMemberOfOrg")
            isMemberOfOrg
        } catch (e: Exception) {
            logger.error("Error validating GitHub user membership for $username", e)
            false
        }
    }
}
