package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class GithubUserValidationService(
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(GithubUserValidationService::class.java)
    private val TARGET_ORGANIZATION = "EntryDSM"

    fun validateUserMembership(token: String, username: String): Boolean {
        return try {
            val userUrl = "https://api.github.com/user"

            val headers = HttpHeaders().apply {
                set(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }

            val userRequest = RequestEntity.get(userUrl)
                .headers(headers)
                .build()

            val userResponse = restTemplate.exchange<Map<String, Any>>(userRequest)
            val currentUsername = userResponse.body?.get("login")?.toString()

            if (currentUsername != username) {
                logger.error("Token username mismatch: $currentUsername != $username")
                return false
            }

            val organizationsUrl = "https://api.github.com/users/$username/orgs"
            val orgsRequest = RequestEntity.get(organizationsUrl)
                .headers(headers)
                .build()

            val orgsResponse = restTemplate.exchange<List<Map<String, Any>>>(orgsRequest)

            val isMemberOfOrg = orgsResponse.body?.any {
                it["login"]?.toString() == TARGET_ORGANIZATION
            } ?: false

            logger.info("Membership status for $username in $TARGET_ORGANIZATION: $isMemberOfOrg")

            isMemberOfOrg
        } catch (e: Exception) {
            logger.error("Error validating GitHub user membership for $username", e)
            false
        }
    }
}
