package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
data class RefreshToken(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val githubId: String,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "email", nullable = true, unique = false)
    val email: String,

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Column(name = "token_expiration")
    var tokenExpiration: LocalDateTime? = null
)
