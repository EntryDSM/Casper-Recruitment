package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID = UUID.randomUUID(),
    val loginId: String,
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[!@#\$%^&*])(?=\\S+$).{8,}$",
        message = "비밀번호는 최소 8자 이상, 하나 이상의 숫자와 특수 문자를 포함해야 합니다."
    )
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    var password: String,
    @Column(name = "name", nullable = false)
    var name: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,
    val provider: String,  // 구글 로그인 사용자의 provider
    val providerId: String,

)
