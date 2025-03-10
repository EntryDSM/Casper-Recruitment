package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class LoginRequest(
    @field:Length(min = 1, max = 15)
    @field:NotBlank
    val username: String,

    @field:Length(min = 10, max = 30)
    @field:NotBlank
    val password: String
)
