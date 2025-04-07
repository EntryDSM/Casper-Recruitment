package entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
data class LoginAccessTokenResponse(
    val accessToken: String,
    val scope: String,
    val tokenType: String,
)
