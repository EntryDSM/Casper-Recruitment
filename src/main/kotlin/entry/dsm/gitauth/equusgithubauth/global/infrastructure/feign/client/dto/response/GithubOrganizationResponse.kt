package entry.dsm.gitauth.equusgithubauth.global.infrastructure.feign.client.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubOrganizationResponse(
    val login: String,
)
