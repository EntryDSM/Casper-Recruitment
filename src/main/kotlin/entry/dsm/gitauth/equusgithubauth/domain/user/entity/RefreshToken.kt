package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "refresh_token")
data class RefreshToken(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val githubId: String,

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Column(name = "token_expiration")
    var tokenExpiration: Long? = null
)
