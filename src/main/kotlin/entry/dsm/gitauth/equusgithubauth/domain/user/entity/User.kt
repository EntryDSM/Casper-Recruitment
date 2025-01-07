package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(name = "github_id", nullable = false, unique = true)
    val githubId: String,
    @Column(name = "username", nullable = false)
    val username: String,
    @Column(name = "email", nullable = true, unique = true)
    val email: String?,
    @Column(name = "profile_url")
    val profileUrl: String? = null,
    @Column(name = "avatar_url")
    val avatarUrl: String? = null,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "is_member_of_org", nullable = false)
    val isMemberOfOrg: Boolean = false,
    @Column(name = "access_token")
    var accessToken: String? = null,
    @Column(name = "token_expiration")
    var tokenExpiration: LocalDateTime? = null,
)
