package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import entry.dsm.gitauth.equusgithubauth.domain.user.entity.enums.UserRole
import entry.dsm.gitauth.equusgithubauth.global.entity.BaseUUIDEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    id: UUID? = null,
    val loginId: String,
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String? = null,
    @Column(name = "name")
    var name: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,
    val provider: String? = null,
    val providerId: String? = null,
) : BaseUUIDEntity(id)
