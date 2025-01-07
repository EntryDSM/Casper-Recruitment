package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import org.slf4j.LoggerFactory
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
    private val logger = LoggerFactory.getLogger(GithubUserService::class.java)
    private val timestampFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME


    fun getGithubUserInformation(oAuth2User: OAuth2User): GithubUserInformation {
        return try {
            val client = getAuthorizedClient(oAuth2User)

            githubUserTokenValidationService.validateAccessToken(client.accessToken.tokenValue)

            validateOrganizationMembership(client, oAuth2User)

            createGithubUserInformation(oAuth2User, client)
        } catch (e: Exception) {
            logger.error("GitHub 사용자 정보 취득 중 오류 발생: ${e.message}", e)
            throw IllegalStateException("GitHub 사용자 정보를 가져올 수 없습니다.", e)
        }
    }

    private fun getAuthorizedClient(oAuth2User: OAuth2User): OAuth2AuthorizedClient {
        return authorizedClientService.loadAuthorizedClient("github", oAuth2User.name)
            ?: throw IllegalArgumentException("사용자 ${oAuth2User.name}에 대한 인증된 클라이언트를 찾을 수 없습니다.")
    }

    private fun validateOrganizationMembership(client: OAuth2AuthorizedClient, oAuth2User: OAuth2User) {
        val username = oAuth2User.attributes["login"].toString()
        if (!githubUserValidationService.validateUserMembership(client.accessToken.tokenValue, username)) {
            logger.warn("조직 멤버십 검증 실패: $username")
            throw IllegalArgumentException("사용자가 조직의 멤버가 아닙니다.")
        }
        logger.info("조직 멤버십 검증 성공: $username")
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
            createdAt = parseTimestamp(getRequiredAttributeValue(oAuth2User, "created_at")),
            updatedAt = parseTimestamp(getRequiredAttributeValue(oAuth2User, "updated_at")),
            accessToken = client.accessToken.tokenValue,
            tokenExpiration = parseTokenExpirationTime(client)
        )
    }

    private fun parseTimestamp(timestamp: String): LocalDateTime {
        return LocalDateTime.parse(
            timestamp.replace("Z", ""),
            timestampFormatter
        )
    }

    private fun parseTokenExpirationTime(client: OAuth2AuthorizedClient): LocalDateTime {
        return client.accessToken.expiresAt?.atZone(ZoneId.systemDefault())?.toLocalDateTime()
            ?: throw IllegalStateException("토큰 만료 시간이 누락되었습니다.")
    }

    private fun getRequiredAttributeValue(oAuth2User: OAuth2User, attributeName: String): String {
        return oAuth2User.attributes[attributeName]?.toString()
            ?: throw IllegalStateException("사용자 ${oAuth2User.attributes["login"]}의 필수 속성 '$attributeName'이(가) 누락되었습니다.")
    }

    private fun getOptionalAttributeValue(oAuth2User: OAuth2User, attributeName: String): String? {
        return oAuth2User.attributes[attributeName]?.toString()
    }
}