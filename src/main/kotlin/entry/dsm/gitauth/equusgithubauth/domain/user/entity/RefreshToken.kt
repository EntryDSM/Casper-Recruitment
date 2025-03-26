package entry.dsm.gitauth.equusgithubauth.domain.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val loginId: String,
    @Indexed
    var refreshToken: String,
    @TimeToLive
    var tokenExpiration: Long,
)
