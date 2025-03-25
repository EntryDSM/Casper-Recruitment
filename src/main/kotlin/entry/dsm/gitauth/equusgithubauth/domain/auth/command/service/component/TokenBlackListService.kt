package entry.dsm.gitauth.equusgithubauth.domain.auth.command.service.component

import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
@Component
class TokenBlackListService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun blackList(accessToken:String){

        val expiration = jwtTokenProvider.getExpiration(accessToken)
        if (expiration > 0) {
            redisTemplate.opsForValue().set("blacklist:$accessToken", "logout", expiration, TimeUnit.MILLISECONDS)
        }

    }


}