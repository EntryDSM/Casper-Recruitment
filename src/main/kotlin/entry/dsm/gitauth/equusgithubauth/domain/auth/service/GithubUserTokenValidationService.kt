package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.Thread.sleep

@Service
class GithubUserTokenValidationService(
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(GithubUserTokenValidationService::class.java)

    fun validateAccessToken(token: String) {
        token.takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("Access token is empty.")
        val isTokenValid = retry(3, 2000) { isTokenActive(token) } // Add delay between retries
        if (!isTokenValid!!) {
            throw IllegalArgumentException("Access token is expired or invalid.")
        }
    }

    private fun retry(times: Int, delay: Long = 0L, block: () -> Boolean?): Boolean? {
        repeat(times - 1) {
            val result = try {
                block()
            } catch (e: Exception) {
                logger.error("Retry failed: ${e.message}")
                null
            }
            if (result == true) return result
            Thread.sleep(delay)
        }
        return block()
    }

    private fun isTokenActive(token: String): Boolean {
        return try {
            val request = buildGithubApiRequest(token)
            val response: ResponseEntity<String> = restTemplate.exchange(request, String::class.java)
            response.statusCode == HttpStatus.OK
        } catch (ex: Exception) {
            logger.error("Error occurred while validating GitHub access token: ${ex.message}", ex)
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
}
