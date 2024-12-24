package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Table
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash
@Table(name = "refresh_token")
data class RefreshToken(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val githubId: String,

    @Indexed
    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @TimeToLive
    @Column(name = "token_expiration")
    var tokenExpiration: Long? = null
)
