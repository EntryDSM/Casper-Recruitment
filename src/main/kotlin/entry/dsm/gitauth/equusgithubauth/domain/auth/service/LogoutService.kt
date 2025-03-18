package entry.dsm.gitauth.equusgithubauth.domain.auth.service

import entry.dsm.gitauth.equusgithubauth.domain.auth.exception.InvalidAccessTokenException
import entry.dsm.gitauth.equusgithubauth.domain.user.entity.repository.RefreshTokenRepository
import entry.dsm.gitauth.equusgithubauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

@Service
class LogoutService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @Transactional
    fun logout(accessToken: String) {
        if (!jwtTokenProvider.valid(accessToken)) {
            throw InvalidAccessTokenException
        }

        val userName = jwtTokenProvider.getSubjectFromToken(accessToken)

        refreshTokenRepository.findByLoginId(userName)?.let {
            refreshTokenRepository.delete(it)
        }

        val expiration = jwtTokenProvider.getExpiration(accessToken)
        if (expiration > 0) {
            redisTemplate.opsForValue().set("blacklist:$accessToken", "logout", expiration, TimeUnit.MILLISECONDS)
        }
    }
}

