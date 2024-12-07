package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class GithubUserService(
    private val authorizedClientService: OAuth2AuthorizedClientService,
    private val githubUserValidationService: GithubUserValidationService,
    private val githubUserTokenValidationService: GithubUserTokenValidationService
) {
    private val logger = LoggerFactory.getLogger(GithubUserService::class.java)

    fun getGithubUserInformation(oAuth2User: OAuth2User): GithubUserInformation {
        val client = getAuthorizedClient(oAuth2User)
        githubUserTokenValidationService.validateAccessToken(client.accessToken.tokenValue)

        return try {
            if (!githubUserValidationService.validateUserMembership(client.accessToken.tokenValue, oAuth2User.attributes["login"].toString())) {
                throw IllegalArgumentException("User is not a member of the organization.")
            }
            logger.info("Successfully validated user membership for: ${oAuth2User.attributes["login"]}")
            createGithubUserInformation(oAuth2User, client)
        } catch (e: Exception) {
            logger.error("Error occurred while getting GitHub user information for ${oAuth2User.attributes["login"]}: ${e.message}", e)
            throw IllegalStateException("Failed to get GitHub user information.", e)
        }
    }

    private fun getAuthorizedClient(oAuth2User: OAuth2User): OAuth2AuthorizedClient {
        return authorizedClientService.loadAuthorizedClient("github", oAuth2User.name)
            ?: throw IllegalArgumentException("No authorized client found for the user ${oAuth2User.name}.")
    }

    private fun createGithubUserInformation(
        oAuth2User: OAuth2User,
        client: OAuth2AuthorizedClient
    ): GithubUserInformation {
        return GithubUserInformation(
            githubId = getRequiredAttributeValue(oAuth2User, "id"),
            username = getRequiredAttributeValue(oAuth2User, "login"),
            email = getOptionalAttributeValue(oAuth2User, "email"),
            profileUrl = getRequiredAttributeValue(oAuth2User, "html_url"),
            avatarUrl = getRequiredAttributeValue(oAuth2User, "avatar_url"),
            createdAt = getRequiredAttributeValue(oAuth2User, "created_at"),
            updatedAt = getRequiredAttributeValue(oAuth2User, "updated_at"),
            accessToken = client.accessToken.tokenValue,
            tokenExpiration = client.accessToken.expiresAt.toString()
        )
    }

    private fun getRequiredAttributeValue(oAuth2User: OAuth2User, attributeName: String): String {
        return oAuth2User.attributes[attributeName]?.toString()
            ?: throw IllegalStateException("Required attribute '$attributeName' is missing for user ${oAuth2User.attributes["login"]}.")
    }

    private fun getOptionalAttributeValue(oAuth2User: OAuth2User, attributeName: String): String? {
        return oAuth2User.attributes[attributeName]?.toString()
    }
}
