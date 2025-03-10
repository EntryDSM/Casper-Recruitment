package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request

import entry.dsm.gitauth.equusgithubauth.global.util.RegexpUtil
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpRequest(
    @field:NotBlank
    val username: String,

    @field:Pattern(regexp = RegexpUtil.PASSWORD_PATTERN)
    @field:NotBlank
    val password: String
)
