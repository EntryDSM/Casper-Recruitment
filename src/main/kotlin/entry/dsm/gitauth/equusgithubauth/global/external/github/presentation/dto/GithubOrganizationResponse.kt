package entry.dsm.gitauth.equusgithubauth.global.external.github.presentation.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubOrganizationResponse(
    val login: String,
)
