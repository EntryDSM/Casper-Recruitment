package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.GithubUserInformation
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GithubUserService(
    private val authorizedClientService: OAuth2AuthorizedClientService,
    private val restTemplate: RestTemplate
) {
    fun getGithubUserInformation(oAuth2User: OAuth2User): GithubUserInformation {
        val client = getAuthorizedClient(oAuth2User)
        validateAccessToken(client.accessToken.tokenValue)
        return createGithubUserInformation(oAuth2User, client)
    }

    private fun getAuthorizedClient(oAuth2User: OAuth2User): OAuth2AuthorizedClient {
        return authorizedClientService.loadAuthorizedClient("github", oAuth2User.name)
            ?: throw IllegalArgumentException("인증된 클라이언트를 찾을 수 없음")
    }

    private fun validateAccessToken(token: String) {
        token.takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("토큰이 비어있음")
        if (!isTokenActive(token)) {
            throw IllegalArgumentException("토큰이 만료되었거나 잘못된 토큰입니다.")
        }
    }

    private fun isTokenActive(token: String): Boolean {
        return try {
            val request = buildGithubApiRequest(token)
            val response: ResponseEntity<String> = restTemplate.exchange(request, String::class.java)
            response.statusCode == HttpStatus.OK
        } catch (ex: Exception) {
            false
        }
    }

    private fun buildGithubApiRequest(token: String): RequestEntity<Void> {
        val url = "https://api.github.com/user"
        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $token")
        }
        return RequestEntity.get(url).headers(headers).build()
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
            ?: throw IllegalStateException("필수 속성이 존재하지 않음")
    }

    private fun getOptionalAttributeValue(oAuth2User: OAuth2User, attributeName: String): String? {
        return oAuth2User.attributes[attributeName]?.toString()
    }
}