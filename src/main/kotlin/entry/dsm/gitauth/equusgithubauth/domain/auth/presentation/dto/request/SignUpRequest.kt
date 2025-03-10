package entry.dsm.gitauth.equusgithubauth.domain.auth.presentation.dto.request

import entry.dsm.gitauth.equusgithubauth.global.util.RegexpUtil
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class SignUpRequest(
    @field:Length(min = 1, max = 15)
    @field:NotBlank
    val username: String,

    @field:Pattern(
        regexp = RegexpUtil.PASSWORD_PATTERN,
        message = "비밀번호는 영어, 숫자, 특수문자를 포함해야 하며 10~30자여야 합니다."
    )
    @field:NotBlank
    val password: String
)
