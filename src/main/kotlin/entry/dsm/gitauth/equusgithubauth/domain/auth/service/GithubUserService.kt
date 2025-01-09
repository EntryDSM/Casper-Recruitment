package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.AuthorizedClientNotFoundException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.MissingRequiredAttributeException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.OrganizationMembershipErrorException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.TokenExpirationMissingException
import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.UserInfoFetchFailureException
import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class GithubUserService(
    private val authorizedClientService: OAuth2AuthorizedClientService,
    private val githubUserValidationService: GithubUserValidationService,
    private val githubUserTokenValidationService: GithubUserTokenValidationService,
) {
    private val timestampFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun getGithubUserInformation(oAuth2User: OAuth2User): GithubUserInformation {
        return try {
            val client = getAuthorizedClient(oAuth2User)

            githubUserTokenValidationService.validateAccessToken(client.accessToken.tokenValue)

            validateOrganizationMembership(client, oAuth2User)

            createGithubUserInformation(oAuth2User, client)
        } catch (e: Exception) {
            throw UserInfoFetchFailureException
        }
    }

    private fun getAuthorizedClient(oAuth2User: OAuth2User): OAuth2AuthorizedClient {
        return authorizedClientService.loadAuthorizedClient("github", oAuth2User.name)
            ?: throw AuthorizedClientNotFoundException
    }

    private fun validateOrganizationMembership(
        client: OAuth2AuthorizedClient,
        oAuth2User: OAuth2User,
    ) {
        val username = oAuth2User.attributes["login"].toString()
        if (!githubUserValidationService.validateUserMembership(client.accessToken.tokenValue, username)) {
            throw OrganizationMembershipErrorException
        }
    }

    private fun createGithubUserInformation(
        oAuth2User: OAuth2User,
        client: OAuth2AuthorizedClient,
    ): GithubUserInformation {
        return GithubUserInformation(
            githubId = getRequiredAttributeValue(oAuth2User, "id"),
            username = getRequiredAttributeValue(oAuth2User, "login"),
            email = getOptionalAttributeValue(oAuth2User, "email"),
            profileUrl = getRequiredAttributeValue(oAuth2User, "html_url"),
            avatarUrl = getRequiredAttributeValue(oAuth2User, "avatar_url"),
            createdAt = parseTimestamp(getRequiredAttributeValue(oAuth2User, "created_at")),
            updatedAt = parseTimestamp(getRequiredAttributeValue(oAuth2User, "updated_at")),
            accessToken = client.accessToken.tokenValue,
            tokenExpiration = parseTokenExpirationTime(client),
        )
    }

    private fun parseTimestamp(timestamp: String): LocalDateTime {
        return LocalDateTime.parse(
            timestamp.replace("Z", ""),
            timestampFormatter,
        )
    }

    private fun parseTokenExpirationTime(client: OAuth2AuthorizedClient): LocalDateTime {
        return client.accessToken.expiresAt?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
            ?: throw TokenExpirationMissingException
    }

    private fun getRequiredAttributeValue(
        oAuth2User: OAuth2User,
        attributeName: String,
    ): String {
        return oAuth2User.attributes[attributeName]?.toString()
            ?: throw MissingRequiredAttributeException
    }

    private fun getOptionalAttributeValue(
        oAuth2User: OAuth2User,
        attributeName: String,
    ): String? {
        return oAuth2User.attributes[attributeName]?.toString()
    }
}
